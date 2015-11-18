package com.liquidwolf.drinksomemore.activities;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.liquidwolf.drinksomemore.DrinkSomeMoreApp;
import com.liquidwolf.drinksomemore.R;

import java.util.Calendar;
import java.util.Date;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * Constants for water add
     */
    private static final int LITTLE_GLASS = 26;
    private static final int REGULAR_GLASS = 33;
    private static final int LITTLE_BOTTLE = 50;
    private static final int REGULAR_BOTTLE = 100;

    //Debugging constant
    private static final String TAG = HomeActivity.class.getSimpleName();

    private int waterToday;
    private SeekBar barWater;
    private SQLiteDatabase databaseUserAndWaterConsommation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //this.initialiseBarWater();
        this.initialiseDB();

        this.barWater = (SeekBar) this.findViewById(R.id.seekBar);
        barWater.setMax(300); //TODO Replace 300 by a variable or a constant

    }

    public void onClick (View v) {

      switch (v.getId()) {

          case (R.id.button26):
              this.waterToday += LITTLE_GLASS;
              break;
          case (R.id.button33):
              this.waterToday += REGULAR_GLASS;
              break;
          case R.id.button50:
              this.waterToday += LITTLE_BOTTLE;
              break;
          case R.id.button100:
              this.waterToday += REGULAR_BOTTLE;
              break;
          case R.id.buttonDebug:
              //Insérer ici une méthode de debug
          default: break;
      }

        this.refreshBar();
        this.updateDB();


      }

    public void refreshBar(){
            this.barWater.setProgress(this.waterToday);

    }

    public void updateDB(){

        SQLiteDatabase databaseUserAndWaterConsommation = openOrCreateDatabase(DrinkSomeMoreApp.DB_NAME,MODE_PRIVATE,null);

        //databaseUserAndWaterConsommation.insertOrThrow("DailyUse")
        //databaseUserAndWaterConsommation.execSQL("INSERT INTO DailyUse (day, waterDrank) values(DUMB) ON DUPLICATE KEY UPDATE waterDrank ");

    }

    public void initialiseDB() {

        String hasDatabaseBeenEverInitializedQuery = "SELECT * FROM DailyUse";

        this.databaseUserAndWaterConsommation = openOrCreateDatabase(DrinkSomeMoreApp.DB_NAME, MODE_PRIVATE,null);

        Cursor checkerOfExistenceOfFirstEntry= this.databaseUserAndWaterConsommation.rawQuery(hasDatabaseBeenEverInitializedQuery, null);

        Log.d(TAG,"Cursor size = " + checkerOfExistenceOfFirstEntry.getCount());
        if(checkerOfExistenceOfFirstEntry.getCount()>0)
        {
            //Database have already been initialized once, time to retrieve the latest entry.
            String hasDatabaseBeenitializedTodayQuery = "SELECT * FROM DailyUse";

            Cursor checkerOfLastEntry = this.databaseUserAndWaterConsommation.rawQuery(hasDatabaseBeenitializedTodayQuery, null);
            checkerOfLastEntry.moveToFirst();
            Date lastUse = new Date(checkerOfLastEntry.getLong(checkerOfLastEntry.getColumnIndex("day")));
            Date today = new Date(System.currentTimeMillis());

            Calendar testerDatelastUse = Calendar.getInstance();
            Calendar testerDateToday = Calendar.getInstance();
            testerDatelastUse.setTime(lastUse);
            testerDateToday.setTime(today);

            Log.d(TAG, "Today is " + testerDateToday.get(Calendar.DAY_OF_MONTH) + "/" + testerDateToday.get(Calendar.MONTH) + "/" + testerDateToday.get(Calendar.YEAR) + " Date of last use is " + testerDatelastUse.get(Calendar.DAY_OF_MONTH) + "/" + testerDatelastUse.get(Calendar.MONTH) + "/" + testerDatelastUse.get(Calendar.YEAR));

            if (!(testerDatelastUse.get(Calendar.YEAR) == testerDateToday.get(Calendar.YEAR) &&
                    testerDatelastUse.get(Calendar.DAY_OF_YEAR) == testerDateToday.get(Calendar.DAY_OF_YEAR)))
            {

                ContentValues values = new ContentValues();
                long todayl = System.currentTimeMillis();
                Log.d(TAG, ""+todayl+"Ms du jour, je dois pas être égal à zéro");
                values.put("day", todayl );
                values.put("waterDrank", 0);
                this.databaseUserAndWaterConsommation.insert("DailyUse", null, values);
                Log.d(TAG, "new entry created in database.");
            }
            else
            {
                Log.d(TAG, "entry of today already created in database.");
            }
        }
        else
        {
            //Database is empty.
            //We create the first entry in the table.
            ContentValues values = new ContentValues();
            long today = System.currentTimeMillis();
            Log.d(TAG, ""+today+"Ms du jour, je dois pas être égal à zéro");
            values.put("day", today);
            values.put("waterDrank", 0);
            this.databaseUserAndWaterConsommation.insert("DailyUse", null, values);
            Log.d(TAG, "first entry created in database.");
        }
        this.databaseUserAndWaterConsommation.close();


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }


}


