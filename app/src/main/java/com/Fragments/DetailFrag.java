package com.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Models.DataItem;
import com.bumptech.glide.Glide;
import com.example.contact.R;
import com.example.contact.databinding.ContactItemBinding;
import com.example.contact.databinding.FragmentDetailBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailFrag extends Fragment {

    FragmentDetailBinding detailBinding;
    boolean BookmarkIsChecked = false;
    ArrayList<String> bookmarksArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        detailBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_detail , container , false);

        DataItem dataItem = getArguments().getParcelable("model");

        initializeText(dataItem);
        setProfile(dataItem);
        BookMarkButtom(dataItem);

        // Inflate the layout for this fragment
        return detailBinding.getRoot();
    }

    private void BookMarkButtom(DataItem dataItem) {

        ReadDataStore();

        if (bookmarksArray.contains(dataItem.getTitle())) {
            BookmarkIsChecked = true;
            detailBinding.bookmarkbtn.setImageResource(R.drawable.ic_baseline_bookmark_24);
        } else {
            BookmarkIsChecked = false;
            detailBinding.bookmarkbtn.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
        }

        detailBinding.bookmarkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BookmarkIsChecked == false) {
                    if (!bookmarksArray.contains(dataItem.getTitle())){
                        bookmarksArray.add(dataItem.getTitle());
                    }
                    writetoDataStore();
                    detailBinding.bookmarkbtn.setImageResource(R.drawable.ic_baseline_bookmark_24);
                    BookmarkIsChecked = true;

                } else {
                    detailBinding.bookmarkbtn.setImageResource(R.drawable.ic_baseline_bookmark_border_24);

                    bookmarksArray.remove(dataItem.getTitle());
                    writetoDataStore();
                    BookmarkIsChecked = false;
                }
            }


        });
    }

    private void ReadDataStore() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("bookmarks", String.valueOf(new ArrayList<String>()));
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        bookmarksArray = gson.fromJson(json, type);
        Log.e("TAG", "ReadDataStore: " + bookmarksArray);
    }

    private void writetoDataStore() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(bookmarksArray);

        editor.putString("bookmarks", json);
        editor.apply();
    }

    private void setProfile(DataItem dataItem) {
        Glide.with(detailBinding.getRoot().getContext())
                .load("https://randomuser.me/api/portraits/men/1.jpg")
                .thumbnail(Glide.with(detailBinding.getRoot().getContext()).load(R.drawable.loading))
                .into(detailBinding.Profiledetail);
    }

    private void initializeText(DataItem dataItem) {

        String Title = dataItem.getTitle();
        String FirtNam = dataItem.getFirst();
        String LastName = dataItem.getLast();

        String Phone = dataItem.getPhone();
        String Country = dataItem.getCountry();
        String City = dataItem.getCity();
        Number PostNum = dataItem.getPostcode();

        detailBinding.Nametxt.setText(Title + " " + FirtNam + " " + LastName);
        detailBinding.PhoneNum.setText(Phone);
        detailBinding.location.setText(Country + " " + City + " " + PostNum);
    }
}