package com.example.myapplication.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.Contact;
import com.example.myapplication.viewmodel.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import static com.example.myapplication.utils.Constants.CONTACT_KEY;
import static com.example.myapplication.utils.Constants.EDIT_CONTACT_REQUEST_CODE;

public class ViewContactInfoActivity extends AppCompatActivity {
    private TextView tv_name, tv_emailId, tv_mobileNo;
    private de.hdodenhof.circleimageview.CircleImageView iv_dp;
    private FloatingActionButton fab_edit;
    private Contact contact;
    private ContactViewModel contactViewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_info);
        init();

        contactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ContactViewModel.class);

        contact = (Contact) getIntent().getSerializableExtra(CONTACT_KEY);

        showContactInfo();
    }

    private void init() {
        tv_name = findViewById(R.id.tv_name);
        tv_emailId = findViewById(R.id.tv_emailId);
        tv_mobileNo = findViewById(R.id.tv_mobileNo);
        fab_edit = findViewById(R.id.fab_edit);
        iv_dp = findViewById(R.id.iv_dp);

        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContactInfoActivity.this, AddEditContactActivity.class);
                intent.putExtra(CONTACT_KEY, contact);
                startActivityForResult(intent, EDIT_CONTACT_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.save).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                deleteContact();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteContact() {
        ContactViewModel contactViewModel = new ContactViewModel(getApplication());
        contactViewModel.deleteContact(contact);
        Toast.makeText(this, "Contact delete", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK && requestCode == EDIT_CONTACT_REQUEST_CODE) {
            contact = (Contact) data.getSerializableExtra(CONTACT_KEY);

            contactViewModel.updateContact(contact);

            showContactInfo();

            Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();
        }

    }


    private void showContactInfo() {
        String name, mobileNo, emailId, imagePath;

        tv_name.setText(contact.getName());
        tv_emailId.setText(contact.getEmailId());
        tv_mobileNo.setText(contact.getMobileNo());

        imagePath = contact.getImagePath();

        Picasso.get().load(Uri.parse(imagePath))
                .error(R.drawable.ic_person)
                .into(iv_dp);
    }

}
