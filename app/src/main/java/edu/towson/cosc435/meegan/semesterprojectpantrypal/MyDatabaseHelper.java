package edu.towson.cosc435.meegan.semesterprojectpantrypal;

import static java.sql.DriverManager.println;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PantryPal.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String USERS_FILE = "users.txt";
    public static final String TABLE_ITEMS = "items";
    public static final String COL_ITEM_ID = "id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_ITEM_NAME = "name";
    public static final String COL_ITEM_CATEGORY = "category";
    public static final String COL_ITEM_QUANTITY = "quantity";
    public static final String COL_ITEM_EXPIRATION_DATE = "expiration_date";
    private Context mContext;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_ID + " INTEGER PRIMARY KEY,"
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
                "FOREIGN KEY (" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + " (" + COL_ID + "));";
        db.execSQL(createItemsTable);
    }

    public int createUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        // Add other user data as needed, such as email
        // values.put(COL_EMAIL, email);
        long userId = db.insert(TABLE_USERS, null, values);
        db.close();
        return (int) userId;
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
        Log.d("\n\nNEW ITEM: \n\n", item.toString());
        db.close();

    }

    public List<Item> getItemsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> items = new ArrayList<>();
        Cursor cursor = db.query(TABLE_ITEMS, null, COL_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Item item = new Item(
                        cursor.getInt(cursor.getColumnIndex(COL_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_ITEM_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COL_ITEM_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(COL_ITEM_EXPIRATION_DATE))
                );
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
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


    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean result = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return result;
    }

}



