package com.example.gourn.buzztracker;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class DB_Handler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CincoDeCinco.db";
    public static final String TABLE_USER = "User";
    public static final String TABLE_ADMIN = "Admin";
    public static final String TABLE_LOCEMP = "Location Employee";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";

    public DB_Handler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_EMAIL + " TEXT NOT NULL, " + COLUMN_PASSWORD + " TEXT NOT NULL)";
        String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "(" + COLUMN_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_EMAIL + " TEXT NOT NULL, " + COLUMN_PASSWORD + " TEXT NOT NULL)";
        String CREATE_ADMIN_LOCEMP = "CREATE TABLE " + TABLE_LOCEMP + "(" + COLUMN_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_EMAIL + " TEXT NOT NULL, " + COLUMN_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ADMIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public void addUser(Person newUser) {
        String table = "";
        if (newUser instanceof User) {
            table = TABLE_USER;
        } else if (newUser instanceof Admin) {
            table = TABLE_ADMIN;
        } else if (newUser instanceof LocationEmployee) {
            table = TABLE_LOCEMP;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, (newUser.getName()));
        values.put(COLUMN_EMAIL, (newUser.getEmail()));
        values.put(COLUMN_NAME, (newUser.getPassword()));
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table, null, values);
        db.close();
    }


}
