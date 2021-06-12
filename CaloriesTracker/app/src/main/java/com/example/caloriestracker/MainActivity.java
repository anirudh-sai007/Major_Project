package com.example.caloriestracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainActivity;
     Calendar calendar;
     SimpleDateFormat dateFormat;
     String date;
    FirebaseFirestore db;
    CollectionReference notebookref;
    CollectionReference reference;
    DocumentReference reference1,r2;
    int u,cur,n1;
    Date date1;
    DateFormat dateFormat1;
    private static final String calorieInfoFile = "calorie_info.txt";
    private static HashMap<String, Integer> calorieInfo = new HashMap<>();
    TextView tv1,u1,u2,u3;

    int caloriess=2,calorries=2,ccalories=2,ri,calories;
    TextView tv2;
    Button b1,b2;
    String formattedDate;
    RecyclerView recyclerView;
  //  RecyclerAdapter adapter;
  NoteAdapter adapter;
    ArrayList<Adddata> datalist;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = FirebaseFirestore.getInstance();
        date1 = new Date();
        dateFormat1  = new SimpleDateFormat("yyMMdd", Locale.getDefault());

        tv1=findViewById(R.id.tc);
        tv2=findViewById(R.id.total);

        u1=findViewById(R.id.tu1);
        u2=findViewById(R.id.tu2);
        u3=findViewById(R.id.tu3);
        u1.setVisibility(View.INVISIBLE);
        u2.setVisibility(View.INVISIBLE);
        u3.setVisibility(View.INVISIBLE);
        Date c = Calendar.getInstance().getTime();
        Calendar f=Calendar.getInstance();
        System.out.println("Current time => " + c);
        cur=0;
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
         formattedDate = df.format(c);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl, new wait());
        ft.commit();
        db.collection("CaloriesData").document("1").collection(formattedDate).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.getResult().isEmpty())
                        {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fl, new Empty());
                            ft.commit();


                        }
                        else
                        {
                            u1.setVisibility(View.VISIBLE);
                            u2.setVisibility(View.VISIBLE);
                            u3.setVisibility(View.VISIBLE);
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.fl, new FoodFragment());
                            ft.commit();

                        }
                    }
                });
        //notebookref = db.collection("CaloriesData").document("1").collection(formattedDate);


       // CalendarView.OnDateChangeListener
     //    u=getIntent().getIntExtra("calories",0);


      // tv1.setText(String.valueOf(u));
      //  int u=Integer.parseInt(s1);
       // Toast.makeText(MainActivity.this,String.valueOf(u)+"HIII",Toast.LENGTH_LONG).show();
      //  adapter = new NoteAdapter(options,this);
//        recyclerView=findViewById(R.id.flst);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        datalist=new ArrayList<>();
//
//        adapter=new RecyclerAdapter(datalist);

      //  Toast.makeText(MainActivity.this,"HI",Toast.LENGTH_LONG).show();
         progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        // progressBar.setMax(u);
//        recyclerView.setAdapter(adapter);
//        db=FirebaseFirestore.getInstance();
//        db.collection("CaloriesData").document("1").collection(formattedDate).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
//                        for(DocumentSnapshot d:list)
//                        {
//                            Adddata obj=d.toObject(Adddata.class);
//                            datalist.add(obj);
//                        }
//                        adapter.notifyDataSetChanged();
//
//                    }
//                });




        mainActivity = this;
        try {
            calorieInfo = loadCalorieInfo(calorieInfoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        b1 = findViewById(R.id.faab);
        b2= findViewById(R.id.fabb);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ccalories=3;
                startActivity(new Intent(getApplicationContext(), DetectorActivity.class));
            }
        });
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }

    public void addFood(String food) {

       // int calories = getCalorie(food);
        //TextView total = (TextView) findViewById(R.id.total);
        //TextView progressBar_total = (TextView) findViewById(R.id.tc);
       // int cur = Integer.parseInt(total.getText().toString());

       // progressBar_total.setText("Current calories: " + cur + "");
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setProgress(cur);
       // cur = Integer.parseInt(total.getText().toString());

        long tsLong = (long) (System.currentTimeMillis() / 1000);
        java.util.Date d = new java.util.Date(tsLong * 1000L);
        String ts = new SimpleDateFormat("h:mm a").format(d);
//        date.setText(ts);
//        foodItem.setText(food);
//        cals.setText(calories + "");
//        date.setGravity(Gravity.CENTER);
//        foodItem.setGravity(Gravity.CENTER);
//        cals.setGravity(Gravity.CENTER);


        Random r = new Random();

        if(ccalories==3) {
            ri = r.nextInt(5);
            calories = getCalorie(food)-ri;
            ccalories=2;
        }
        cur += calories;
        tv2.setText(cur + "");
        progressBar.setProgress(cur);
        HashMap<String,String> hm2=new HashMap<>();
        hm2.put("cnt",String.valueOf(cur));
        db.collection("Caloriesprogress").document(formattedDate).set(hm2);
        HashMap<String,String> hm=new HashMap<>();
        if(ts.length()>0) {
            hm.put("Date", ts);
            hm.put("Food", food);
            hm.put("Calories", String.valueOf(calories));
        }
//        db.collection("Caloriesprogress").document(formattedDate).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
//                if(task.getResult().exists())
//                {
//
//                    for(QueryDocumentSnapshot document : task.getResult())
//                }
//            }
//        });
//        date.setLayoutParams(new TableRow.LayoutParams(0));
//        foodItem.setLayoutParams(new TableRow.LayoutParams(1));
//        cals.setLayoutParams(new TableRow.LayoutParams(2));
//        date.getLayoutParams().width = 0;
//        foodItem.getLayoutParams().width = 0;
//        cals.getLayoutParams().width = 0;
//        tr.addView(date);
//        tr.addView(foodItem);
//        tr.addView(cals);
//        t1.addView(tr);
        db=FirebaseFirestore.getInstance();



        reference=db.collection("CaloriesData").document("1").collection(formattedDate);
        reference.add(hm)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                     //   Toast.makeText(MainActivity.this,"Successfully Calculated2",Toast.LENGTH_SHORT).show();

                    }
                });

        db.collection("CaloriesMaximum").document(formattedDate).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            String s2 = task.getResult().getString("cnt");
                             n1 = Integer.parseInt(s2);


                        }

                    }
                });

      //  tv2.setText(String.valueOf(cur));
        if(cur>n1)
        {
           // Toast.makeText(MainActivity.this,"Calories Exceeded",Toast.LENGTH_LONG).show();
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }



    }

    public int getCalorie(String food) {
        return calorieInfo.get(food);
    }

    private HashMap loadCalorieInfo(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)));
        HashMap<String, Integer> calCounts = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            calCounts.put(line.split(": ")[0], Integer.parseInt(line.split(": ")[1]));
        }
        return calCounts;
    }

    @Override
    protected void onStart() {
        super.onStart();
        db=FirebaseFirestore.getInstance();
        Date c = Calendar.getInstance().getTime();
        Calendar f=Calendar.getInstance();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        db.collection("CaloriesMaximum").document(formattedDate).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            String s = task.getResult().getString("cnt");
                           tv1.setText(s);
                            progressBar.setMax(Integer.valueOf(s));

                        }
                    }
                });
        db.collection("Caloriesprogress").document(formattedDate).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()) {
                            String s2 = task.getResult().getString("cnt");
                            int n1 = Integer.parseInt(s2);
                            tv2.setText(s2);
                            progressBar.setProgress(n1);

                        }

                    }
                });
    }

//    @Override
//    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//
//            Intent i = new Intent(MainActivity.this, Home.class);
//            startActivity(i);
//            finish();
//
//    }


}