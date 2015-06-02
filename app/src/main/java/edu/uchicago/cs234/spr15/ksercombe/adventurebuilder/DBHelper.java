package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

/**
 * Created by katesercombe on 5/24/15.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AdventureBuilderDB.db";
    public static final String ADVENTURE_TABLE_NAME = "adventureList";
    public static final String ADVENTURE_COLUMN_ID = "id";
    public static final String ADVENTURE_COLUMN_DATE = "date";
    public static final String ADVENTURE_COLUMN_STORY = "story";

    private HashMap hp;
    public DBHelper (Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + ADVENTURE_TABLE_NAME + " (id integer primary key, date text, story text)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ADVENTURE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertAdventure (Adventure adventure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ADVENTURE_COLUMN_DATE, adventure.date.toString());
        contentValues.put(ADVENTURE_COLUMN_STORY, adventure.story);

        db.insert(ADVENTURE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + ADVENTURE_TABLE_NAME + " where id=" + id + " ", null);
        return res;
    }
    public ArrayList getAllAdventures()
    {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + ADVENTURE_TABLE_NAME, null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ADVENTURE_COLUMN_DATE)));
            res.moveToNext();
        }
        return array_list;
    }

    }
