package com.example.myapplication.view.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewContactsAdapter extends RecyclerView.Adapter<ViewContactsAdapter.ContactViewHolder> {
    private List<Contact> allContacts = new ArrayList<>();
    private OnItemCLickListener listener;

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ietm_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = allContacts.get(position);
        holder.tv_name.setText(contact.getName());
        Picasso.get()
                .load(Uri.parse(contact.getImagePath()))
                .error(R.drawable.ic_person)
                .into(holder.iv_dp);
    }

    @Override
    public int getItemCount() {
        return allContacts.size();
    }

    public void setAllContacts(List<Contact> allContacts) {
        this.allContacts = allContacts;
        notifyDataSetChanged();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        de.hdodenhof.circleimageview.CircleImageView iv_dp;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_dp = itemView.findViewById(R.id.iv_dp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact contact = allContacts.get(getAdapterPosition());
                    listener.onItemClick(contact);
                }
            });
        }
    }

    public interface OnItemCLickListener {
        void onItemClick(Contact contact);
    }

    public void onClickListener(OnItemCLickListener listener) {
        this.listener = listener;
    }
}
