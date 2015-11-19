package com.liquidwolf.drinksomemore.activities;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.liquidwolf.drinksomemore.DrinkSomeMoreApp;
import com.liquidwolf.drinksomemore.R;

import java.util.Calendar;
import java.util.Date;


public class HomeActivity extends Activity implements View.OnClickListener {


    /**
     * Constants for water add
     */
    private static final int LITTLE_GLASS = 26;
    private static final int REGULAR_GLASS = 33;
    private static final int LITTLE_BOTTLE = 50;
    private static final int REGULAR_BOTTLE = 100;

    //Debugging constant
    private static final String TAG = HomeActivity.class.getSimpleName();

    /**
     * Parameters used for database management
     */
    private long currentDayID;
    private int waterToday;
    private SQLiteDatabase databaseUserAndWaterConsommation;

    /**
     * Widgets
     */
    private SeekBar barWater;
    private TextView labelWaterDrank;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.waterToday=0;
        this.barWater = (SeekBar) this.findViewById(R.id.seekBar);
        barWater.setMax(300); //TODO Replace 300 by a variable or a constant

        this.barWater.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
        });//Weird, but allow the bar to not be moved without altering its layout.


        this.labelWaterDrank = (TextView) this.findViewById(R.id.textViewWaterDrank);

        //this.initialiseBarWater();
        this.initialiseDB();
        this.refreshUI();



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
          case R.id.buttonValidateTextField:
              EditText editText=(EditText)this.findViewById(R.id.editTextCustomValue);
              this.waterToday += Integer.parseInt(editText.getText().toString());
              editText.setText("");
              break;
          case R.id.buttonDebug:
              //Insert debug function here
          default: break;
      }

        this.refreshUI();
        this.updateDB();


      }

    public void refreshUI(){
        this.barWater.setProgress(this.waterToday);
        this.labelWaterDrank.setText(getResources().getString(R.string.labelDrank_Start)+" "+(float)this.waterToday/100+" "+(getResources().getString(R.string.labelDrank_End)));
    }

    public void updateDB() {

        this.databaseUserAndWaterConsommation = openOrCreateDatabase(DrinkSomeMoreApp.DB_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("day", this.currentDayID);
        values.put("waterDrank", this.waterToday);
        databaseUserAndWaterConsommation.replace("DailyUse", null, values);
        this.databaseUserAndWaterConsommation.close();

    }


    public void initialiseDB() {

        String hasDatabaseBeenEverInitializedQuery = "SELECT max(day) FROM DailyUse";

        this.databaseUserAndWaterConsommation = openOrCreateDatabase(DrinkSomeMoreApp.DB_NAME, MODE_PRIVATE,null);

        Cursor checkerOfLastEntry= this.databaseUserAndWaterConsommation.rawQuery(hasDatabaseBeenEverInitializedQuery, null);

        if(checkerOfLastEntry.getCount()>0)
        {
            //Database have already been initialized once, time to retrieve the latest entry.

            checkerOfLastEntry.moveToFirst();
            this.currentDayID = checkerOfLastEntry.getLong(0);

            //All this stuff, just to compare date!
            //Maybe i will use a third party library later...
            Date lastUse = new Date(this.currentDayID);
            Date today = new Date(System.currentTimeMillis());
            Calendar testerDatelastUse = Calendar.getInstance();
            Calendar testerDateToday = Calendar.getInstance();
            testerDatelastUse.setTime(lastUse);
            testerDateToday.setTime(today);

            if (!(testerDatelastUse.get(Calendar.YEAR) == testerDateToday.get(Calendar.YEAR) &&
                    testerDatelastUse.get(Calendar.DAY_OF_YEAR) == testerDateToday.get(Calendar.DAY_OF_YEAR)))
            {
                //Today is a different day of the latest entry in DB. Time to create a new row!
                this.createNewDayInDatabase();
                Log.d(TAG, "new entry created in database.");
            }
            else
            {
                //An entry is already created in DB for today, let's work with it.
                Log.d(TAG, "entry of today already created in database. Retrieving data.");
                String retrievingDataOfTodayQuery = "SELECT day, waterDrank FROM DailyUse WHERE day = ?";
                checkerOfLastEntry= this.databaseUserAndWaterConsommation.rawQuery(retrievingDataOfTodayQuery, new String[]{Long.toString(this.currentDayID)});
                checkerOfLastEntry.moveToFirst();
                this.waterToday=checkerOfLastEntry.getInt(checkerOfLastEntry.getColumnIndex("waterDrank"));
            }
        }
        else
        {
            //Database is empty.
            //We create the first entry in the table.

            this.createNewDayInDatabase();
            Log.d(TAG, "first entry created in database.");
        }
        this.databaseUserAndWaterConsommation.close();


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }

    private void createNewDayInDatabase(){
        ContentValues values = new ContentValues();
        this.currentDayID=System.currentTimeMillis();
        values.put("day", this.currentDayID);
        values.put("waterDrank", 0);
        this.databaseUserAndWaterConsommation.insert("DailyUse", null, values);

    }

}


