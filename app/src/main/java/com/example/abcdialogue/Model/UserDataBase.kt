package com.example.abcdialogue.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {

    /**
     * Connects the dataBase to the Dao
     */
    abstract val userDataBaseDao: UserDataBaseDao

    companion object{

        /**
         * 数据库单例
         */
        @Volatile
        private var INSTANCE: UserDataBase? = null


        /**
         * 获取数据库单例，如果为空就新创建一个
         */
        fun getInstance(context : Context): UserDataBase{
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "user_information"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this lesson. You can learn more about
                    // migration with Room in this blog post:
                    // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                    .fallbackToDestructiveMigration()
                    .build()
                // Assign INSTANCE to the newly created database.
                INSTANCE = instance
            }
            return instance
        }
    }
}