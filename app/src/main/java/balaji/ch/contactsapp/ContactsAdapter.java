package balaji.ch.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//This is an adapter class which is used to add the Contacts details to each card layout
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>{

//    Declaring required variables
    private MainActivity context;
    private List<Contacts> contactsList;

//    Constructor to initialize the required variables
    public ContactsAdapter(MainActivity context, List<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        declaring the variables
        Contacts contacts = contactsList.get(position);
        holder.contactName.setText(contacts.getContactName());
        holder.contactNumber.setText(contacts.getContactNumber());

//        When clicked on the cardview or each tap or contact this method is invoked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                When click on this view, we wil be redirected to the next activity, ContactDetailActivity
                Intent i = new Intent(context, ContactDetailActivity.class);
                i.putExtra("name", contacts.getContactName());
                i.putExtra("contact", contacts.getContactNumber());
                i.putExtra("position", holder.getAdapterPosition());

                context.startActivity(i);
            }
        });

//        When we click on the delete button of the cardview, this method is invoked and deleteContact which is present in the MainActivity is called to delete the row.
        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteContact(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }


    //    Create a new ViewHolder class to hold contacts data
    public static class MyViewHolder extends RecyclerView.ViewHolder{
    //        Create widgets
        public TextView contactName,contactNumber;
        public Button deleteContact;

//        Initializing the views
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.contactNameTV);
            contactNumber = itemView.findViewById(R.id.contactNumberTV);
            deleteContact = itemView.findViewById(R.id.deleteContact);
        }
    }
}
