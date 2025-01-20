package com.xubop961.retrofitexample;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Drinks {
    @SerializedName("drinks")
    List<Coctail> drinks = new ArrayList<>();

    public class Coctail {
        @SerializedName("strDrink")
        public String coctailName;
        @SerializedName("strDrinkThumb")
        public String coctailImageUrl;
        @SerializedName("idDrink")
        public String coctailId;
    }
}
