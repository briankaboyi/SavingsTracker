package com.example.savingstrackerapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.savingstrackerapp.data.db.Converters
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem

@Database(
    entities = [GoalSavingsItem::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class GoalSavingsDatabase: RoomDatabase(){
    abstract fun getGoalSavingsDao(): GoalSavingsDao

    companion object{
        @Volatile
        private var instance: GoalSavingsDatabase?=null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?:createDatabse(context).also{instance = it}
        }

        private fun createDatabse(context: Context) =
            Room.databaseBuilder(context.applicationContext,GoalSavingsDatabase::class.java,"GoalSavings.db").build()
    }
}
