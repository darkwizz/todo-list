package com.ubuntu.artur.todolistapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ubuntu.artur.todolistapp.entities.TodoItem;
import com.ubuntu.artur.todolistapp.todomodule.TodoModule;


public class MainActivity extends Activity implements View.OnClickListener, TextWatcher,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ArrayAdapter<TodoItem> adapter;
    private Button addNewBtn;
    private Button deleteBtn;
    private Button okBtn;
    private Button cancelBtn;
    // private LinearLayout todoItemsLayout;
    private ListView todoListView;
    private EditText todoTextEdit;
    private TodoModule module;
    private int lastSelectedTodoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewBtn = (Button)findViewById(R.id.addNewBtn);
        addNewBtn.setOnClickListener(this);

        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(this);
        deleteBtn.setEnabled(false); // no selected TodoItems at a start of work

        okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(this);
        okBtn.setEnabled(false);

        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
        cancelBtn.setEnabled(false);

        // todoItemsLayout = (LinearLayout)findViewById(R.id.controlsLayout);
        todoTextEdit = (EditText)findViewById(R.id.todoTextEdit);
        todoTextEdit.addTextChangedListener(this);
        //InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //manager.hideSoftInputFromWindow(todoTextEdit.getWindowToken(), 0);
        todoTextEdit.setEnabled(false);

        module = new TodoModule();
        adapter = new ArrayAdapter<TodoItem>(this, android.R.layout.simple_list_item_multiple_choice);
        // Test todoItems
        //TodoItem[] items = { new TodoItem("Finish Android app"), new TodoItem("Finish 2nd lab") };

        /* TODO
           fillListView() method - when TodoModule will have DB interaction, then there
           will be reading from DB by module.getTodoItems() calling
        */
        //fillListView();

        adapter.addAll(module.getTodoItems());
        //adapter.addAll(items);

        todoListView = (ListView)findViewById(R.id.todoListView);
        todoListView.setAdapter(adapter);
        todoListView.setOnItemClickListener(this);
        //todoListView.setOnFocusChangeListener(this);
        //todoListView.setOnItemSelectedListener(this);
        todoListView.setOnItemLongClickListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

    /////////////////////////////////////////////////////////////
    ////////////////// UI EVENTS HANDLERS ///////////////////////
    /////////////////////////////////////////////////////////////

    @Override
    public void onClick(View sender) {
        int id = sender.getId();
        switch(id) {
            case R.id.addNewBtn:
                onAddNewTodoItem();
                break;
            case R.id.deleteBtn:
                onDeleteTodoItem();
                break;
            case R.id.okBtn:
                onAcceptAdding();
                break;
            case R.id.cancelBtn:
                onCancelAdding();
                break;
            default:
                // this method doesn't handle click on other buttons
                break;
        }
    }

    //////////////////////////
    // Button Clicks handlers
    //////////////////////////

    private void onAddNewTodoItem() {
        cancelBtn.setEnabled(true);
        todoTextEdit.setEnabled(true);
        todoTextEdit.requestFocus();
        InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(todoTextEdit, InputMethodManager.SHOW_IMPLICIT);
    }

    private void onDeleteTodoItem() {
        TodoItem item = adapter.getItem(lastSelectedTodoPosition);
        module.onTodoItemDeleted(item.getId());
        adapter.remove(item);
        adapter.notifyDataSetChanged();
        deleteBtn.setEnabled(false);
    }

    private void onAcceptAdding() {
        String todoText = todoTextEdit.getText().toString();
        TodoItem item = new TodoItem(todoText);
        module.onTodoItemAdded(item.getId(), item);
        adapter.add(item);
        adapter.notifyDataSetChanged();
        offNewTodoEnterControls();
    }

    private void offNewTodoEnterControls() {
        cancelBtn.setEnabled(false);
        okBtn.setEnabled(false);
        todoTextEdit.setText("");
        todoTextEdit.setEnabled(false);
    }

    private void onCancelAdding() {
        offNewTodoEnterControls();
    }

    // END of Clicks handlers
    //////////////////////////


    //////////////////////////
    // TextEdit events handlers

    @Override
    public void onTextChanged(CharSequence string, int start, int before, int count) {
        boolean hasSomeText = string.length() != 0;
        okBtn.setEnabled(hasSomeText);
    }

    // Stubs

    @Override
    public void beforeTextChanged(CharSequence string, int start, int count, int after) { }

    @Override
    public void afterTextChanged(Editable editor) { }

    // END of TextEdit event section
    //////////////////////////


    //////////////////////////
    // TodoListView events handlers

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView textView = (CheckedTextView)view;
        TodoItem item = adapter.getItem(position);
        item.setDoneState(textView.isChecked());
        module.onTodoItemStateChanged(item.getId(), textView.isChecked());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        lastSelectedTodoPosition = position;
        CheckedTextView textView = (CheckedTextView)view;
        onItemClick(parent, textView, position, id);
        textView.setSelected(true);
        deleteBtn.setEnabled(true);
        return false;
    }

    // END of TodoListView section
    //////////////////////////
}
