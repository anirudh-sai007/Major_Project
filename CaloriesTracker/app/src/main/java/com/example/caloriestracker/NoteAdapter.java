package com.example.caloriestracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoteAdapter extends FirestoreRecyclerAdapter<Adddata,NoteAdapter.Noteholder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private OnItemClickListener listener;
    Context context;
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Adddata> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Noteholder holder, int position, @NonNull Adddata model) {
        holder.fooditem.setText(model.getFood());

      //  holder.link.setVisibility(View.INVISIBLE);
        holder.calories.setText(model.getCalories());
        holder.date.setText(model.getDate());
        holder.cardView.setBackground(context.getResources().getDrawable(R.drawable.left_circular_bg6));
       // holder.fooditem.setTextColor(Color.parseColor("#2F80ED"));
    //    holder.calories.setTextColor(Color.parseColor("#F2994A"));
     //   holder.date.setTextColor(Color.parseColor("#7367F0"));

        //    holder.prior.setText(String.valueOf(model.getPrior()));

    }

    @NonNull
    @Override
    public Noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_view,
                parent,false);

        return new Noteholder(v);
    }

    class Noteholder extends RecyclerView.ViewHolder{
        TextView fooditem;

        TextView calories;
        TextView date;
        MaterialCardView cardView;


        Adddata user;


        public Noteholder(@NonNull final View itemView) {
            super(itemView);

           cardView=itemView.findViewById(R.id.rlv);
            fooditem=itemView.findViewById(R.id.t1);
            calories=itemView.findViewById(R.id.t2);
            date=itemView.findViewById(R.id.t3);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });




        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

/*
package com.example.modf_events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference notebookref = db.collection("admin");
    //CollectionReference notebookref = db.collection("orphan").document("char2").collection("reply");


    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
                     //        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        Query query = notebookref.orderBy("month", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();
        adapter = new NoteAdapter(options,this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scheduleLayoutAnimation();
        //LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.list_layout_controller);
       // recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onStart()

    {


        super.onStart();
        adapter.startListening();


    }
    @Override
    protected void onStop() {


        super.onStop();
        adapter.stopListening();


    }
}
 */