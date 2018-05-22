package com.simpleapp.jasur.newsqlexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Jasur on 22.05.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String DB_NAME = "text";
    public String TABLE_NAME = "text";
    public String KEY_ID = "id";
    public String KEY_TEXT = "text";
    public ArrayList<Integer> itemid;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TEXT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void add(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TEXT, text);
        db.insert(TABLE_NAME, null, cv);
    }

    public void update(int id, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TEXT, text);
        db.update(TABLE_NAME, cv, "id=" + id, null);
    }

    public ArrayList<String> all(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);
        ArrayList<String> al = new ArrayList<String>();
        String t = new String();
        itemid = new ArrayList<Integer>();
        if (c.moveToFirst()){
            int i=0;
            do{
                t = (c.getString(c.getColumnIndex("text")));
                itemid.add(c.getInt(c.getColumnIndex("id")));
                al.add(t);
            } while (c.moveToNext());
        }
        MainActivity.itemid = itemid;
        return al;
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"id="+id,null);
    }
}
