package com.example.teachermanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class StudentDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";

    public StudentDatabase(Context context)
    {
        super(context,DATABASE_NAME,null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE contacts ( _id INTEGER PRIMARY KEY"+ " AUTOINCREMENT, name TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}
