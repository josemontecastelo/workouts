package com.josealfonsomora.workouts.data.repositories

import android.content.ContentValues
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.josealfonsomora.workouts.data.sources.room.WorkoutDao
import com.josealfonsomora.workouts.data.sources.room.WorkoutEntity
import com.josealfonsomora.workouts.data.sources.room.toWorkout
import com.josealfonsomora.workouts.data.sources.sqlite.WorkoutSqliteContract.WorkoutEntry
import com.josealfonsomora.workouts.di.WorkoutsSharedPreferences
import com.josealfonsomora.workouts.domain.Muscle
import com.josealfonsomora.workouts.domain.Workout
import com.josealfonsomora.workouts.domain.WorkoutType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class DailyWorkoutsRepository @Inject constructor(
    private val workoutsDb: SQLiteDatabase,
    private val workoutDao: WorkoutDao,
    @WorkoutsSharedPreferences private val workoutsSharedPreferences: SharedPreferences,
) {

    suspend fun setupDailyWorkoutsDB() {
        withContext(IO) {
            if (workoutDao.getAllWorkouts().first().isEmpty()) {
                val workoutEntity = WorkoutEntity(
                    name = "Face pull TRX",
                    workoutType = WorkoutType.TRX,
                    description = "Face pull con TRX",
                    muscles = listOf(Muscle.SHOULDERS),
                    image = "trx",
                    date = Date().toString(),
                    done = false
                )
                val workoutEntity2 = workoutEntity.copy(
                    name = "Curl biceps TRX",
                    description = "Curl de biceps con TRX",
                    muscles = listOf(Muscle.BICEPS),
                )

                val workoutEntity3 = workoutEntity.copy(
                    name = "Sentadillas con TRX",
                    description = "Sentadillas con TRX",
                    muscles = listOf(Muscle.QUADS),
                )

                workoutDao.insertWorkout(workoutEntity, workoutEntity2, workoutEntity3)
            }
        }
        withContext(IO) {
            val cursor =
                workoutsDb.rawQuery("SELECT Count(*) FROM ${WorkoutEntry.TABLE_NAME}", null)
            cursor.moveToFirst()
            val count = cursor.getInt(0)
            cursor.close()

            if (count <= 0) {
                val date = Date().toString()
                val values = ContentValues().apply {
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_NAME, "Cinta 30min")
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE, WorkoutType.CARDIO.name)
                    put(
                        WorkoutEntry.COLUMN_NAME_WORKOUT_DESCRIPTION,
                        "Correr en cinta durante 30 minutos"
                    )
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_MUSCLES, Muscle.QUADS.name)
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_IMAGE, "cardio")
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_DATE, date)
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_DONE, false)
                }
                workoutsDb.insert(WorkoutEntry.TABLE_NAME, null, values)

                workoutsDb.insert(WorkoutEntry.TABLE_NAME, null, ContentValues().apply {
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_NAME, "Sentadillas con Kettlebell")
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE, WorkoutType.KETTLEBELL.name)
                    put(
                        WorkoutEntry.COLUMN_NAME_WORKOUT_DESCRIPTION,
                        "Sentidas con Kettlebell de 10kg"
                    )
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_MUSCLES, Muscle.QUADS.name)
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_IMAGE, "kettlebell")
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_DATE, date)
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_DONE, false)
                })
            }
        }
    }

    suspend fun getDailyWorkouts(): List<Workout> {
        return withContext(IO) {
            val cursor = workoutsDb.query(
                WorkoutEntry.TABLE_NAME, null, null, null, null, null, null
            )
            val workouts = mutableListOf<Workout>()
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndexOrThrow(WorkoutEntry.COLUMN_NAME_WORKOUT_NAME))
                val type =
                    cursor.getString(cursor.getColumnIndexOrThrow(WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE))
                val description =
                    cursor.getString(cursor.getColumnIndexOrThrow(WorkoutEntry.COLUMN_NAME_WORKOUT_DESCRIPTION))
                val muscles =
                    cursor.getString(cursor.getColumnIndexOrThrow(WorkoutEntry.COLUMN_NAME_WORKOUT_MUSCLES))
                        .split(",").map { Muscle.valueOf(it) }
                workouts.add(Workout(name, WorkoutType.valueOf(type), description, muscles))
            }
            cursor.close()
            workouts
        }
    }

    suspend fun deleteKettleBellWorkouts() {
        // Define la parte 'where' de la consulta.
        val selection = "${WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE} LIKE ?"
        // Especifica los argumentos en el orden del marcador de posición ?.
        val selectionArgs = arrayOf(WorkoutType.KETTLEBELL.name)
        // Emite la instrucción SQL.
        val deletedRows = workoutsDb.delete(WorkoutEntry.TABLE_NAME, selection, selectionArgs)
    }

    suspend fun getWorkoutsFilteredByType(type: WorkoutType) {
        val db = workoutsDb

        // Define una proyección que especifica qué columnas de la base de datos
        // utilizarás realmente después de esta consulta.
        val projection = arrayOf(
            BaseColumns._ID,
            WorkoutEntry.COLUMN_NAME_WORKOUT_NAME,
            WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE
        )

        // Filtra resultados WHERE "type" = 'Cardio'
        val selection = "${WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE} = ?"
        val selectionArgs = arrayOf(type.name)

        // Como serán ordenados los resultados en el Cursor resultante
        val sortOrder = "${WorkoutEntry.COLUMN_NAME_WORKOUT_NAME} DESC"

        val cursor = db.query(
            WorkoutEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val workouts = mutableListOf<Workout>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(WorkoutEntry.COLUMN_NAME_WORKOUT_NAME))
                val type = getString(getColumnIndexOrThrow(WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE))
                workouts.add(
                    Workout(
                        name = name,
                        workoutType = WorkoutType.valueOf(type),
                        description = "",
                        muscles = listOf()
                    )
                )
            }
        }
        cursor.close()
    }

    suspend fun getWorkouts(): Flow<List<Workout>> {
        return withContext(IO) {
            workoutDao.getAllWorkouts().map { workouts ->
                workouts.map { workout -> workout.toWorkout() }
            }
        }
    }

    suspend fun addRandomWorkouts() {
        withContext(IO) {
            (1..10).forEach { id ->
                delay(1000)
                val workoutEntity = WorkoutEntity(
                    name = "TRX $id",
                    workoutType = WorkoutType.TRX,
                    description = "Sentadillas con TRX $id",
                    muscles = listOf(Muscle.QUADS),
                    image = "trx",
                    date = Date().toString(),
                    done = false
                )
                workoutDao.insertWorkout(workoutEntity)
            }
        }
    }

    suspend fun deleteAllWorkouts() {
        withContext(IO) {
            workoutDao.deleteAll()
        }
    }
}