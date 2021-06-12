package com.example.caloriestracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Home extends AppCompatActivity {
    Button b;
    EditText e;
    String cal;
    int x;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        b = (Button) findViewById(R.id.button);
        e = (EditText) findViewById(R.id.et);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cal1 = e.getText().toString();


                int x1 = Integer.valueOf(cal1);

                Toast.makeText(Home.this, String.valueOf(x1), Toast.LENGTH_SHORT).show();
                if (x1 < 100 || x1 > 5000 || x1 == 0) {
                    Toast.makeText(Home.this, "Invalid Calories", Toast.LENGTH_SHORT).show();
                } else {
                    Date c = Calendar.getInstance().getTime();
                    Calendar f = Calendar.getInstance();
                    System.out.println("Current time => " + c);

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    HashMap<String, String> hm2 = new HashMap<>();
                    hm2.put("cnt", String.valueOf(x1));
                    db.collection("CaloriesMaximum").document(formattedDate).set(hm2);
                    x = 10;
                    Intent it = new Intent(Home.this, MainActivity.class);
                    it.putExtra("calories", x1);
                    startActivity(it);
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        String formattedDate;
        db = FirebaseFirestore.getInstance();
        Date c = Calendar.getInstance().getTime();
        Calendar f = Calendar.getInstance();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        db.collection("CaloriesData").document("1").collection(formattedDate)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty()) {
                    Intent i = new Intent(Home.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {

                }

            }
        });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(x==10)
//        {
//            Intent it = new Intent(Home.this, MainActivity.class);
//            startActivity(it);
//        }
//    }
}