package com.example.myapplication.view.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    public void insertContact(Contact contact);

    @Update
    public void updateContact(Contact contact);

    @Delete
    public void deleteContact(Contact contact);

    @Query("select * from Contact")
    public LiveData<List<Contact>> getAllContacts();

}
