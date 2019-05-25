package com.example.android.recyclerviewproject;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddServDialog.AddServeDialogListener {

    private RecyclerView myRecycler;
    private ExampleAdapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private ArrayList<ExampleItem> myList;
    private TextView myTotalHrs;
    private Button addServBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        //TODO update myTotalHrs in loadData and applyTexts functions
        myTotalHrs = findViewById(R.id.numhrs_lbl);

        initRecyclerView();
        initAddButton();
    }

    private void initRecyclerView() {
        //initialize all objects
        myRecycler = findViewById(R.id.recycler_view);
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ExampleAdapter(myList);

        //configure objects to the recyclerview
        myRecycler.setLayoutManager(myLayout);
        myRecycler.setAdapter(myAdapter);

        //configures button on the cardview by accessing this custom function from ExampleAdapter
        myAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openProgActivity();
            }
        });
    }

    private void openProgActivity(){
        Intent myInt = new Intent(this, ProgramActivity.class);
        startActivity(myInt);
    }


    //ADD NEW PROGRAM BUTTON
    private void initAddButton() {
        addServBtn = findViewById(R.id.add_btn);
        addServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        AddServDialog myDialog = new AddServDialog();
        myDialog.show(getSupportFragmentManager(), "Add New Service Dialog");
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED PREF", MODE_PRIVATE);
        Gson myGson = new Gson();
        String json = sharedPreferences.getString("SERVICE LIST", null);
        Type myType = new TypeToken<ArrayList <ExampleItem> >(){}.getType();
        myList = myGson.fromJson(json, myType);

        if(myList == null) myList = new ArrayList<>();
    }

    @Override //implements interface AddServeDialogListener
    public void applyText(String name, double hrs, String myRole) {
        ExampleItem myItem = new ExampleItem(name, hrs, myRole);
        myList.add(myItem); //adds new service into private arraylist
        initRecyclerView(); //refresh list on layout.xml
    }

    @Override
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Gson myGson = new Gson();
        String json = myGson.toJson(myList);
        myEdit.putString("SERVICE LIST", json);
        myEdit.apply();
    }
}
