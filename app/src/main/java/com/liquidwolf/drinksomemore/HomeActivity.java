package com.liquidwolf.drinksomemore;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class HomeActivity extends AppCompatActivity {


    /**
     * Constants for water add
     */

    private static final int LITTLE_GLASS = 26;
    private static final int REGULAR_GLASS = 33;
    private static final int LITTLE_BOTTLE = 50;
    private static final int REGULAR_BOTTLE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}


