package com.example.abcdialogue.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "user_name")
    val userName: String?,
    @ColumnInfo(name = "pass_word")
    val passWord: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
