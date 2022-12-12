package com.libraryloans;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/* **********************************************************
*                                                           *
*                                                           *
*   Created by Nathan (B00808241) and Damian (B00787753)    *
*                                                           *
*                                                           *
************************************************************/
public class LibraryLoans {

    List<Loan> loans = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<Item> items = new ArrayList<>();

    private Scanner input = new Scanner(System.in);
    PrintWriter pw = null;

    public static void main(String[] args)
            // throws to handle file handling exceptions
            throws IOException {
        LibraryLoans libraryMenu = new LibraryLoans();
        libraryMenu.getLoans();
        libraryMenu.getUsers();
        libraryMenu.getItems();
        libraryMenu.start();
    }

    public void getLoans() {

        try {
            Scanner scanner = new Scanner(new FileReader("LOANS.csv"));

            String itemString;
            scanner.nextLine();
            while (scanner.hasNext()) {
                itemString = scanner.nextLine();
                List<String> itemFields = Arrays.asList(itemString.split(","));
                Long barcode = Long.parseLong(itemFields.get(0));
                String userId = itemFields.get(1);
                String issueDate = itemFields.get(2);
                String dateDue = itemFields.get(3);
                Integer numRenews = Integer.parseInt(itemFields.get(4));

                Loan loan = new Loan(barcode, userId, issueDate, dateDue, numRenews);
                loans.add(loan);

            }

        } // Catch the an error in accessing or processing the LOANS
        catch (FileNotFoundException ex) {
            System.out.println("Error retrieving List of Loans");
        }
    }

    public void getUsers() {

        try {
            Scanner scanner = new Scanner(new FileReader("USERS.csv"))
                    .useDelimiter("s\\    ");

            String itemString;
            scanner.nextLine();
            while (scanner.hasNext()) {
                itemString = scanner.nextLine();
                List<String> itemFields = Arrays.asList(itemString.split(","));
                String userId = itemFields.get(0);
                String firstName = itemFields.get(1);
                String lastName = itemFields.get(2);
                String email = itemFields.get(3);

                User user = new User(userId, firstName, lastName, email);
                users.add(user);

            }

        } // Catch the an error in accessing or processing the USERS 
        catch (FileNotFoundException ex) {
            System.out.println("Error retrieving List of Users");
        }
    }

    public void getItems() {

        try {
            Scanner scanner = new Scanner(new FileReader("ITEMS.csv"));

            String itemString;
            scanner.nextLine();
            while (scanner.hasNext()) {
                itemString = scanner.nextLine();
                List<String> itemFields = Arrays.asList(itemString.split(","));
                Long barcode = Long.parseLong(itemFields.get(0));
                String author = itemFields.get(1);
                String title = itemFields.get(2);
                String type = itemFields.get(3);
                Integer year = Integer.parseInt(itemFields.get(4));
                String isbn = itemFields.get(5);

                Item item = new Item(barcode, author, title, type, year, isbn);
                items.add(item);

            }

        } // Catch the an error in accessing or processing the ITEMS 
        catch (FileNotFoundException ex) {
            System.out.println("Error retrieving List of Items");
        }
    }

    private void start() { // Menu Method

        String answer;
        System.out.println("Welcome to the Library Menu");
        System.out.println("Please Select an Option: ");
        System.out.println("1. Issue an Item"); // Done
        System.out.println("2. Renew an existing Loan"); // Done
        System.out.println("3. Return an Item"); // Done
        System.out.println("4. View all items currently on Loan"); // Done
        System.out.println("5. View all items held by library"); // Done
        System.out.println("6. Exit Program"); // Done

        System.out.println("\nYour Choice: ");
        answer = input.nextLine();

        switch (answer) { // User Selection Menu

            case "1": // Issue an Item

                try {
                    System.out.println("\nIssuing an Item ");
                    System.out.println("Please enter Barcode: ");
                    Long barcode = Long.parseLong(input.nextLine());
                    System.out.println("\nPlease enter User ID: ");
                    String userId = input.nextLine();
                    createLoan(barcode, userId);
                    returnToMenu();

                } catch (Exception ex) {
                    System.out.println("Invalid Input. " + ex.getMessage());
                    returnToMenu();
                }
                break;
            case "2": // Renew Loan

                System.out.println("\nRenew a Loan");
                System.out.println("Please enter Barcode: ");
                try {
                    Long barcodeForRenewal = Long.parseLong(input.nextLine());
                    Loan loan = findLoanByBarcode(barcodeForRenewal);
                    if (numRenewsWithinLimit(loan)) {
                        String type = findTypeByBarcode(barcodeForRenewal);
                        String dueDate = loan.getDueDate();
                        LocalDate increasedDueDate = increaseDueDate(type, dueDate);
                        String formattedDate = getFormattedDate(increasedDueDate);
                        loan.setDueDate(formattedDate);
                        loan.increaseNumRenews();
                        returnToMenu();

                    } else {
                        System.out.println("Could not renew loan, renewal limit reached.");
                        returnToMenu();
                    }
                    break;
                } catch (Exception ex) {
                    System.out.println("Error renewing loan. " + ex.getMessage());
                    returnToMenu();
                    break;
                }
            case "3": // Return Item

                try {
                    System.out.println("\nReturning Item ");
                    System.out.println("Please enter Barcode of Item to return: ");
                    Long barcode = Long.parseLong(input.nextLine());
                    deleteLoanIfExists(barcode);
                    returnToMenu();

                } catch (NumberFormatException ex) {
                    System.out.println("Invalid Input. " + ex.getMessage());
                    returnToMenu();
                }
                break;
            case "4": // View Current Loans
                System.out.println("Displaying List of Items Currently on Loan: \n");
                showLoans();
                returnToMenu();

                break;
            case "5": // View Items
                System.out.println("Displaying Items held by Library: \n");
                showItems();
                returnToMenu();

                break;
            case "6": // Exit Program
                saveToCsv();
                System.out.println("Goodbye! ");
                System.exit(0);
                break;
            default:
                System.out.println("\nUnexpected value please select options 1 - 6\n");
                start();
        }
    }

    public void showItems() {
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    public void showUsers() {
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public void showLoans() {
        for (Loan loan : loans) {
            System.out.println(loan.toString());
        }
    }

    private void createLoan(Long barcode, String userId) {
        if (barcodeExists(barcode) && userIdExists(userId)) {
            try {
                String todaysDate = getFormattedDate(LocalDate.now());
                String itemType = findTypeByBarcode(barcode);
                String dueDate = getDueDate(itemType);
                Loan loan = new Loan(barcode, userId,
                        todaysDate, dueDate, 0);
                loans.add(loan);
                System.out.println("Loan Created Successfully. ");
            } catch (Exception ex) {
                System.out.println("Error creating loan. " + ex.getMessage());
            }
        } else {
            System.out.println("Barcode or User ID does not exist. ");
        }
    }

    private boolean barcodeExists(Long barcode) {
        for (Item item : items) {
            if (item.getBarcode().equals(barcode)) {
                return true;
            }
        }
        return false;
    }

    private boolean userIdExists(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private boolean deleteLoanIfExists(Long barcode) {
        for (Loan loan : loans) {
            if (loan.getBarcode().equals(barcode)) {
                loans.remove(loan);
                System.out.println("\nItem Successfully Returned ");
                return true;
            }
        }
        System.out.println("\nInvalid Barcode ");
        return false;
    }

    private String getFormattedDate(LocalDate date) {

//        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String getDueDate(String type) throws Exception {
        switch (type) {
            case "Book":
                return getFormattedDate(LocalDate.now().plusWeeks(4));
            case "Multimedia":
                return getFormattedDate(LocalDate.now().plusWeeks(1));
            default:
                throw new Exception("Invalid type, could not get due date.");
        }
    }

    private String findTypeByBarcode(Long barcode) throws Exception {
        for (Item item : items) {
            if (item.getBarcode().equals(barcode)) {
                return item.getType();
            }
        }
        throw new Exception("Could not find item by barcode.");
    }

    private Loan findLoanByBarcode(Long barcode) throws Exception {
        for (Loan loan : loans) {
            if (loan.getBarcode().equals(barcode)) {
                return loan;
            }
        }
        throw new Exception("Could not find loan by barcode.");
    }

    private LocalDate increaseDueDate(String type, String dueDate) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dueDate, formatter);

        switch (type) {
            case "Book":
                return localDate.plusWeeks(2);
            case "Multimedia":
                return localDate.plusWeeks(1);
            default:
                throw new Exception("Cannot increase Due Date.");
        }
    }

    private boolean numRenewsWithinLimit(Loan loan) throws Exception {
        Long barcode = loan.getBarcode();
        String type = findTypeByBarcode(barcode);
        Integer numRenews;

        if (type.equals("Book")) {
            numRenews = loan.getNumRenews();
            if (numRenews >= 3) {
                System.out.println("Item is a Book, max amount of renews is 3. ");
                return false;

            } else {
                System.out.println("Item is being renewed. ");
                return true;
            }
        } else if (type.equals("Multimedia")) {
            numRenews = loan.getNumRenews();
            if (numRenews >= 2) {
                System.out.println("Item is Multimedia, max amount of renews is 2. ");
                return false;

            } else {
                System.out.println("Item is being renewed. ");
                return true;
            }

        } else {
            throw new Exception("Please enter correct Barcode.");
        }
    }

    public void saveToCsv() {
        try {
            pw = new PrintWriter("LOANS.csv");
            // Manually setting Headings to match original
            pw.println("Barcode,User_id,Issue_Date,Due_Date,numRenews");
            for (Loan loan : loans) {
                pw.write(loan.toCsv());
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error retrieving List of Loans");
        }
    }

    private void returnToMenu() {
        System.out.println("\nWould you like to return to the menu Y/N? \n");
        String backToMenu = input.nextLine();
        backToMenu = backToMenu.toUpperCase();

        if (backToMenu.equals("Y")) {
            this.start();
        } else {
            saveToCsv();
            System.out.println("Existing Loans Successfully Saved ");
            System.out.println("Program Exiting ");
        }
    }
}
