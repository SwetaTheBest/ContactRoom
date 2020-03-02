package com.example.myapplication.database;

import android.app.Application;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.model.Contact;
import com.example.myapplication.view.interfaces.ContactDao;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactsDatabase extends RoomDatabase {
    private static ContactsDatabase instance;

    public abstract ContactDao contactDao();

    public static ContactsDatabase getInstance(Application application) {

        if (instance == null) {
            synchronized (ContactsDatabase.class) {
                if (instance == null)
                    instance = Room.databaseBuilder(application, ContactsDatabase.class, "contact_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
            }
        }

        return instance;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
