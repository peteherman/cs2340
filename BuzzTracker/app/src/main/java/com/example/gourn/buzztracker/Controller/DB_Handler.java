package com.example.gourn.buzztracker.Controller;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.gourn.buzztracker.Model.LocationEmployee;
import com.example.gourn.buzztracker.Model.Admin;
import com.example.gourn.buzztracker.Model.Location;
import com.example.gourn.buzztracker.Model.Person;
import com.example.gourn.buzztracker.Model.User;

public class DB_Handler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CincoDeCinco.db";
    private static final String TABLE_PERSON = "Person";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_USER_TYPE = "UserType";

    public SQLiteDatabase db;

    public DB_Handler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSON_TABLE = "CREATE TABLE " + TABLE_PERSON + "(" + COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_USER_TYPE + " INTEGER)";
//        String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "(" + COLUMN_NAME + " TEXT, " +
//                COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_PASSWORD + " TEXT NOT NULL)";
//        String CREATE_ADMIN_LOCEMP = "CREATE TABLE " + TABLE_LOCEMP + "(" + COLUMN_NAME + " TEXT, " +
//                COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_PASSWORD + " TEXT NOT NULL)";
        String CREATE_LOCATION = "CREATE TABLE Location (Name TEXT, Latitude TEXT, Longitude TEXT," +
                "Address TEXT, Type TEXT, PhoneNum TEXT, Website TEXT)";
//        db.execSQL(CREATE_USER_TABLE);
//        db.execSQL(CREATE_ADMIN_TABLE);
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public void addUser(Person newUser) {
        String table = TABLE_PERSON;
//        if (newUser instanceof User) {
//            table = TABLE_USER;
//        } else if (newUser instanceof Admin) {
//            table = TABLE_ADMIN;
//        } else if (newUser instanceof LocationEmployee) {
//            table = TABLE_LOCEMP;
//        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, (newUser.getName()));
        values.put(COLUMN_EMAIL, (newUser.getEmail()));
        values.put(COLUMN_USER_TYPE, newUser.getUserType().ordinal());
        db.insert(table, null, values);
    }

    public Person loadUser(String email) {
        String query = "SELECT * FROM " + TABLE_PERSON +" WHERE " + COLUMN_EMAIL + "= '"
                + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        Person currentUser = new Person("", "", null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            currentUser.setName(cursor.getString(0));
            currentUser.setEmail(cursor.getString(1));
            cursor.close();
        }
        return currentUser;
    }

//    public void csvParse(Context context) throws IOException {
////        String x = System.getProperty("user.dir");
////        x += "/location_dataa.csv";
////        URL path = DB_Handler.class.getResource(x);
////        File f = new File(path.getFile());
////        Scanner scan = new Scanner(f);
////        scan.useDelimiter("\n");
////        scan.next();
//        ArrayList<String> lines = new ArrayList<>();
//
////        while (scan.hasNext()) {
////            lines.add(scan.next());
////        }
////        FileReader file = new FileReader("location_dataa.csv");
////        BufferedReader buffer = new BufferedReader(file);
////
//        InputStream is = context.getResources().openRawResource(R.raw.locationdata);
//        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
//        while (buffer.readLine() != null) {
//            lines.add(buffer.readLine());
//        }
//        String[][] data = new String[lines.size()][11];
//
//        for (int i = 0; i < data.length; i++) {
//            String[] currLine = lines.get(i).split(",");
//            for (int j = 0; j < data[i].length; j++) {
//                data[i][j] = currLine[j];
//            }
//        }
//        String columns = "Name, Latitude, Longitude, Address, Type, PhoneNum, Website";
//        String str1 = "INSERT INTO Location" + " (" + columns + ") values(";
//        String str2 = ");";
//        db.beginTransaction();
//        for (int i = 0; i < data.length; i++) {
//            StringBuilder sb = new StringBuilder(str1);
//            sb.append("'" + data[i][1] + ",");
//            sb.append(data[i][2] + "',");
//            sb.append(data[i][3] + "',");
//            sb.append(data[i][4] + ", " + data[i][5] + ", " + data[i][6] + "',");
//            sb.append(data[i][7] + "',");
//            sb.append(data[i][8] + "',");
//            sb.append(data[i][9] + "'");
//            sb.append(str2);
//            db.execSQL(sb.toString());
//        }
//        db.setTransactionSuccessful();
//        db.endTransaction();
//    }

    public Location[] getAllLocations() {
        String query = "SELECT * FROM Location";
        Cursor cursor = db.rawQuery(query, null);
        Location[] locations = new Location[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("Name"));
            String lat = cursor.getString(cursor.getColumnIndex("Latitude"));
            String longi = cursor.getString(cursor.getColumnIndex("Longitude"));
            String address = cursor.getString(cursor.getColumnIndex("Address"));
            String type = cursor.getString(cursor.getColumnIndex("Type"));
            String phone = cursor.getString(cursor.getColumnIndex("PhoneNum"));
            String website = cursor.getString(cursor.getColumnIndex("Website"));
            Location loc =
                    new Location(name, lat, longi, address, type, phone, website);
            locations[i] = loc;
            i++;
        }
        cursor.close();
        return locations;
    }

    public void clearLocations() {
        String del = "DELETE FROM Location";
        db.execSQL(del);
    }
}
