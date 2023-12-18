package com.example.groupwareproject2;

public class SingleItem {
    public String QATitle;
    public String writer;
    public String writeDate;
    public int articleNum;

    public SingleItem(String title, String writer, String writeDate, int num){
        this.QATitle = title;
        this.writer = writer;
        this.writeDate = writeDate;
        this.articleNum = num;
    }
}
