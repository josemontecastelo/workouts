package com.josealfonsomora.workouts.data.sources.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object WorkoutSqliteContract {
    object WorkoutEntry : BaseColumns {
        const val TABLE_NAME = "daily_workout"
        const val COLUMN_NAME_WORKOUT_NAME = "workout_name"
        const val COLUMN_NAME_WORKOUT_TYPE = "workout_type"
        const val COLUMN_NAME_WORKOUT_DESCRIPTION = "workout_description"
        const val COLUMN_NAME_WORKOUT_MUSCLES = "workout_muscles"
        const val COLUMN_NAME_WORKOUT_IMAGE = "workout_image"
        const val COLUMN_NAME_WORKOUT_DATE = "workout_date"
        const val COLUMN_NAME_WORKOUT_DONE = "workout_done"
    }
}

class WorkoutsDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "Workouts.db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_ENTRIES =
            """
            CREATE TABLE ${WorkoutSqliteContract.WorkoutEntry.TABLE_NAME} 
            (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${WorkoutSqliteContract.WorkoutEntry.COLUMN_NAME_WORKOUT_NAME} TEXT, 
            ${WorkoutSqliteContract.WorkoutEntry.COLUMN_NAME_WORKOUT_TYPE} TEXT, 
            ${WorkoutSqliteContract.WorkoutEntry.COLUMN_NAME_WORKOUT_DESCRIPTION} TEXT, 
            ${WorkoutSqliteContract.WorkoutEntry.COLUMN_NAME_WORKOUT_MUSCLES} TEXT, 
            ${WorkoutSqliteContract.WorkoutEntry.COLUMN_NAME_WORKOUT_IMAGE} INTEGER, 
            ${WorkoutSqliteContract.WorkoutEntry.COLUMN_NAME_WORKOUT_DATE} TEXT, 
            ${WorkoutSqliteContract.WorkoutEntry.COLUMN_NAME_WORKOUT_DONE} INTEGER)    
            """


        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${WorkoutSqliteContract.WorkoutEntry.TABLE_NAME}"

    }

}