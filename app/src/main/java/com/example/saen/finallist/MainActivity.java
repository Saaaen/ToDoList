package com.example.saen.finallist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.saen.finallist.model.Item;
import com.example.saen.finallist.utils.SPHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listItem;
    private List<Item> itemList;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemActivity.startActivity(MainActivity.this);
            }
        });

        listItem = (ListView) findViewById(R.id.list_item);
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,
                getData());
        listItem.setAdapter(adapter);
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                String[] opt = {"EDIT", "COMPLETE"};
                builder.setItems(opt, new OnOptListener(itemList.get(i)));
                builder.show();
            }
        });
    }


    private class OnOptListener implements DialogInterface.OnClickListener {
        private Item item;
        public OnOptListener(Item item) {
            this.item = item;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case 0:
                    EditItemActivity.startActivity(MainActivity.this, item);

                    break;

                case 1:
                    SPHelper.delNode(MainActivity.this, item);
                    updateData();
                    break;
            }
            dialogInterface.dismiss();
        }
    }

    private List<String> getData() {
        itemList = SPHelper.getItemList(this);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            data.add(itemList.get(i).getName());
        }
        return data;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateData();
    }

    private void updateData() {
        adapter.clear();
        adapter.addAll(getData());
        adapter.notifyDataSetInvalidated();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
