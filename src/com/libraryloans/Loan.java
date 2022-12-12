package com.libraryloans;

public class Loan {

    private Long barcode;
    private String userId;
    private String issueDate;
    private String dueDate;
    private Integer numRenews;

    public Loan(Long barcode, String userId, String issueDate,
            String dueDate, Integer numRenews) {

        this.barcode = barcode;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.numRenews = numRenews;
    }

    @Override
    public String toString() {  // Printing Lists to Formatted String
        return "Barcode: " + barcode
                + System.lineSeparator()
                + "User ID: " + userId
                + System.lineSeparator()
                + "Issue Date: " + issueDate
                + System.lineSeparator()
                + "Due Date: " + dueDate
                + System.lineSeparator()
                + "Number of Renews: " + numRenews
                + System.lineSeparator();

    }
    
    public String toCsv() { //Changing format to match original file 
        return barcode + "," + userId + "," + issueDate +  "," + dueDate + ","
                + numRenews + System.lineSeparator();
                
    }
    
    public Long getBarcode() {
        return barcode;
    }

    public String getUserId() {
        return userId;
    }

    public String getissueDate() {
        return issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Integer getNumRenews() {
        return numRenews;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void increaseNumRenews() {
        numRenews++;
    }
}
