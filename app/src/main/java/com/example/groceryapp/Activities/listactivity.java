package com.example.groceryapp.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groceryapp.Data.databasehandler;
import com.example.groceryapp.Model.GroceryM;
import com.example.groceryapp.R;
import com.example.groceryapp.UI.recyclerviewadapter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class listactivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<GroceryM> list;
    databasehandler db;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    EditText editText1,editText2;
    Button button;
    databasehandler databasehandler;
    recyclerviewadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("GROCERY APP");

        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        db=new databasehandler(this);
        databasehandler=new databasehandler(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createpopup();
            }
        });
        list=db.getAllGrocery();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter=new recyclerviewadapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    private void createpopup()
    {

            builder=new AlertDialog.Builder(this);
            View v=getLayoutInflater().inflate(R.layout.popup,null);
            editText1=(EditText) v.findViewById(R.id.edittext1);
            editText2=(EditText) v.findViewById(R.id.edittext2);
            button=(Button) v.findViewById(R.id.button);
            builder.setView(v);
            alertDialog=builder.create();
            alertDialog.show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    if((!editText1.getText().toString().isEmpty())&&(!editText2.getText().toString().isEmpty()))
                    {
                        savetodb(v);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"ENTER FULL DATA",Toast.LENGTH_LONG).show();
                    }

                }
            });


    }


    private void savetodb(View v)
    {

            String name,quantity;
            name=editText1.getText().toString();
            quantity=editText2.getText().toString();
            GroceryM newg=new GroceryM();
            newg.setName(name);
            newg.setQuantity(quantity);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dat= formatter.format(date);
        newg.setDate(dat);

        databasehandler.addGrocery(newg);
            alertDialog.dismiss();
            list.add(newg);
            adapter.notifyDataSetChanged();
    }

}
