package com.example.tatproject.ForRecyclerView;

public class BookSingleItem {
    String journey;
    public String title;
    String price;
    int packID;

    public BookSingleItem(String title, String journey, String price, int packID){
        this.journey = journey;
        this.title = title;
        this.price = price;
        this.packID = packID;
    }
}
