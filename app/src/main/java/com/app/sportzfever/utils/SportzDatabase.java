package com.app.sportzfever.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 4/4/2016.
 */
public class SportzDatabase {
    private static final String TAG = "SportzDatabase";
    private static final String DATABASE_NAME = "Medicine_Detail";
    private static String DATABASE_TABLE;
    private static final int DATABASE_VERSION = 1;


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public SportzDatabase(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, "database", null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("create table medData(match_id text primary key,id text,team1Id text," +
                        "team2Id text)");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            onCreate(db);
        }
    }

    public SportzDatabase open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // ---closes the database---
    public void close() {
        DBHelper.close();
    }


    public void insertdata(String match_id,
                           String id, String team1Id,
                           String team2Id) {

        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("match_id", match_id);
        cv.put("team1Id", team1Id);
        cv.put("team2Id", team2Id);

        db.insert("medData", null, cv);

    }


    public Cursor fetchData(String match_id) {
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("select * from medData" + " where match_id = '"
                    + match_id + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
            //	this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public void updateData(String match_id,
                           String id, String team1Id, String team2Id) {
        //SQLiteDatabase data = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.e("id", "*****" + id);
        Log.e("match_id", "*****" + match_id);
        Log.e("team1Id", "*****" + team1Id);
        Log.e("team2Id", "*****" + team2Id);
        cv.put("id", id);
        cv.put("match_id", match_id);
        cv.put("team1Id", team1Id);
        cv.put("team2Id", team2Id);

        db.update("medData", cv, "match_id =\"" + match_id + "\"", null);
        //	this.close();
    }

    public void deleteData(String match_id) {
        //	SQLiteDatabase data = this.getReadableDatabase();
        db.delete("medData", "match_id ='" + match_id + "'", null);
        //this.close();
    }

    public void deleteTable() {
        //SQLiteDatabase data = this.getReadableDatabase();
        db.delete("medData", null, null);
        //this.close();
    }


}
