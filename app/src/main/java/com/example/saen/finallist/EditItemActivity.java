package com.example.saen.finallist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.saen.finallist.model.Item;
import com.example.saen.finallist.utils.SPHelper;

public class EditItemActivity extends AppCompatActivity {
    private Item item = new Item();
    private Button buttonSubmit;
    private EditText editItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("id", 0);
        String name = intent.getStringExtra("name");

        item.setId(id);
        item.setName(name);

        editItemName = (EditText) findViewById(R.id.edit_add_item);
        buttonSubmit = (Button) findViewById(R.id.btn_submit);

        editItemName.setText(name);
        buttonSubmit.setOnClickListener(new EditItemActivity.OnSubmitListener(editItemName, item));
    }

    private class OnSubmitListener implements View.OnClickListener {
        private EditText editItemName;
        private Item item;

        public OnSubmitListener(EditText editItemName, Item item) {
            this.editItemName = editItemName;
            this.item = item;
        }

        @Override
        public void onClick(View view) {
            String itemName = editItemName.getText().toString();
            item.setName(itemName);
            SPHelper.modify(EditItemActivity.this, item);
            EditItemActivity.this.finish();
        }
    }
    public static void startActivity(Context context, Item item) {
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("name", item.getName());

        ((Activity) context).startActivityForResult(intent, 1);
    }

}
