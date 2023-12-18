package com.example.tatproject.ForRecyclerView;

public class PackageSingleItem {
    String packageID;
    public String title;
    String price;
    int resID;
    int resID2;

    public PackageSingleItem(String title, String packID, String price, int resID, int resID2){
        this.packageID = packID;
        this.title = title;
        this.price = price;
        this.resID = resID;
        this.resID2 = resID2;
    }
}
