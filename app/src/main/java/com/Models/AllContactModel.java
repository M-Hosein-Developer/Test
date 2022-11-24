package com.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllContactModel {


    @SerializedName("info")
    private Listinfo listinfo;


    public Listinfo getListinfo(){return listinfo;}

        public static class Listinfo {

            @SerializedName("seed")
            private String seed;

            @SerializedName("results")
            private int results;

            @SerializedName("page")
            private int page;

            @SerializedName("version")
            private String version;


            public String getseed(){
                return seed;
            }

            public int getresults(){
                return results;
            }

            public int getpage(){
                return page;
            }

            public String getversion(){
                return version;
            }

        }


            @SerializedName("results")
            private List<DataItem> results;


            public List<DataItem> getResults(){
                return results;
            }


}
