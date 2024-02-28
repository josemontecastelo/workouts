package com.josealfonsomora.workouts.data.repositories

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.josealfonsomora.workouts.R
import com.josealfonsomora.workouts.data.sources.sqlite.WorkoutSqliteContract
import com.josealfonsomora.workouts.data.sources.sqlite.WorkoutSqliteContract.WorkoutEntry
import com.josealfonsomora.workouts.domain.Muscle
import com.josealfonsomora.workouts.domain.Workout
import com.josealfonsomora.workouts.domain.WorkoutType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class DailyWorkoutsRepository @Inject constructor(
    private val workoutsDb: SQLiteDatabase,
) {

    suspend fun setupDailyWorkoutsDB(){
        withContext(IO) {
            val cursor = workoutsDb.rawQuery("SELECT Count(*) FROM ${WorkoutEntry.TABLE_NAME}", null)
            cursor.moveToFirst()
            val count = cursor.getInt(0)
            cursor.close()

            if(count <=0){
                val date = Date().toString()
                val values = ContentValues().apply {
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_NAME, "Cinta 30min")
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE, WorkoutType.CARDIO.name)
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_DESCRIPTION, "Correr en cinta durante 30 minutos")
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_MUSCLES, Muscle.QUADS.name)
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_IMAGE, "cardio")
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_DATE, date)
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_DONE, false)
                }
                workoutsDb.insert(WorkoutEntry.TABLE_NAME, null, values)

                workoutsDb.insert(WorkoutEntry.TABLE_NAME, null, ContentValues().apply {
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_NAME, "Sentadillas con Kettlebell")
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE, WorkoutType.KETTLEBELL.name)
                    put(WorkoutEntry.COLUMN_NAME_WORKOUT_DESCRIPTION, "Sentidas con Kettlebell de 10kg")
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
                WorkoutEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
            val workouts = mutableListOf<Workout>()
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(WorkoutEntry.COLUMN_NAME_WORKOUT_NAME))
                val type = cursor.getString(cursor.getColumnIndex(WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE))
                val description = cursor.getString(cursor.getColumnIndex(WorkoutEntry.COLUMN_NAME_WORKOUT_DESCRIPTION))
                val muscles = cursor.getString(cursor.getColumnIndex(WorkoutEntry.COLUMN_NAME_WORKOUT_MUSCLES)).split(",").map { Muscle.valueOf(it) }
                workouts.add(Workout(name, WorkoutType.valueOf(type), description, muscles))
            }
            cursor.close()
            workouts
        }
    }

    suspend fun deleteKettleBellWorkouts(){
        // Define la parte 'where' de la consulta.
        val selection = "${WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE} LIKE ?"
        // Especifica los argumentos en el orden del marcador de posición ?.
        val selectionArgs = arrayOf(WorkoutType.KETTLEBELL.name)
        // Emite la instrucción SQL.
        val deletedRows = workoutsDb.delete(WorkoutEntry.TABLE_NAME, selection, selectionArgs)
    }

    suspend fun getWorkoutsFilteredByType(type: WorkoutType){
        val db = workoutsDb

        // Define una proyección que especifica qué columnas de la base de datos
        // utilizarás realmente después de esta consulta.
        val projection = arrayOf(
            BaseColumns._ID, WorkoutEntry.COLUMN_NAME_WORKOUT_NAME,
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
                val name = getString(getColumnIndex(WorkoutEntry.COLUMN_NAME_WORKOUT_NAME))
                val type = getString(getColumnIndex(WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE))
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
}