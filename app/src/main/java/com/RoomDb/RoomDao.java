package com.RoomDb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.RoomDb.Entites.ContactListEntity;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContactListEntity contactListEntity);

    @Query("SELECT * FROM AllContact")
    Flowable<ContactListEntity> getAllContatcData();

}
