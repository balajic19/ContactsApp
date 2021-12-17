package balaji.ch.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactDetailActivity extends AppCompatActivity {

    private String contactName, contactNumber;
    private EditText contactNameET, contactNumberET;
    private Button saveContactButton;
    private int position;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private List<Contacts> contactsList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        contactName = getIntent().getStringExtra("name");
        contactNumber = getIntent().getStringExtra("contact");
        position = getIntent().getExtras().getInt("position");

        contactNameET = findViewById(R.id.contactNameET);
        contactNumberET = findViewById(R.id.contactNumberET);

        contactNameET.setText(contactName);
        contactNumberET.setText(contactNumber);

        saveContactButton = findViewById(R.id.saveContactButton);
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactName = contactNameET.getText().toString();
                String contactNumber = contactNumberET.getText().toString();

                Contacts contacts = new Contacts(contactName, contactNumber);
                contactsList = readPreferences();
                contactsList.set(position, contacts);
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