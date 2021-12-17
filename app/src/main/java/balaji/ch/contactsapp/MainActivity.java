package balaji.ch.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabButton;
    private List<Contacts> contactsList = new ArrayList<>();
    private ContactsAdapter contactsAdapter;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    public void deleteContact(int position) {
        contactsList.remove(position);
        saveData();
        contactsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerView = findViewById(R.id.recyclerView);
        fabButton = findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddNewContact.class);
                startActivity(i);

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    protected void onResume() {
        super.onResume();

        contactsList = readPreferences();

        if(contactsList != null){
            Collections.sort(contactsList, (text1, text2) -> text1.getContactName().compareToIgnoreCase(text2.getContactName()));
            contactsAdapter = new ContactsAdapter(MainActivity.this, contactsList);
            recyclerView.setAdapter(contactsAdapter);
        }
    }

    public void saveData(){
        prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(contactsList);
        prefsEditor.putString("contactsList", json);
        prefsEditor.apply();
    }

    public ArrayList<Contacts> readPreferences(){
        Gson gson = new Gson();
        String json = prefs.getString("contactsList", null);
        Type type = new TypeToken<ArrayList<Contacts>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}