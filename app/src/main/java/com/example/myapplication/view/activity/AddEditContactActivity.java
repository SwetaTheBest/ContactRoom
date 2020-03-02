package com.example.myapplication.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.model.Contact;
import com.squareup.picasso.Picasso;

import static com.example.myapplication.utils.Constants.ADD_CONTACT_REQUEST_CODE;
import static com.example.myapplication.utils.Constants.BROWSE_IMAGE_REQUEST_CODE;
import static com.example.myapplication.utils.Constants.CONTACT_KEY;

public class AddEditContactActivity extends AppCompatActivity {

    private EditText et_name, et_mobileNo, et_emailId;
    private String imagePath = "NA";
    private de.hdodenhof.circleimageview.CircleImageView iv_dp;
    private int id = -1;
    private Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);
        init();

        Intent intent = getIntent();

        contact = (Contact) intent.getSerializableExtra(CONTACT_KEY);

        if (contact != null) {
            updateContact();
        }
        iv_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, BROWSE_IMAGE_REQUEST_CODE);
            }
        });

    }

    private void updateContact() {
        String name, mobileNo, emailId;

        et_name.setText(contact.getName());
        et_mobileNo.setText(contact.getMobileNo());
        et_emailId.setText(contact.getEmailId());
        imagePath = contact.getImagePath();

        Picasso.get().load(Uri.parse(imagePath))
                .error(R.drawable.ic_person)
                .into(iv_dp);
    }

    private void init() {
        et_name = findViewById(R.id.et_name);
        et_mobileNo = findViewById(R.id.et_mobileNo);
        et_emailId = findViewById(R.id.et_emailId);
        iv_dp = findViewById(R.id.iv_dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.delete).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveContact();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveContact() {
        String name, mobileNo, emailId;
        int id = -1;

        name = et_name.getText().toString();
        mobileNo = et_mobileNo.getText().toString();
        emailId = et_emailId.getText().toString();

        Intent intent = new Intent();


        if (contact != null) {
            id = contact.getId();
            Contact contact = new Contact();
            contact.setName(name);
            contact.setMobileNo(mobileNo);
            contact.setEmailId(emailId);
            contact.setImagePath(imagePath);
            contact.setId(id);
            intent.putExtra(CONTACT_KEY, contact);
            setResult(RESULT_OK, intent);

        } else {
            Contact contact = new Contact();
            contact.setName(name);
            contact.setMobileNo(mobileNo);
            contact.setEmailId(emailId);
            contact.setImagePath(imagePath);
            intent.putExtra(CONTACT_KEY, contact);
            setResult(RESULT_OK, intent);

        }
        finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        assert data != null;
        if (data.getData() != null) {
            imagePath = data.getData().toString();

            Picasso.get().load(data.getData())
                    .error(R.drawable.ic_person)
                    .into(iv_dp);
        }
    }
}
