package com.example.philipricedealership._utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "MovieBooking.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropDbs(new String[]{"product", "user", "order"});
    }
    public void checkTableExist() {
        SQLiteDatabase db = this.getWritableDatabase();
        String checkUserTable = "CREATE TABLE IF NOT EXISTS user ( uid INTEGER PRIMARY KEY AUTOINCREMENT, state INTEGER, image TEXT, email TEXT, username TEXT, password TEXT, address TEXT, cart TEXT);";
        String checkProductTable = "CREATE TABLE IF NOT EXISTS product ( uid INTEGER PRIMARY KEY AUTOINCREMENT, imgUrl TEXT, name TEXT, description TEXT, price REAL);";
        String checkOrderTable = "CREATE TABLE IF NOT EXISTS orders ( uid INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, total REAL, items TEXT, date TEXT);";
        db.execSQL(checkOrderTable);
        db.execSQL(checkProductTable);
        db.execSQL(checkUserTable);
    }

    public boolean dropDbs( String[] dbNames) {
        SQLiteDatabase db = getWritableDatabase();
        for (String dbName : dbNames)
            db.execSQL(String.format("drop Table if exists %s", dbName));
        return true;
    }

    public boolean truncateDbs(String[] dbNames) {
        SQLiteDatabase db = getWritableDatabase();
        for (String dbName : dbNames)
            db.execSQL(String.format("DELETE FROM %s", dbName));
        db.close();
        return true;
    }
    public Cursor execRawQuery(String query, String[] args) {
        Cursor result;
        try {
            result = this.getWritableDatabase().rawQuery(query, args);
        } catch (Exception e) {
            System.out.println("ERRR RAW Q : "+e);
            return null;
        }
        return result;
    }
    public boolean insert(ContentValues values, String table){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(table, null, values);
        return result != -1 ;
    }
    public boolean update(ContentValues values, String condition, String table){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.update(table, values, condition , null);
        return result != -1 ;
    }

}