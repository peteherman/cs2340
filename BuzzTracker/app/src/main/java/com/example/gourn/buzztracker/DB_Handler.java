package com.example.gourn.buzztracker;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DB_Handler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CincoDeCinco.db";
    public static final String TABLE_USER = "User";
    public static final String TABLE_ADMIN = "Admin";
    public static final String TABLE_LOCEMP = "Location Employee";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";

    public SQLiteDatabase db;

    public DB_Handler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_PASSWORD + " TEXT NOT NULL)";
        String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "(" + COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_PASSWORD + " TEXT NOT NULL)";
        String CREATE_ADMIN_LOCEMP = "CREATE TABLE " + TABLE_LOCEMP + "(" + COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_PASSWORD + " TEXT NOT NULL)";
        String CREATE_LOCATION = "CREATE TABLE Location(Name TEXT, Latitude TEXT, Longitude TEXT," +
                "Address TEXT, Type TEXT, PhoneNum TEXT, Website TEXT)";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ADMIN_TABLE);
        db.execSQL(CREATE_LOCATION);
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
        values.put(COLUMN_PASSWORD, (newUser.getPassword()));
        db.insert(table, null, values);
    }

    public Person loadUser(String email) {
        String query = "SELECT * FROM " + TABLE_USER +" WHERE " + COLUMN_EMAIL + "= '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        Person currentUser = new Person("", "", "");
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            currentUser.setName(cursor.getString(0));
            currentUser.setEmail(cursor.getString(1));
            currentUser.setPassword(cursor.getString(2));
            cursor.close();
        }
        return currentUser;
    }

    public void csvParse() throws FileNotFoundException {
        URL path = DB_Handler.class.getResource("LocationData.csv");
        File f = new File(path.getFile());
        Scanner scan = new Scanner(f);
        scan.useDelimiter("\n");
        scan.next();
        ArrayList<String> lines = new ArrayList<>();

        while (scan.hasNext()) {
            lines.add(scan.next());
        }

        String[][] data = new String[lines.size()][11];

        for (int i = 0; i < data.length; i++) {
            String[] currLine = lines.get(i).split(",");
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = currLine[j];
            }
        }
        String columns = "Name, Latitude, Longitude, Address, Type, PhoneNum, Website";
        String str1 = "INSERT INTO Location" + " (" + columns + ") values(";
        String str2 = ");";
        for (int i = 0; i < data.length; i++) {
            StringBuilder sb = new StringBuilder(str1);
            sb.append("'" + data[i][1] + ",");
            sb.append(data[i][2] + "',");
            sb.append(data[i][3] + "',");
            sb.append(data[i][4] + ", " + data[i][5] + ", " + data[i][6] + "',");
            sb.append(data[i][7] + "',");
            sb.append(data[i][8] + "',");
            sb.append(data[i][9] + "'");
            sb.append(str2);
            db.execSQL(sb.toString());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public com.example.gourn.buzztracker.Location[] getAllLocations() {
        String query = "SELECT * FROM Location";
        Cursor cursor = db.rawQuery(query, null);
        com.example.gourn.buzztracker.Location[] locations = new com.example.gourn.buzztracker.Location[cursor.getCount()];
        cursor.moveToFirst();
        int i = 0;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("Name"));
            String lat = cursor.getString(cursor.getColumnIndex("Latitude"));
            String longi = cursor.getString(cursor.getColumnIndex("Longitude"));
            String address = cursor.getString(cursor.getColumnIndex("Address"));
            String type = cursor.getString(cursor.getColumnIndex("Type"));
            String phone = cursor.getString(cursor.getColumnIndex("PhoneNum"));
            String website = cursor.getString(cursor.getColumnIndex("Website"));
            com.example.gourn.buzztracker.Location loc =
                    new com.example.gourn.buzztracker.Location(name, lat, longi, address, type, phone, website);
            locations[i] = loc;
            i++;
        }
        cursor.close();
        return locations;
    }
}
