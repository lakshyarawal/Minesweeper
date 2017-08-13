package com.example.lakshya.tictac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.BatchUpdateException;

public class startActivity extends AppCompatActivity {

    EditText editText;
    TextView nameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setTitle("Minesweeper");
        editText =(EditText)findViewById(R.id.editTextId);
        Button startButton = (Button) findViewById(R.id.startButton);
        nameTextView = (TextView) findViewById(R.id.textView);
        final SharedPreferences sharedPreferences = getSharedPreferences("tictac",MODE_PRIVATE);
        String name = sharedPreferences.getString("userName",null);
        if(name==null){
            nameTextView.setText("Welcome User");
        }
        else {
            nameTextView.setText("Welcome "+name);
        }
         startButton.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                String name = editText.getText().toString();
                 if(name.isEmpty()){
                     Toast.makeText(startActivity.this,"Enter name!!",Toast.LENGTH_LONG).show();
                     return;
                 }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                 editor.putString("userName",name);
                 editor.commit();
                 Intent i = new Intent(startActivity.this,MainActivity.class);
                 i.putExtra("userName",name);
                 startActivity(i);
             }
         });
    }
}
