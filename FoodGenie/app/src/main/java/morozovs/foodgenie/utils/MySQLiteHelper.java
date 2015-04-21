package morozovs.foodgenie.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sergeymorozov on 6/26/14.
 */

/*PlaceInfo*/
//public String place_id;
//public String reference;
//public int price_level;
//public String name;
//public double rating;
//public String[] types;
//public String formatted_address;
//public OpeningHours opening_hours;
//public boolean isSelected;

/*MyPlaceInfo*/
//public String myReview;
//public double myRating;
//public boolean isBlacklisted;
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FoodGenie.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_MY_VISITED_PLACES = "my_visited_places";

    public static final String COLUMN_ID = "my_place_id";
    public static final String COLUMN_PLACE_REVIEW = "my_place_review";
    public static final String COLUMN_PLACE_BLACKLISTED = "my_place_blacklisted";
    public static final String COLUMN_PLACE_VISITED = "my_place_visited";
    public static final String COLUMN_PLACE_NAME = "my_place_name";


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MY_VISITED_PLACES + "("
            + COLUMN_ID + " text primary key, "
            + COLUMN_PLACE_REVIEW + " text, "
            + COLUMN_PLACE_BLACKLISTED + " numeric not null,"
            + COLUMN_PLACE_VISITED + " numeric not null,"
            + COLUMN_PLACE_NAME + " text"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_VISITED_PLACES);
        onCreate(db);
    }

}
