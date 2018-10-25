package com.example.mft12.pocket

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {
    val dbName = "PocketApp"
    val dbTable = "Pocket"
    val colID = "ID"
    val colName = "Name"
    val colMoney = "Money"
    val dbVersion = 1

    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable ( $colID INTEGER PRIMARY KEY, $colName TEXT, $colMoney FLOAT);"
    var sqlDb: SQLiteDatabase?=null

    constructor(context: Context) {
        var db = DatabaseHelperPocket(context)
        sqlDb = db.writableDatabase
    }

    inner class DatabaseHelperPocket:SQLiteOpenHelper {

        var context:Context?=null
        constructor(context:Context):super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(context, "Database created successfully", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS $dbName")

        }

    }

    fun insert(Name:String, Money:Double):Long {
        val values = ContentValues()
        values.put(colName, Name)
        values.put(colMoney, Money)

        val ID = sqlDb!!.insert(dbTable,"",values)
        return ID
    }

    fun read(context: Context):ArrayList<Member> {

        val members = ArrayList<Member>()
        val projection = arrayOf(colName, colMoney)
        val selectionArgs = arrayOf(colName)
        var cursor: Cursor?=null
        try {
            cursor = sqlDb!!.rawQuery("select * from ${dbTable}", null)
        } catch (e: SQLiteException) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            sqlDb!!.execSQL(sqlCreateTable)
            return ArrayList()
        }
        var ID:Int
        var name:String
        var Money:Double
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast) {
                ID = cursor.getInt(cursor.getColumnIndex(colID))
                name = cursor.getString(cursor.getColumnIndex(colName))
                Money = cursor.getDouble(cursor.getColumnIndex(colMoney))

                members.add(Member(ID,name, Money))
                cursor.moveToNext()
            }
        }
        return members
    }
}