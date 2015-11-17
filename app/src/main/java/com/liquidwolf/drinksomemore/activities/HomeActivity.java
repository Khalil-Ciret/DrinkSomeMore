package com.liquidwolf.drinksomemore.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.liquidwolf.drinksomemore.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * Constants for water add
     */

    private static final int LITTLE_GLASS = 26;
    private static final int REGULAR_GLASS = 33;
    private static final int LITTLE_BOTTLE = 50;
    private static final int REGULAR_BOTTLE = 100;

    private int waterToday;
    private SeekBar barWater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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


      }

    public void refreshBar(){
            this.barWater.setProgress(this.waterToday);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}


