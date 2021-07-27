package com.example.abcdialogue.Bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "username")
    val userName: String?,
    @ColumnInfo(name = "password")
    val passWord: String?
){
    @PrimaryKey
    var id = 0
}
