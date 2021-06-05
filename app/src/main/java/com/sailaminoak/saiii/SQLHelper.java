package com.sailaminoak.saiii;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {

    public String cat="name";
    public String place="gg";
    public SQLHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public boolean queryData(String sql){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(sql);}
        catch (Exception e){
            return false;
        }
        return true;
    }
    public boolean insertData(String name,byte[] image,int ids,String nameOfTable){
        try{
            SQLiteDatabase db=getWritableDatabase();
            String name1="name";
            String image1="image";
            String ids1="ids";

            ContentValues values = new ContentValues();
            values.put(name1,name);
            values.put(image1, image);
            values.put(ids1,ids);
            db.insert(nameOfTable,null,values);


        }
       catch (Exception e){
           return false;
       }
       return true;
    }


    public Cursor getData(String sql){
        SQLiteDatabase db=getWritableDatabase();
        return db.rawQuery(sql,null );
    }
    public boolean update(String newName,int id,String nameOfTable){
        SQLiteDatabase Db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);

        if( Db.update(nameOfTable, values,   "ids" +" = '" + id + "'", null)==-1){
            return false;
        }
        return true;
    }
    public void delete(int id,String TBL_NAME) {
        getWritableDatabase().execSQL("delete from "+TBL_NAME+" where ids='"+id+"'");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
