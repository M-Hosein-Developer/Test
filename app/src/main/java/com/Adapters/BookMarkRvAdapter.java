package com.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.Models.DataItem;
import com.bumptech.glide.Glide;
import com.example.contact.R;
import com.example.contact.databinding.BookmarkItemBinding;
import com.example.contact.databinding.ContactItemBinding;

import java.util.ArrayList;

public class BookMarkRvAdapter extends RecyclerView.Adapter<BookMarkRvAdapter.BookMarkRvHolder>{

    ArrayList<DataItem> dataItems;
    LayoutInflater layoutInflater;

    public BookMarkRvAdapter(ArrayList<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public BookMarkRvAdapter.BookMarkRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        BookmarkItemBinding bookmarkItemBinding = DataBindingUtil.inflate(layoutInflater , R.layout.bookmark_item , parent , false);
        return new BookMarkRvHolder(bookmarkItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkRvAdapter.BookMarkRvHolder holder, int position) {
        holder.bind(dataItems.get(position));
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

    static class BookMarkRvHolder extends RecyclerView.ViewHolder{

        BookmarkItemBinding bookmarkItemBinding;

        public BookMarkRvHolder(BookmarkItemBinding bookmarkItemBinding) {
            super(bookmarkItemBinding.getRoot());
            this.bookmarkItemBinding = bookmarkItemBinding;
        }



        public void bind(DataItem dataItem){

            bookmarkItemBinding.NameAndLast.setText(dataItem.getTitle()+ " " + dataItem.getFirst() + " " + dataItem.getLast());
            bookmarkItemBinding.PhoneNumber.setText(dataItem.getPhone());

            ProfilItem(dataItem);

        }

        private void ProfilItem(DataItem dataItem) {
            Glide.with(bookmarkItemBinding.getRoot().getContext())
                    .load("https://randomuser.me/api/portraits/men/1.jpg")
                    .thumbnail(Glide.with(bookmarkItemBinding.getRoot().getContext()).load(R.drawable.loading))
                    .into(bookmarkItemBinding.Profile);

        }

    }

}
