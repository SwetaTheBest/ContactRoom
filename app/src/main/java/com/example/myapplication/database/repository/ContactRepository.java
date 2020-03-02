package com.example.myapplication.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.database.ContactsDatabase;
import com.example.myapplication.model.Contact;
import com.example.myapplication.view.interfaces.ContactDao;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private ContactsDatabase contactsDatabase;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {
        contactsDatabase = ContactsDatabase.getInstance(application);
        contactDao = contactsDatabase.contactDao();
        allContacts = contactDao.getAllContacts();
    }


    public void insertContact(Contact contact) {
        new InsertContactAsyncTask(contactDao).execute(contact);
    }

    public void updateContact(Contact contact) {
        new UpdateContactAsyncTask(contactDao).execute(contact);
    }

    public void deleteContact(Contact contact) {
        new DeleteContactAsyncTask(contactDao).execute(contact);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        ContactDao contactDao;


        public InsertContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insertContact(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        ContactDao contactDao;


        public UpdateContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.updateContact(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        ContactDao contactDao;


        public DeleteContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.deleteContact(contacts[0]);
            return null;
        }
    }

}
