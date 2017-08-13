package com.example.lakshya.tictac;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;



public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{
    LinearLayout mainLayout;
    LinearLayout rowLayouts[];
    MyButton[][] buttons;
    public final static boolean NO_MINES = false;
    public final static boolean MINES = true;
    public static int n = 8;
    public static int minesNumber = 10;
    boolean gameStarted = true,gameOver=false;
    Random rand = new Random();
    int  placeX,placeY ;
    int flag=0;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i2 =getIntent();
        userName = i2.getStringExtra("userName");
        Toast.makeText(this,"GO "+userName,Toast.LENGTH_LONG).show();
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        setUpBoard();
    }
    void resetBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buttons[i][j].mines =NO_MINES;
                buttons[i][j].count = 0;
                buttons[i][j].setText("");
            }
        }
        gameStarted = true;
        gameOver = false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.newGame){
            resetBoard();
        }
        return true;
    }
    public void setUpBoard(){
        buttons = new MyButton[n][n];
        rowLayouts = new LinearLayout[n];
        for(int i = 0; i < n; i++){
            rowLayouts[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0, 1f);
            params.setMargins(5,5,5,5);
            rowLayouts[i].setLayoutParams(params);
            rowLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rowLayouts[i]);
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                buttons[i][j] = new MyButton(this);
                buttons[i][j].a = i;
                buttons[i][j].b = j;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setOnLongClickListener(this);
                buttons[i][j].setTextSize(50);
                buttons[i][j].setTextColor(ContextCompat.getColor(this, R.color.buttonColor));
                rowLayouts[i].addView(buttons[i][j]);

            }


        }

    }
    public void onClick(View view){
        if(gameOver){
            return;
        }

 MyButton button = (MyButton) view;
        if (gameStarted)placeMines(button);
        gameStarted = false;
        reveal(button);
        if(button.mines){
            button.setText("*");
            Toast.makeText(this,"Game Over",Toast.LENGTH_LONG).show();
            gameOver = true;
        }
        else {
            button.setText(button.count+"");
        }
        checkStatus();
    }
    public void placeMines(MyButton b){
        int i =0;
while(i < minesNumber){
    placeX = rand.nextInt(8);
    placeY = rand.nextInt(8);
if(!buttons[placeX][placeY].mines && buttons[placeX][placeY]!=b ){
    buttons[placeX][placeY].mines = MINES;
    i++;
}
         }
         setCount();
    }
    public void setCount(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(buttons[i][j].mines){
                    buttons[i][j].count = 10;
                    if(i-1>=0)buttons[i-1][j].count++;
                    if(j-1>=0)buttons[i][j-1].count++;
                    if(i+1<n)buttons[i+1][j].count++;
                    if(j+1<n)buttons[i][j+1].count++;
                    if(i-1>=0&&j-1>=0)buttons[i-1][j-1].count++;
                    if(i-1>=0&&j+1<n)buttons[i-1][j+1].count++;
                    if(i+1<n&&j-1>=0)buttons[i+1][j-1].count++;
                    if(i+1<n&&j+1<n)buttons[i+1][j+1].count++;
                }
            }
        }
    }
    public void reveal(MyButton b){
        int i =b.a;
        int j =b.b;
        if(b.count==0){
            if(i-1>=0){
                buttons[i-1][j].setText(buttons[i-1][j].count+"");
                if(buttons[i-1][j].count==0){
                    b.count =-1;
                    reveal(buttons[i-1][j]);
                }

            }
            if(j-1>=0){
                buttons[i][j-1].setText(buttons[i][j-1].count+"");
                if(buttons[i][j-1].count==0){
                    b.count =-1;
                    reveal(buttons[i][j-1]);
                }

            }
            if(i+1<n){
                buttons[i+1][j].setText(buttons[i+1][j].count+"");
                if(buttons[i+1][j].count==0){
                    b.count =-1;
                    reveal(buttons[i+1][j]);
                }
            }
            if(j+1<n){
                buttons[i][j+1].setText(buttons[i][j+1].count+"");
                if(buttons[i][j+1].count==0){
                    b.count =-1;
                    reveal(buttons[i][j+1]);
                }
            }
            if(i-1>=0&&j-1>=0){
                buttons[i-1][j-1].setText(buttons[i-1][j-1].count+"");
                if(buttons[i-1][j-1].count==0){
                    b.count =-1;
                    reveal(buttons[i-1][j-1]);
                }
            }
            if(i-1>=0&&j+1<n){
                buttons[i-1][j+1].setText(buttons[i-1][j+1].count+"");
                if(buttons[i-1][j+1].count==0){
                    b.count =-1;
                    reveal(buttons[i-1][j+1]);
                }
            }

            if(i+1<n&&j-1>=0){
                buttons[i+1][j-1].setText(buttons[i+1][j-1].count+"");
                if(buttons[i+1][j-1].count==0){
                    b.count =-1;
                    reveal(buttons[i+1][j-1]);
                }
            }

            if(i+1<n&&j+1<n){
                buttons[i+1][j+1].setText(buttons[i+1][j+1].count+"");
                if(buttons[i+1][j+1].count==0){
                    b.count =-1;
                    reveal(buttons[i+1][j+1]);
                }
            }
            b.setText(b.count+"");
        }

    }
    public boolean onLongClick(View view){

        MyButton button = (MyButton) view;
        button.setText("F");
        flag++;
        checkStatus();
        return true;
    }
    public void checkStatus(){
        int counter =0;

        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                if(buttons[i][j].mines){
                   if(buttons[i][j].getText().toString().contentEquals("F")){
                       counter++;
                   }
                }
            }
        }
        if(counter==minesNumber){
            Toast.makeText(this,"CONGO",Toast.LENGTH_LONG).show();
            gameOver = true;
        }
    }

}
