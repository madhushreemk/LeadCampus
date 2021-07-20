package com.leadcampusapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LeadDb";
    public static final String TABLE_NAME = "LeadDb_table";
    public static final String STATEMASTER = "statemaster";
    public static final String DISTRICTMASTER = "districtmaster";
    public static final String CITYMASTER = "citymaster";
    public static final String COLLEGEMASTER = "collegemaster";
    public static final String PROGRAMMEMASTER = "programmemaster";
    public static final String COURSEMASTER = "coursemaster";
    public static final String SEMMASTER = "semmaster";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,MOBILE TEXT, EMAIL TEXT, DOB TEXT, PHOTO TEXT)");
        //db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,APPLICATIONID TEXT,NAME TEXT,STATUS TEXT,PROGRAM TEXT,HEIGHT TEXT,WEIGHT TEXT,HB TEXT,BG TEXT,AGE TEXT,GENDER TEXT,BMI TEXT,BMR TEXT,PHOTO TEXT)");

        //db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,APPLICATIONID TEXT,NAME TEXT,STATUS TEXT,PROGRAM TEXT)");

        Log.d("Insidexxxxx","Oncreate");

        String tab_StateMaster = "Create table "+STATEMASTER+" (STATEID INTEGER PRIMARY KEY, STATENAME TEXT, STATUS TEXT)";
        String tab_DistrictMaster = "Create table "+DISTRICTMASTER+" (DISTRICTID INTEGER PRIMARY KEY, STATEID INTEGER, DISTRICTNAME TEXT, FOREIGN KEY(STATEID) REFERENCES "+STATEMASTER+"(STATEID))";
        String tab_CityMaster = "Create table "+CITYMASTER+" (CITYID INTEGER PRIMARY KEY, DISTRICTID INTEGER, CITYNAME TEXT, FOREIGN KEY(DISTRICTID) REFERENCES "+DISTRICTMASTER+"(DISTRICTID))";
        String tab_CollegeMaster = "Create table "+COLLEGEMASTER+" (COLLEGEID INTEGER PRIMARY KEY, CITYID INTEGER, COLLEGENAME TEXT, FOREIGN KEY(CITYID) REFERENCES "+CITYMASTER+"(CITYID))";
        String tab_ProgrammeMaster = "Create table "+PROGRAMMEMASTER+" (PROGRAMMEID INTEGER PRIMARY KEY, PROGRAMMENAME TEXT)";
        String tab_CourseMaster = "Create table "+COURSEMASTER+" (COURSEID INTEGER PRIMARY KEY, PROGRAMMEID INTEGER, COURSENAME TEXT, FOREIGN KEY(PROGRAMMEID) REFERENCES "+PROGRAMMEMASTER+"(PROGRAMMEID))";
        String tab_SemesterMaster = "Create table "+SEMMASTER+" (SEMID INTEGER PRIMARY KEY, SEMNAME TEXT)";


        db.execSQL(tab_StateMaster);
        db.execSQL(tab_DistrictMaster);
        db.execSQL(tab_CityMaster);
        db.execSQL(tab_CollegeMaster);
        db.execSQL(tab_ProgrammeMaster);
        db.execSQL(tab_CourseMaster);
        db.execSQL(tab_SemesterMaster);

        Log.d("Insidexxxxx","After Table create");

        //db.execSQL("create table " + "Filepath" +" (APPLICATIONID TEXT PRIMARY KEY,FILENAME TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertApplication(String applicationId, String name, String status, String program) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("APPLICATIONID",applicationId);
        contentValues.put("NAME",name);
        contentValues.put("STATUS",status);
        contentValues.put("PROGRAM",program);
        /*contentValues.put("HEIGHT","");
        contentValues.put("WEIGHT","");
        contentValues.put("HB","");
        contentValues.put("BG","");
        contentValues.put("AGE","");
        contentValues.put("GENDER","");
        contentValues.put("BMI","");
        contentValues.put("BMR","");
        contentValues.put("PHOTO","");*/

        long result = db.insert(TABLE_NAME,null,contentValues);

        Log.d("Result is", String.valueOf(result));

        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean insertIntoStateDB(int stateId, String stateName, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("STATEID",stateId);
        contentValues.put("STATENAME",stateName);
        contentValues.put("STATUS",status);

        long result = db.insert(STATEMASTER,null,contentValues);
        Log.d("Resultxxxxx is", String.valueOf(result));

        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertIntoDistrictDB(int distId, int stateId, String distName, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("DISTRICTID",distId);
        contentValues.put("STATEID",stateId);
        contentValues.put("DISTRICTNAME",distName);

        long result = db.insert(DISTRICTMASTER,null,contentValues);
        Log.d("Resultxxxxx is", String.valueOf(result));

        if(result == -1)
            return false;
        else
            return true;
    }



    public Cursor getStateData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+STATEMASTER,null);
        return res;
    }

    public Cursor getDistrictInfo(int stateid) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.query(DISTRICTMASTER, new String[] {
                        "DISTRICTID", "STATEID", "DISTRICTNAME"}, "STATEID" + "=?",
                new String[] { String.valueOf(stateid)}, null, null, null, null);

        Log.d("count", String.valueOf(res.getCount()));

        return res;
    }








/*    public boolean insertFilePath(String applicationId, String fileName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("APPLICATIONID",applicationId);
        contentValues.put("FILENAME",fileName);


        long result = db.insert("Filepath",null,contentValues);

        Log.d("Result is", String.valueOf(result));

        if(result == -1)
            return false;
        else
            return true;
    }*/







    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }

    public Cursor getApplicationInfo(String applicationId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.query("applicationForm_tabless", new String[] {
                        "APPLICATIONID", "NAME", "STATUS", "PROGRAM" }, "APPLICATIONID" + "=?",
                new String[] { String.valueOf(applicationId)}, null, null, null, null);

        Log.d("count", String.valueOf(res.getCount()));

        return res;
    }

   /* public Cursor getFileName(String applicationId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.query("Filepath", new String[] {
                        "FILENAME" }, "APPLICATIONID" + "=?",
                new String[] { String.valueOf(applicationId)}, null, null, null, null);

        Log.d("count", String.valueOf(res.getCount()));

        return res;
    }*/

}