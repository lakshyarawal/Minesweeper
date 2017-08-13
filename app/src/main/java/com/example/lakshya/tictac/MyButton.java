package com.example.lakshya.tictac;

import android.content.Context;
import android.widget.Button;

/**
 * Created by LAKSHYA on 6/16/2017.
 */

public class MyButton extends Button {

    int a,b;
    int count;
    boolean mines;

    public MyButton(Context context) {

        super(context);
        mines = MainActivity.NO_MINES;
        count  = 0;
    }

}
