package com.RoomDb.Converter;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.Models.AllContactModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class AllContactModelConverter {

    @TypeConverter
    public String tojson(AllContactModel allContactModel){
        if (allContactModel == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<AllContactModel>(){}.getType();
        String json = gson.toJson(allContactModel , type);
        return json;
    }

    @TypeConverter
    public AllContactModel toDataClass(String allContact){
        if (allContact == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<AllContactModel>(){}.getType();
        AllContactModel allContactModel = gson.fromJson(allContact , type);
        return allContactModel;
    }

}
