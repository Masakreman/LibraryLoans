package com.libraryloans;

public class Item {

    private Long barcode;
    private String author;
    private String title;
    private String type;
    private Integer year;
    private String isbn;

    public Item(Long barcode, String author, String title, String type, Integer year, String isbn) {

        this.barcode = barcode;
        this.author = author;
        this.title = title;
        this.type = type;
        this.year = year;
        this.isbn = isbn;
    }

    @Override
    public String toString() { // Printing Lists to Formatted String
        return "Barcode = " + barcode
                + System.lineSeparator()
                + "Author = " + author
                + System.lineSeparator()
                + "Title = " + title
                + System.lineSeparator()
                + "Type = " + type
                + System.lineSeparator()
                + "Year = " + year
                + System.lineSeparator()
                + "Isbn = " + isbn
                + System.lineSeparator();
    }
    
    // Getters and Setters
    public Long getBarcode() {
        return barcode;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public Integer getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }
}
