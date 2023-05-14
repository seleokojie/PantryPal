package edu.towson.cosc435.meegan.semesterprojectpantrypal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PantryPal.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USERNAME = "username";

    private static final String COL_PASSWORD = "password";
    public static final String TABLE_ITEMS = "items";
    public static final String COL_ITEM_ID = "id";
    public static final String COL_ITEM_NAME = "name";
    public static final String COL_ITEM_CATEGORY = "category";
    public static final String COL_ITEM_QUANTITY = "quantity";
    public static final String COL_ITEM_EXPIRATION_DATE = "expiration_date";
    private Context mContext;

    public MyDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        insertUsersFromResources(getWritableDatabase());
        displayXMLvalues();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_USER_ID + " INT,"
                + COL_USERNAME + " TEXT,"
                + COL_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
        insertUsersFromResources(db);
        String createItemsTable = "CREATE TABLE " + TABLE_ITEMS + " (" +
                COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_ID + " INTEGER, " +
                COL_ITEM_NAME + " TEXT, " +
                COL_ITEM_CATEGORY + " TEXT, " +
                COL_ITEM_QUANTITY + " TEXT, " +
                COL_ITEM_EXPIRATION_DATE + " TEXT, " +
                "FOREIGN KEY (" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + " (" + COL_USER_ID + "));";
        db.execSQL(createItemsTable);
        displayXMLvalues();
    }
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, item.getUserId());
        values.put(COL_ITEM_NAME, item.getName());
        values.put(COL_ITEM_CATEGORY, item.getCategory());
        values.put(COL_ITEM_QUANTITY, item.getQuantity());
        values.put(COL_ITEM_EXPIRATION_DATE, item.getExpirationDate());
        db.insert(TABLE_ITEMS, null, values);
        Log.d("NEW ITEM:", item.toString());
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement database upgrade logic here
    }
    private void insertUsersFromResources(SQLiteDatabase db) {

        String[] users = mContext.getResources().getStringArray(R.array.users_array);
        String[] passwords = mContext.getResources().getStringArray(R.array.passwords_array);

        for (int i = 0; i < users.length; i++) {
            ContentValues values = new ContentValues();
            values.put(COL_USERNAME, users[i]);
            values.put(COL_PASSWORD, passwords[i]);
            db.insert(TABLE_USERS, null, values);
        }

    }
    private void displayXMLvalues() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USERNAME,COL_PASSWORD};
        Cursor cursor = db.query(TABLE_USERS, columns, null, null, null, null, null);

        Set<String> usernames = new HashSet<>();
        Set<String> passwords = new HashSet<>();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COL_USERNAME));
                usernames.add(username);
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
                passwords.add(password);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        Log.d("USERNAMEVALUES", usernames.toString());
        Log.d("PASSWORDVALUES", passwords.toString());
    }



    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USER_ID};
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean result = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return result;
    }

}



