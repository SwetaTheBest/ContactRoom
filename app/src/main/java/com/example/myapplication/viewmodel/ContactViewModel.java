package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.database.repository.ContactRepository;
import com.example.myapplication.model.Contact;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository repository;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);

    }

    public void insertContact(Contact contact) {
        repository.insertContact(contact);
    }

    public void updateContact(Contact contact) {
        repository.updateContact(contact);
    }

    public void deleteContact(Contact contact) {
        repository.deleteContact(contact);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return repository.getAllContacts();
    }

}
