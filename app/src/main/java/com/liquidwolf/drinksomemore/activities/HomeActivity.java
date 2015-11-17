package com.liquidwolf.drinksomemore.activities;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.liquidwolf.drinksomemore.DrinkSomeMoreApp;
import com.liquidwolf.drinksomemore.R;

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
      }

        this.refreshBar();
        this.updateDB();


      }

    public void refreshBar(){
            this.barWater.setProgress(this.waterToday);

    }

    public void updateDB(){

    return;
      //  SQLiteDatabase databaseUserAndWaterConsommation = SQLiteDatabase.openDatabase(DrinkSomeMoreApp.DB_NAME,MODE_PRIVATE,null);

        //databaseUserAndWaterConsommation.insertOrThrow("DailyUse")
        //databaseUserAndWaterConsommation.execSQL("INSERT INTO DailyUse (day, waterDrank) values(DUMB) ON DUPLICATE KEY UPDATE waterDrank ");

    }

    public void initialiseDB() {
        //this.databaseUserAndWaterConsommation = SQLiteDatabase.openDatabase(DrinkSomeMoreApp.DB_NAME,MODE_PRIVATE,null);
        Date today = new Date(System.currentTimeMillis());

        Log.d(TAG, ""+today.getTime());
        String hasDatabaseBeenInitializedTodayQuery = "SELECT max(day) FROM DailyUse";

        this.databaseUserAndWaterConsommation = openOrCreateDatabase(DrinkSomeMoreApp.DB_NAME, MODE_PRIVATE,null);

        Cursor checkerOfExistenceOfFirstEntry= this.databaseUserAndWaterConsommation.rawQuery(hasDatabaseBeenInitializedTodayQuery, null);

        if(checkerOfExistenceOfFirstEntry != null)
        {
            checkerOfExistenceOfFirstEntry.moveToFirst();
            Toast.makeText(this,""+checkerOfExistenceOfFirstEntry.getInt(0), Toast.LENGTH_LONG).show();
            //TODO check if it's a new day or not, then create the entry in DB
        }
        else
        {
           //We create the first entry in the table.
            this.databaseUserAndWaterConsommation.execSQL("INSERT INTO DailyUse VALUES (" + Long.toString(System.currentTimeMillis()) + ", 0");
        }



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }


}


