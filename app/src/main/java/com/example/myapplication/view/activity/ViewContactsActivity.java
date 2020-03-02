package com.example.myapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Contact;
import com.example.myapplication.view.adapter.ViewContactsAdapter;
import com.example.myapplication.viewmodel.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.myapplication.utils.Constants.ADD_CONTACT_REQUEST_CODE;
import static com.example.myapplication.utils.Constants.CONTACT_KEY;

public class ViewContactsActivity extends AppCompatActivity {

    private ContactViewModel contactViewModel;
    private RecyclerView recycler;
    private ViewContactsAdapter adapter;
    private LiveData<List<Contact>> allContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        adapter = new ViewContactsAdapter();

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);

        contactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ContactViewModel.class);

        allContacts = contactViewModel.getAllContacts();

        allContacts.observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setAllContacts(contacts);
            }
        });

        FloatingActionButton fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContactsActivity.this, AddEditContactActivity.class);
                startActivityForResult(intent, ADD_CONTACT_REQUEST_CODE);
            }
        });
        adapter.onClickListener(new ViewContactsAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(ViewContactsActivity.this, ViewContactInfoActivity.class);
                intent.putExtra(CONTACT_KEY, contact);
                startActivity(intent);
            }
        });


        FloatingActionButton fab_addContact = findViewById(R.id.fab_add);
        fab_addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContactsActivity.this, AddEditContactActivity.class);
                startActivityForResult(intent, ADD_CONTACT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK && requestCode == ADD_CONTACT_REQUEST_CODE) {
            Contact contact = (Contact) data.getSerializableExtra(CONTACT_KEY);
            contactViewModel.insertContact(contact);
            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        }
    }


}
