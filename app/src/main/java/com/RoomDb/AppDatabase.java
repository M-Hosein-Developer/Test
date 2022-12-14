package com.RoomDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.RoomDb.Converter.AllContactModelConverter;
import com.RoomDb.Entites.ContactListEntity;

@TypeConverters({AllContactModelConverter.class})
@Database(entities = {ContactListEntity.class} , version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String Db_Name = "AppDb";
    private static AppDatabase instance;
    public abstract RoomDao roomDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext() , AppDatabase.class , Db_Name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
