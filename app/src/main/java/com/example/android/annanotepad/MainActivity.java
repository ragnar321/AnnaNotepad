package com.example.android.annanotepad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    EditText todoEditForm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();

        //Set up preferences store
        sharedPref = context.getSharedPreferences(getString(R.string.anna_todo_prefs), MODE_PRIVATE);
        editor = sharedPref.edit();

        //Set up form (populated in the onResume() method)
        todoEditForm = findViewById(R.id.todo_entry_form);

        //Set up textWatcher to monitor list changes
        TextWatcher textWatcher = setUpTextWatcher();
        todoEditForm.addTextChangedListener(textWatcher);
    }

    protected void onResume() {
        super.onResume();
        todoEditForm.setText(restoreToDoList());
//        System.out.println("I'm running!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToDoList();
        editor.commit();
//        System.out.println("I'm in the onPause method!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_all:
                todoEditForm.selectAll();
                return true;

            case R.id.action_clear_all:
                todoEditForm.setText("");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    protected TextWatcher setUpTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                System.out.println("I'm the afterTextChanged method!");
                saveToDoList();
            }
        };
    }

    protected void saveToDoList() {
        String todoData = todoEditForm.getText().toString();
        editor.putString(getString(R.string.anna_todo_prefs), todoData);
        editor.apply();
//        System.out.println("Updated todo data is: " + todoData);
    }

    protected String restoreToDoList() {
        String todoList = sharedPref.getString(getString(R.string.anna_todo_prefs), "");
//        System.out.println("The restored data is " + todoList);
        return todoList;
    }





}
