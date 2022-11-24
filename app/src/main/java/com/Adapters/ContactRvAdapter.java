package com.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.Models.AllContactModel;
import com.Models.DataItem;
import com.ViewModel.AppViewModel;
import com.bumptech.glide.Glide;
import com.example.contact.R;
import com.example.contact.databinding.ContactItemBinding;

import java.util.ArrayList;

import dagger.hilt.android.qualifiers.ActivityContext;

public class ContactRvAdapter extends RecyclerView.Adapter<ContactRvAdapter.ContactRvHolder> {

    ArrayList<DataItem> dataItems;
    LayoutInflater layoutInflater;

    public ContactRvAdapter(ArrayList<DataItem> dataItems) {
        this.dataItems = dataItems;
    }


    @NonNull
    @Override
    public ContactRvAdapter.ContactRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ContactItemBinding contactItemBinding = DataBindingUtil.inflate(layoutInflater , R.layout.contact_item , parent , false);
        return new ContactRvHolder(contactItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRvAdapter.ContactRvHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(dataItems.get(position));

        holder.contactItemBinding.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("model",dataItems.get(position));
                Navigation.findNavController(v).navigate(R.id.action_ContactFragment_to_detailFrag , bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public void updataData(ArrayList<DataItem> newdata){
        dataItems.clear();
        dataItems.addAll(newdata);
        notifyDataSetChanged();
    }

    static class ContactRvHolder extends RecyclerView.ViewHolder{

        ContactItemBinding contactItemBinding;

        public ContactRvHolder(ContactItemBinding contactItemBinding) {
            super(contactItemBinding.getRoot());
            this.contactItemBinding = contactItemBinding;
        }



        public void bind(DataItem dataItem){

            contactItemBinding.NameAndLast.setText(dataItem.getTitle()+ " " + dataItem.getFirst() + " " + dataItem.getLast());
            contactItemBinding.PhoneNumber.setText(dataItem.getPhone());

            ProfilItem(dataItem);

        }

        private void ProfilItem(DataItem dataItem) {
            Glide.with(contactItemBinding.getRoot().getContext())
                    .load("https://randomuser.me/api/portraits/men/1.jpg")
                    .thumbnail(Glide.with(contactItemBinding.getRoot().getContext()).load(R.drawable.loading))
                    .into(contactItemBinding.Profile);

        }

    }
}
