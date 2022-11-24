package com.RoomDb.Entites;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.Models.AllContactModel;

@Entity(tableName = "AllContact")
public class ContactListEntity {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "AllContatc")
    public AllContactModel allContactModel;

    public ContactListEntity(AllContactModel allContactModel) {
        this.allContactModel = allContactModel;
    }


    public int getId(){
        return uid;
    }

    public AllContactModel getAllContactModel(){
        return allContactModel;
    }

}
