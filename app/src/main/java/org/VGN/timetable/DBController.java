package org.VGN.timetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "checkboxesDb";
    public static final String TABLE_CHECKBOXES_MONDAY = "monday";
    public static final String TABLE_CHECKBOXES_TUESDAY = "tuesday";
    public static final String TABLE_CHECKBOXES_WEDNESDAY = "wednesday";
    public static final String TABLE_CHECKBOXES_THURSDAY = "thursday";
    public static final String TABLE_CHECKBOXES_FRIDAY = "friday";
    public static final String TABLE_CHECKBOXES_SATURDAY = "saturday";
    public static final String TABLE_CHECKBOXES_SUNDAY = "sunday";

    public static final String KEY_ID = "_id";
    public static final String KEY_TEXT = "text";
    public static final String KEY_STATE = "state";

    public DBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CHECKBOXES_MONDAY + "(" + KEY_ID
                + " integer primary key," + KEY_TEXT + " text," + KEY_STATE + " integer" + ")");
        db.execSQL("create table " + TABLE_CHECKBOXES_TUESDAY + "(" + KEY_ID
                + " integer primary key," + KEY_TEXT + " text," + KEY_STATE + " integer" + ")");
        db.execSQL("create table " + TABLE_CHECKBOXES_WEDNESDAY + "(" + KEY_ID
                + " integer primary key," + KEY_TEXT + " text," + KEY_STATE + " integer" + ")");
        db.execSQL("create table " + TABLE_CHECKBOXES_THURSDAY + "(" + KEY_ID
                + " integer primary key," + KEY_TEXT + " text," + KEY_STATE + " integer" + ")");
        db.execSQL("create table " + TABLE_CHECKBOXES_FRIDAY + "(" + KEY_ID
                + " integer primary key," + KEY_TEXT + " text," + KEY_STATE + " integer" + ")");
        db.execSQL("create table " + TABLE_CHECKBOXES_SATURDAY + "(" + KEY_ID
                + " integer primary key," + KEY_TEXT + " text," + KEY_STATE + " integer" + ")");
        db.execSQL("create table " + TABLE_CHECKBOXES_SUNDAY + "(" + KEY_ID
                + " integer primary key," + KEY_TEXT + " text," + KEY_STATE + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CHECKBOXES_MONDAY);
        db.execSQL("drop table if exists " + TABLE_CHECKBOXES_TUESDAY);
        db.execSQL("drop table if exists " + TABLE_CHECKBOXES_WEDNESDAY);
        db.execSQL("drop table if exists " + TABLE_CHECKBOXES_THURSDAY);
        db.execSQL("drop table if exists " + TABLE_CHECKBOXES_FRIDAY);
        db.execSQL("drop table if exists " + TABLE_CHECKBOXES_SATURDAY);
        db.execSQL("drop table if exists " + TABLE_CHECKBOXES_SUNDAY);

        onCreate(db);
    }
}
