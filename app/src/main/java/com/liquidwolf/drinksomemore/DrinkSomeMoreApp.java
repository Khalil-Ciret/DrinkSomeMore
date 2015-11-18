package com.liquidwolf.drinksomemore;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.liquidwolf.drinksomemore.activities.HomeActivity;

/**
 * Created by khalil.ciret on 17/11/15.
 * This class purpose is to initialize the application correctly.
 * It will create a database stocking the use water consommation.
 */
public class DrinkSomeMoreApp extends Application {

    public static final String DB_NAME ="user_water_conso";

    //Debugging constant
    public static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Creating database...");
        SQLiteDatabase databaseUserAndWaterConsommation = openOrCreateDatabase(DB_NAME,MODE_PRIVATE,null);
        databaseUserAndWaterConsommation.execSQL("CREATE TABLE IF NOT EXISTS DailyUse(day int PRIMARY KEY ASC,  waterDrank int)");
        databaseUserAndWaterConsommation.close();
        Log.d(TAG, "Database ready for initialization.");

    }
}
