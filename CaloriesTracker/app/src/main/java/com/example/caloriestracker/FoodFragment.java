package com.example.caloriestracker;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FoodFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore db;
    CollectionReference notebookref;
    String formattedDate;
    NoteAdapter adapter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_food, container, false);
        Date c = Calendar.getInstance().getTime();
        Calendar f=Calendar.getInstance();
        System.out.println("Current time => " + c);
       SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        db = FirebaseFirestore.getInstance();
        formattedDate = df.format(c);
        Toast.makeText(getActivity(),formattedDate,Toast.LENGTH_SHORT).show();
        notebookref = db.collection("CaloriesData").document("1").collection(formattedDate);

        Query query = notebookref.orderBy("Date", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Adddata> options = new FirestoreRecyclerOptions.Builder<Adddata>()
                .setQuery(query, Adddata.class)
                .build();
        adapter = new NoteAdapter(options,getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.flst);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.scheduleLayoutAnimation();
        //LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.list_layout_controller);
        // recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(adapter);
        return  view;
    }

    @Override
    public void onStart()

    {


        super.onStart();
        adapter.startListening();


    }
    @Override
    public void onStop() {


        super.onStop();
        adapter.stopListening();


    }
}
