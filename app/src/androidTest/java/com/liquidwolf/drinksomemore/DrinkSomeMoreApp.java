package com.liquidwolf.drinksomemore;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by khalil.ciret on 17/11/15.
 * This class purpose is to initialize the application correctly.
 * It will create a database stocking the use water consommation.
 */
public class DrinkSomeMoreApp extends Application {

    public static final String DB_NAME ="user_water_cons";


    @Override
    public void onCreate() {
        super.onCreate();

        SQLiteDatabase databaseUserAndWaterConsommation = openOrCreateDatabase(DB_NAME,MODE_PRIVATE,null);
        databaseUserAndWaterConsommation.execSQL("CREATE TABLE IF NOT EXISTS DayOfUse(DATE day, INT waterDrank");

    }
}
