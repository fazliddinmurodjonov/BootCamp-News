package com.example.androiddatabaselesson2bootcamp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androiddatabaselesson2bootcamp.models.BSWType
import com.example.androiddatabaselesson2bootcamp.models.Basic
import com.example.androiddatabaselesson2bootcamp.models.Social
import com.example.androiddatabaselesson2bootcamp.models.World
import com.example.androiddatabaselesson2bootcamp.util.Constant

class BootcampDb(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.VERSION), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val basicQuery =
            "CREATE TABLE ${Constant.BASIC_TABLE} (${Constant.BASIC_ID}  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,${Constant.BASIC_TITLE} TEXT NOT NULL, ${Constant.BASIC_TEXT} TEXT NOT NULL)"
        val worldQuery =
            "CREATE TABLE ${Constant.WORLD_TABLE} (${Constant.WORLD_ID}  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,${Constant.WORLD_TITLE} TEXT NOT NULL, ${Constant.WORLD_TEXT} TEXT NOT NULL)"
        val socialQuery =
            "CREATE TABLE ${Constant.SOCIAL_TABLE} (${Constant.SOCIAL_ID}  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,${Constant.SOCIAL_TITLE} TEXT NOT NULL, ${Constant.SOCIAL_TEXT} TEXT NOT NULL)"

        db?.execSQL(basicQuery)
        db?.execSQL(worldQuery)
        db?.execSQL(socialQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun insertBasic(basic: BSWType) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.BASIC_TITLE, basic.title)
        contentValues.put(Constant.BASIC_TEXT, basic.text)
        database.insert(Constant.BASIC_TABLE, null, contentValues)
        database.close()
    }

    override fun insertWorld(world: BSWType) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.WORLD_TITLE, world.title)
        contentValues.put(Constant.WORLD_TEXT, world.text)
        database.insert(Constant.WORLD_TABLE, null, contentValues)
        database.close()
    }

    override fun insertSocial(social: BSWType) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.SOCIAL_TITLE, social.title)
        contentValues.put(Constant.SOCIAL_TEXT, social.text)
        database.insert(Constant.SOCIAL_TABLE, null, contentValues)
        database.close()
    }

    @SuppressLint("Recycle")
    override fun getBasicById(id: Int): BSWType {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.BASIC_TABLE,
            arrayOf(Constant.BASIC_ID, Constant.BASIC_TITLE, Constant.BASIC_TEXT),
            "${Constant.BASIC_ID} = ?",
            arrayOf("$id"), null, null, null

        )
        cursor?.moveToFirst()
        return BSWType(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
    }

    @SuppressLint("Recycle")
    override fun getWorldById(id: Int): BSWType {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.WORLD_TABLE,
            arrayOf(Constant.WORLD_ID, Constant.WORLD_TITLE, Constant.WORLD_TEXT),
            "${Constant.WORLD_ID} = ?",
            arrayOf("$id"), null, null, null
        )
        cursor?.moveToFirst()
        return BSWType(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
    }

    @SuppressLint("Recycle")
    override fun getSocialById(id: Int): BSWType {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.SOCIAL_TABLE,
            arrayOf(Constant.SOCIAL_ID, Constant.SOCIAL_TITLE, Constant.SOCIAL_TEXT),
            "${Constant.SOCIAL_ID} = ?",
            arrayOf("$id"), null, null, null
        )
        cursor?.moveToFirst()
        return BSWType(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
    }

    override fun getAllBasic(): ArrayList<BSWType> {
        val basicList = ArrayList<BSWType>()
        val query = "select * from ${Constant.BASIC_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val basic = BSWType(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                basicList.add(basic)
            } while (cursor.moveToNext())
        }
        return basicList
    }

    override fun getAllWorld(): ArrayList<BSWType> {
        var worldList = ArrayList<BSWType>()
        val query = "select * from ${Constant.WORLD_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val world = BSWType(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                worldList.add(world)
            } while (cursor.moveToNext())
        }
        return worldList
    }

    override fun getAllSocial(): ArrayList<BSWType> {
        var socialList = ArrayList<BSWType>()
        val query = "select * from ${Constant.SOCIAL_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val social = BSWType(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                socialList.add(social)

            } while (cursor.moveToNext())
        }
        return socialList
    }

    override fun getBasicCount(): Int {
        val query = "select * from ${Constant.BASIC_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        cursor.close()
        return cursor.count
    }

    override fun getWorldCount(): Int {
        val query = "select * from ${Constant.WORLD_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        cursor.close()
        return cursor.count
    }

    override fun getSocialCount(): Int {
        val query = "select * from ${Constant.SOCIAL_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        cursor.close()
        return cursor.count
    }

    override fun updateBasic(basic: BSWType): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.BASIC_ID, basic.id)
        contentValues.put(Constant.BASIC_TITLE, basic.title)
        contentValues.put(Constant.BASIC_TEXT, basic.text)
        return database.update(
            Constant.BASIC_TABLE, contentValues, "${Constant.BASIC_ID} = ?", arrayOf("${basic.id}")
        )
    }

    override fun updateWorld(world: BSWType): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.WORLD_ID, world.id)
        contentValues.put(Constant.WORLD_TITLE, world.title)
        contentValues.put(Constant.WORLD_TEXT, world.text)
        return database.update(
            Constant.WORLD_TABLE, contentValues, "${Constant.WORLD_ID} = ?", arrayOf("${world.id}")
        )
    }

    override fun updateSocial(social: BSWType): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.SOCIAL_ID, social.id)
        contentValues.put(Constant.SOCIAL_TITLE, social.title)
        contentValues.put(Constant.SOCIAL_TEXT, social.text)
        return database.update(
            Constant.SOCIAL_TABLE,
            contentValues,
            "${Constant.SOCIAL_ID} = ?",
            arrayOf("${social.id}")
        )
    }

    override fun deleteBasic(basic: BSWType) {
        val database = this.writableDatabase
        database.delete(Constant.BASIC_TABLE, "${Constant.BASIC_ID} = ? ", arrayOf("${basic.id}"))
        database.close()
    }

    override fun deleteWorld(world: BSWType) {
        val database = this.writableDatabase
        database.delete(Constant.WORLD_TABLE, "${Constant.WORLD_ID} = ? ", arrayOf("${world.id}"))
        database.close()
    }

    override fun deleteSocial(social: BSWType) {
        val database = this.writableDatabase
        database.delete(Constant.SOCIAL_TABLE,
            "${Constant.SOCIAL_ID} = ? ",
            arrayOf("${social.id}"))
        database.close()
    }
}