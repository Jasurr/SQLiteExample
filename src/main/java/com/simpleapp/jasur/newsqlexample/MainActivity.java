package com.simpleapp.jasur.newsqlexample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button add, edit;
    EditText text;
    ListView list;
    DBHelper dbh;
    ArrayList<String> al;
    ArrayAdapter<String> adapter;
    int pos;
    public static ArrayList<Integer> itemid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.add);
        edit = (Button) findViewById(R.id.edit);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        dbh=new DBHelper(this);
        al = dbh.all();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, final long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Menu")
                        .setNeutralButton("O`zgartirish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                itemClicked(al.get((int)l), itemid.get((int)l), (int)l);
                            }
                        })
                        .setNegativeButton("O`chirish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                itemClickedForDelete(itemid.get((int)l));
                            }
                        }).show()
                ;
            }
        });
    }

    public void itemClicked(String text, int id, int index){
        this.text = (EditText) findViewById(R.id.text1);
        this.text.setText(text);
        pos = index;
        edit = (Button) findViewById(R.id.edit);
        add = (Button) findViewById(R.id.add);

        edit.setVisibility(View.VISIBLE);
        add.setVisibility(View.INVISIBLE);
    }

    protected void add(){
        dbh = new DBHelper(getApplicationContext());
        text = (EditText) findViewById(R.id.text1);
        if (text.getText().toString()!=""){
            dbh.add(text.getText().toString());
        }
        al = dbh.all();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    protected void edit(){
        text = (EditText) findViewById(R.id.text1);
        edit = (Button) findViewById(R.id.edit);
        add = (Button) findViewById(R.id.add);
        dbh = new DBHelper(this);
        dbh.update(itemid.get(pos),text.getText().toString());
        al = dbh.all();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        add.setVisibility(View.VISIBLE);
        edit.setVisibility(View.INVISIBLE);

    }

    protected void itemClickedForDelete(int id){
        dbh.delete(id);
        al = dbh.all();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
