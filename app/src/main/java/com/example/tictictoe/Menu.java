package com.example.tictictoe;

import static com.example.tictictoe.R.id.neonEffect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Menu extends AppCompatActivity implements View.OnClickListener{
private CardView card,card2,card3,card4;
TextView high_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        high_score=findViewById(R.id.high_Score);

        SharedPreferences sharedPreferences=getSharedPreferences("ResultDatabase",MODE_PRIVATE);
        String setName= sharedPreferences.getString("ResultName",null);
        high_score.setText(setName);

          card=findViewById(R.id.card);
          card2=findViewById(R.id.card2);
          card3=findViewById(R.id.card3);
          card4=findViewById(R.id.card4);

          card.setOnClickListener(this);
          card2.setOnClickListener(this);
          card3.setOnClickListener(this);
          card4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.card){
            Intent intent=new Intent(Menu.this,Label2.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.card2){
            Intent intent=new Intent(Menu.this,Label3.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.card3){
            Intent intent=new Intent(Menu.this,Label4.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.card4){
            Intent intent=new Intent(Menu.this,Label1.class);
            startActivity(intent);
        }

    }
}