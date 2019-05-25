package com.example.android.recyclerviewproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ProgramActivity extends AppCompatActivity {

    //TODO #4 Define private widgets (recyclerviews + helpers) and the private arraylist to be presented
    private ArrayList<ServiceItem> serviceList;
    private TextView myName;
    private Button addServBtn;
    private RecyclerView myRecycler;
    private ServiceAdapter myAdapter;
    private ExampleItem myItem;
    private RecyclerView.LayoutManager myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        serviceList = new ArrayList<>();
        serviceList.add(new ServiceItem(0.0, "some", "thing"));

        getParceables();
        initRecyclerView();
        initAddButton();

        myName = findViewById(R.id.serveprog_name);
        myName.setText(myItem.getProgram());
    }

    private void getParceables(){ //gains access to item's values (name, hours, role, etc)
        Intent myInt = getIntent();
        myItem = myInt.getParcelableExtra("Item");
    }

    private void initAddButton() {
        addServBtn = findViewById(R.id.add_btn);
        addServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        AddServDialog myDialog = new AddServDialog();
        myDialog.show(getSupportFragmentManager(), "Add New Service Dialog");
    }

    private void initRecyclerView() {
        //initialize all objects
        myRecycler = findViewById(R.id.serverecycler_view);
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ServiceAdapter(serviceList);

        //configure objects to the recyclerview
        myRecycler.setLayoutManager(myLayout);
        myRecycler.setAdapter(myAdapter);

        //configures button on each cardview by accessing this custom function from ServiceAdapter
        myAdapter.setOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //TODO create a dialog for a service item (fill with info)
            }
        });
    }

}
