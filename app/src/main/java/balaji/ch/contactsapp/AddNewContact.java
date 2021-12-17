package balaji.ch.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddNewContact extends AppCompatActivity {

    private EditText addContactET, addNumberET;
    private Button addContactButton;
    private List<Contacts> contactsList;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        addContactButton = findViewById(R.id.addContactButton);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactET = findViewById(R.id.addContactET);
                addNumberET = findViewById(R.id.addNumberET);

                String contactNameString = addContactET.getText().toString();
                String contactNumberString = addNumberET.getText().toString();
                contactsList = readPreferences();
                if (contactsList == null){
                    contactsList = new ArrayList<>();
                }
                contactsList.add(new Contacts(contactNameString, contactNumberString));
                saveData();
                finish();
            }
        });
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