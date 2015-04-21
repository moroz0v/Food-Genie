package morozovs.foodgenie.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import morozovs.foodgenie.models.MyPlaceInfo;

public class FoodGenieDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PLACE_REVIEW,
            MySQLiteHelper.COLUMN_PLACE_BLACKLISTED,
            MySQLiteHelper.COLUMN_PLACE_VISITED,
            MySQLiteHelper.COLUMN_PLACE_NAME
    };

    public FoodGenieDataSource() {
        dbHelper = new MySQLiteHelper(AppController.getInstance());
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void saveMyPlaceInfo(MyPlaceInfo myPlaceInfo) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_ID, myPlaceInfo.getPlace_id());
        values.put(MySQLiteHelper.COLUMN_PLACE_REVIEW, myPlaceInfo.getMyReview());
        values.put(MySQLiteHelper.COLUMN_PLACE_BLACKLISTED, myPlaceInfo.isBlacklisted() ? 1 : 0);
        values.put(MySQLiteHelper.COLUMN_PLACE_VISITED, myPlaceInfo.isVisited() ? 1 : 0);
        values.put(MySQLiteHelper.COLUMN_PLACE_NAME, myPlaceInfo.getName());

        open();

        database.insert(MySQLiteHelper.TABLE_MY_VISITED_PLACES, null, values);

        close();
    }

    public void updateMyPlaceInfo(MyPlaceInfo myPlaceInfo){

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PLACE_REVIEW, myPlaceInfo.getMyReview());
        values.put(MySQLiteHelper.COLUMN_PLACE_BLACKLISTED, myPlaceInfo.isBlacklisted() ? 1 : 0);
        values.put(MySQLiteHelper.COLUMN_PLACE_VISITED, myPlaceInfo.isVisited() ? 1 : 0);

        open();

        database.update(MySQLiteHelper.TABLE_MY_VISITED_PLACES, values, MySQLiteHelper.COLUMN_ID + "= '" + myPlaceInfo.getPlace_id()+"'", null);

        close();

    }

    public void deleteMyPlaceInfo(String placeId) {
        open();
        database.delete(MySQLiteHelper.TABLE_MY_VISITED_PLACES, MySQLiteHelper.COLUMN_ID
                + "='" + placeId +"'", null);
        close();
    }

    public ArrayList<MyPlaceInfo> getAllPlaces() {
        ArrayList<MyPlaceInfo> allPlaces = new ArrayList<MyPlaceInfo>();

        open();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MY_VISITED_PLACES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyPlaceInfo info = cursorToMyPlaceInfo(cursor);
            allPlaces.add(info);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return allPlaces;
    }

    private MyPlaceInfo cursorToMyPlaceInfo(Cursor cursor) {
        MyPlaceInfo placeInfo = new MyPlaceInfo();
        placeInfo.setPlace_id(cursor.getString(0));
        placeInfo.setMyReview(cursor.getString(1));
        placeInfo.setBlacklisted(cursor.getInt(2) == 1);
        placeInfo.setVisited(cursor.getInt(3) == 1);
        placeInfo.setName(cursor.getString(4));
        return placeInfo;
    }
}