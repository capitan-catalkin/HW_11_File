package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class BookModel {


    private String author;

    private String bookTitle;
    private int pages;
    private boolean newBook;
    private List<String> bookChapters;

    public String getAuthor() {
        return author;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getPages() {
        return pages;
    }

    public boolean isNewBook() {
        return newBook;
    }

    public List<String> getBookChapters() {
        return bookChapters;
    }

}

