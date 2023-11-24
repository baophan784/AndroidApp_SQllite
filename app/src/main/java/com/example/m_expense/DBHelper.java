package com.example.m_expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "M_expense.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }
    private void createTables(SQLiteDatabase db) {
        String query = "create table Users(U_id INTEGER primary key autoincrement, U_name TEXT, U_pass TEXT)";
        db.execSQL(query);
        query ="INSERT INTO Users (U_name,U_pass) VALUES('user1','123456')";
        db.execSQL(query);
        query ="INSERT INTO Users (U_name,U_pass) VALUES('user2','123456')";
        db.execSQL(query);
        query="create table Hikes(H_id INTEGER primary key autoincrement, " +
                "H_name TEXT, " +
                "H_location TEXT, " +
                "H_length TEXT, " +
                "H_difficult TEXT, " +
                "H_startdate TEXT," +
                "H_park Integer," +
                "H_description, " +
                "U_id integer REFERENCES Users(U_id));";
        db.execSQL(query);
        query ="create table Observations(" +
                "O_id INTEGER primary key autoincrement, " +
                "O_observation TEXT, " +
                "O_time Text," +
                "O_comment TEXT," +
                "H_id INTEGER REFERENCES Hikes(H_id))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropAndRecreate(db);
    }
    private void dropAndRecreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + "Users");
        db.execSQL("DROP TABLE IF EXISTS " + "Hikes");
        db.execSQL("DROP TABLE IF EXISTS " + "Observations");
        onCreate(db);}
    public static int User_ID;
    public boolean checkLogin(String username, String pass){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from Users where U_name=? and U_pass=?",new String[] {username,pass});
        if (cur.getCount()==1){
            if (cur.moveToFirst()) {
                User_ID=cur.getInt(0);
            }
            cur.close();
            return true;

        }else{
            return false;
        }
    }

    public void addHike(Hikes hike){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("H_name",hike.Name);
        values.put("H_location",hike.Location);
        values.put("H_length",hike.Length);
        values.put("H_difficult",hike.Difficult);
        values.put("H_startdate",hike.Fdate);
        values.put("H_park",hike.Park);
        values.put("H_description",hike.Desc);
        values.put("U_id",hike.U_id);
        db.insert("Hikes",null,values);
        db.close();
    }
    List<Hikes> getAllHikes(int id){
         String query = "SELECT * FROM  Hikes where U_id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        final List<Hikes> list = new ArrayList<>();
        final Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Hikes hike = new Hikes();
                    hike.H_id=cursor.getInt(0);
                    hike.Name=cursor.getString(1);
                    hike.Location=cursor.getString(2);
                    hike.Length=cursor.getString(3);
                    hike.Difficult=cursor.getString(4);
                    hike.Fdate=cursor.getString(5);
                    hike.Park=cursor.getInt(6);
                    hike.Desc=cursor.getString(7);
                    // Adding object to list
                    list.add(hike);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return list;
    }
    void updateHike(Hikes hike){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("H_name",hike.Name);
        values.put("H_location",hike.Location);
        values.put("H_length",hike.Length);
        values.put("H_difficult",hike.Difficult);
        values.put("H_startdate",hike.Fdate);
        values.put("H_park",hike.Park);
        values.put("H_description",hike.Desc);
        values.put("U_id",hike.U_id);
        db.update("Hikes",values,"H_id=?",new String[]{String.valueOf(hike.H_id)});
    }
    public long deleteHike(String hike_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Hikes", "H_id=?", new String[]{hike_id});
    }

    public void Add_Observation(Observations ob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("O_observation",ob.o_observation);
        values.put("O_time",ob.o_time);
        values.put("O_comment",ob.o_cmt);
        values.put("H_id",ob.h_id);
        db.insert("Observations",null,values);
    }
    public List<Observations> getObservation(int id){
        SQLiteDatabase db =this.getReadableDatabase();
        String query ="select * from Observations where H_id="+id;
        List<Observations> lst_observations = new ArrayList<>();
        Cursor cs = db.rawQuery(query,null);
        if(cs.moveToFirst()){
            do{
               Observations observation = new Observations();
                observation.o_id = cs.getInt(0);
                observation.o_observation=cs.getString(1);
                observation.o_time=cs.getString(2);
                observation.o_cmt = cs.getString(3);
                observation.h_id=cs.getInt(4);
                lst_observations.add(observation);
               }while (cs.moveToNext());
        }
        cs.close();
        db.close();
        return lst_observations;
    }
    Cursor getNewObservation(int id){
        SQLiteDatabase db =this.getReadableDatabase();
        String query ="select * from Observations where H_id="+id;
        Cursor cs = db.rawQuery(query,null);
        return cs;
    }

    void updateObservation(Observations observation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("O_id",observation.o_id);
        values.put("O_observation",observation.o_observation);
        values.put("O_time",observation.o_time);
        values.put("O_comment",observation.o_cmt);
        values.put("H_id",observation.h_id);
        db.update("Observations", values, "O_id=?",new String[]{String.valueOf(observation.o_id)});
    }
    public long deleteObservation(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Observations", "O_id=?", new String[]{id});
    }

    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        dropAndRecreate(db);
    }



}
