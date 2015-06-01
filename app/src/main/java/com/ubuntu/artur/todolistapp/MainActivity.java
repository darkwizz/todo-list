package com.ubuntu.artur.todolistapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;


public class MainActivity extends Activity implements View.OnClickListener, TextWatcher {
    private Button addNewBtn;
    private Button deleteBtn;
    private Button doneBtn;
    private Button undoneBtn;
    private LinearLayout todoItemsLayout;
    private ListView todoListView;
    private EditText todoTextEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewBtn = (Button)findViewById(R.id.addNewBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        doneBtn = (Button)findViewById(R.id.doneBtn);
        undoneBtn = (Button)findViewById(R.id.undoneBtn);

        todoItemsLayout = (LinearLayout)findViewById(R.id.controlsLayout);

        todoListView = (ListView)findViewById(R.id.todoListView);

        todoTextEdit = (EditText)findViewById(R.id.todoTextEdit);
        //todoListView.addFooterView();
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

    @Override
    public void onClick(View sender) {
        int id = sender.getId();
    }

    @Override
    public void beforeTextChanged(CharSequence string, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence string, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editor) {

    }
}
