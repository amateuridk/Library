/**
 * A class that represents an endless shelf of Book objects
 */
public class Shelf{

    /** the first book on the shelf */
    private Book headBook;

    /** the last book on the shelf */
    private Book tailBook;

    /** the amount of Books on the shelf */
    private int length;

    /** the avaible sorting criteria */
    enum SortCriteria{
        ISBN, NAME, AUTHOR, GENRE, CONDITION, YEAR
    }

    /** the current sorting criteria */
    private SortCriteria shelfSortCriteria;

    /** 
     * Default constructor creating an empty shelf
     */
    public Shelf(){
        shelfSortCriteria = SortCriteria.ISBN;      //default sorting criteria is by ISBN
        headBook = null;
        tailBook = null;
        length = 0;
    }

    /**
     * Gets the first book on the shelf
     * @return headBook
     */
    public Book getHeadBook(){
        return headBook;
    }

    /**
     * Gets the current sorting criteria
     * @return shelfSortCriteria
     */
    public SortCriteria getSortCriteria(){
        return shelfSortCriteria;
    }
    
    /**
     * Adds Book to Shelf while maintaining current sorting criteria
     * @param addedBook the Book to be added
     * @throws BookAlreadyExistsException if the ISBN of the addedBook already exists
     */
    public void addBook(Book addedBook) throws BookAlreadyExistsException{

        //checks for duplicate ISBNs
        if(checkISBN(addedBook))
            throw new BookAlreadyExistsException("A Book With the Same ISBN Already Exists!, Cannot Add!");

        //edge case of shelf being empty
        if(length == 0){
            headBook = addedBook;
            tailBook = addedBook;
            length++;
        }

        //initially adding the new book as the headBook, then call the sort method to sort it
        else{
            addedBook.setNextBook(headBook);
            headBook = addedBook;
            length++;
            sort(shelfSortCriteria);
        }
    }

    /**
     * Removes a book with the given ISBN from the shelf
     * @param removedISBN the ISBN of the Book to be removed
     * @throws BookDoesNotExistException if the Book isn't in the shelf
     * @throws InvalidISBNException if the ISBN is too long 
     */
    public void removeBook(String removedISBN) throws BookDoesNotExistException, InvalidISBNException{
        
        if(removedISBN.length() > 13)
            throw new InvalidISBNException("Invalid ISBN!");

        if(length==0)
            throw new BookDoesNotExistException("Cannot Remove From Empty Shelf");
        
        //edge case of removing the first book on shelf
        else if(headBook.getISBN() == Long.parseLong(removedISBN)){
            headBook = headBook.getNextBook();
            length--;
        }
        
        else{
            Book prev = null;
            Book curr = headBook;

            while(curr!=null && curr.getISBN() != Long.parseLong(removedISBN)){
                prev = curr;
                curr = curr.getNextBook();
            }

            //if curr is null after the loop, then the ISBN was not located
            if(curr == null)
                throw new BookDoesNotExistException("Book Does Not Exist!");
            
            else{
                prev.setNextBook(curr.getNextBook());
                length--;
                if(prev.getNextBook() == null)
                    tailBook = prev;
            }
        }
    }

    /**
     * Checks if a given Book's ISBN already exists
     * @param added the book who's ISBN must be checked
     * @return true if the ISBN exists already exists, else false
     */
    public boolean checkISBN(Book added){
        Book temp = headBook;

        while(temp!=null){
            if(added.getISBN() == temp.getISBN()){
                return true;
            }
            temp = temp.getNextBook();
        }
        return false;
    }

    /**
     * Sorts the shelf with the given criteria and sets the sorting criteria to that
     * @param sortCriteria the criteria to sort by
     */
    public void sort(SortCriteria sortCriteria){
        shelfSortCriteria = sortCriteria;
        
        Book[] books;

        //general algo is that for each case: convert the shelf LL into an array of Books, sort the array based on the case, then reform the LL with new links based on the sorted array

        switch(shelfSortCriteria){
            
            case NAME:
                books = convert();      
                Book temp = null;
                for(int i = 0; i < books.length-1; i++){
                    for(int j = 1 + i; j < books.length; j++){
                        if((books[i].getName().toUpperCase()).compareTo(books[j].getName().toUpperCase()) >= 0){
                            temp = books[i];
                            books[i] = books[j];
                            books[j] = temp;
                        }
                    }
                }
                headBook = books[0];
                for(int x = 0; x < books.length-1; x++){
                    books[x].setNextBook(books[x+1]);
                }
                tailBook = books[books.length-1];
                tailBook.setNextBook(null);
                break;

            case ISBN:
                books = convert();
                Book temp1 = null;
                for(int i = 0; i < books.length-1; i++){
                    for(int j = 1 + i; j < books.length; j++){
                        if((books[i].getISBN() > books[j].getISBN())){
                            temp1 = books[i];
                            books[i] = books[j];
                            books[j] = temp1;
                        }
                    }
                }
                headBook = books[0];
                for(int x = 0; x < books.length-1; x++){
                    books[x].setNextBook(books[x+1]);
                }
                tailBook = books[books.length-1];
                tailBook.setNextBook(null);
                break;

            case AUTHOR: 
                books = convert();
                Book temp2 = null;
                for(int i = 0; i < books.length-1; i++){
                    for(int j = 1 + i; j < books.length; j++){
                        if((books[i].getAuthor().toUpperCase()).compareTo(books[j].getAuthor().toUpperCase()) >= 0){
                            temp2 = books[i];
                            books[i] = books[j];
                            books[j] = temp2;
                        }
                    }
                }
                headBook = books[0];
                for(int x = 0; x < books.length-1; x++){
                    books[x].setNextBook(books[x+1]);
                }
                tailBook = books[books.length-1];
                tailBook.setNextBook(null);
                break;

            case GENRE: 
                books = convert();
                Book temp3 = null;
                for(int i = 0; i < books.length-1; i++){
                    for(int j = 1 + i; j < books.length; j++){
                        if((books[i].getGenre().toUpperCase()).compareTo(books[j].getGenre().toUpperCase()) >= 0){
                            temp3 = books[i];
                            books[i] = books[j];
                            books[j] = temp3;
                        }
                    }
                }
                headBook = books[0];
                for(int x = 0; x < books.length-1; x++){
                    books[x].setNextBook(books[x+1]);
                }
                tailBook = books[books.length-1];
                tailBook.setNextBook(null);
                break;

            case CONDITION:
                books = convert();
                Book temp4 = null;
                for(int i = 0; i < books.length-1; i++){
                    for(int j = 1 + i; j < books.length; j++){
                        if(books[i].getCondition().name().compareTo(books[j].getCondition().name()) >= 0){
                            temp4 = books[i];
                            books[i] = books[j];
                            books[j] = temp4;
                        }
                    }
                }
                headBook = books[0];
                for(int x = 0; x < books.length-1; x++){
                    books[x].setNextBook(books[x+1]);
                }
                tailBook = books[books.length-1];
                tailBook.setNextBook(null);
                break;
            
            case YEAR:
                books = convert();
                Book temp5 = null;
                for(int i = 0; i < books.length-1; i++){
                    for(int j = 1 + i; j < books.length; j++){
                        if(books[i].getYear() > books[j].getYear()){
                            temp5 = books[i];
                            books[i] = books[j];
                            books[j] = temp5;
                        }
                    }
                }
                headBook = books[0];
                for(int x = 0; x < books.length-1; x++){
                    books[x].setNextBook(books[x+1]);
                }
                tailBook = books[books.length-1];
                tailBook.setNextBook(null);
                break;
                
        }
    }

    /**
     * Method that adds all the Book objects to an array
     * @return bookArray
     */
    public Book[] convert(){

        Book[] bookArray = new Book[length];
        int index = 0;
        Book temp = headBook;

        while(temp!=null){
            bookArray[index] = temp;
            index++;
            temp = temp.getNextBook();
        }
        return bookArray;
    }
 
    /**
     * Prints out the Shelf and each Book Object's attributes
     * The first column changes based on the sorting criteria
     * @param firstColumn what the first column should be
     */
    public void print(SortCriteria firstColumn){
        
        Book temp = headBook;
        String leadingISBN;     

        switch(firstColumn){
           
            case ISBN:
                
                System.out.println("Sorting by ISBN");
                System.out.println();
                System.out.printf("%-13s %20s %20s %20s %20s %40s %20s %20s %20s %20s","ISBN", "Checked Out", "Check Out Date", "Checkout UserID", "Name", "Author", "Genre", "Condition", "Year", "Due Date");
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println();
                while(temp != null){
                    leadingISBN = String.format("%013d", temp.getISBN());       //so the ISBN will always print as 13 characters, if it isn't 13 characters initially, will add the # of leading 0s needed
                    System.out.printf("%-13s %20s %20s %20s %40s %20s %20s %20s %20s %20s",leadingISBN, temp.isCheckedOut() ? "Y" : "N", temp.isCheckedOut() ? temp.getCheckOutDate().toString() : "N/A", temp.isCheckedOut() ? "" + temp.getCheckOutUserID() : "N/A", temp.getName(), temp.getAuthor(), temp.getGenre(), temp.getCondition().name(), "" + temp.getYear(), temp.isCheckedOut() ? temp.getDueDate().toString() : "N/A");
                    System.out.println();
                    temp = temp.getNextBook();
                }
                break;
            
            case AUTHOR:
                System.out.println("Sorting by Author");
                System.out.printf("%-17s %20s %20s %20s %30s %30s %20s %20s %20s %20s","AUTHOR", "Checked Out", "Check Out Date", "Checkout UserID", "Name", "Genre", "Condition", "Year", "ISBN", "Due Date");
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");                
                System.out.println();
                while(temp != null){
                    leadingISBN = String.format("%013d", temp.getISBN());
                    System.out.printf("%-17s %20s %20s %20s %40s %20s %20s %20s %20s %20s","" + temp.getAuthor(), temp.isCheckedOut() ? "Y" : "N", temp.isCheckedOut() ? temp.getCheckOutDate().toString() : "N/A", temp.isCheckedOut() ? "" + temp.getCheckOutUserID() : "N/A", temp.getName(), temp.getGenre(), temp.getCondition().name(), "" + temp.getYear(), leadingISBN, temp.isCheckedOut() ? temp.getDueDate().toString() : "N/A" );
                    System.out.println();
                    temp = temp.getNextBook();
                }
                break;

            case NAME: 
                System.out.println("Sorting by Name");
                System.out.printf("%-40s %20s %20s %20s %20s %20s %20s %20s %20s %20s","NAME", "Checked Out", "Check Out Date", "Checkout UserID", "Author", "Genre", "Condition", "Year", "ISBN", "Due Date");
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println();
                while(temp != null){
                    leadingISBN = String.format("%013d", temp.getISBN());
                    System.out.printf("%-40s %20s %20s %20s %20s %20s %20s %20s %20s %20s","" + temp.getName(), temp.isCheckedOut() ? "Y" : "N", temp.isCheckedOut() ? temp.getCheckOutDate().toString() : "N/A", temp.isCheckedOut() ? "" + temp.getCheckOutUserID() : "N/A", temp.getAuthor(), temp.getGenre(), temp.getCondition().name(), "" + temp.getYear(), leadingISBN, temp.isCheckedOut() ? temp.getDueDate().toString() : "N/A");
                    System.out.println();
                    temp = temp.getNextBook();
                }
                break;

            case GENRE: 
                System.out.println("Sorting by Genre");
                System.out.printf("%-15s %20s %20s %20s %30s %25s %22s %22s %22s %20s","GENRE", "Checked Out", "Check Out Date", "Checkout UserID", "Name", "Author", "Condition", "Year", "ISBN", "Due Date");
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");        
                System.out.println();
                while(temp != null){
                    leadingISBN = String.format("%013d", temp.getISBN());
                    System.out.printf("%-15s %20s %20s %20s %40s %20s %20s %20s %20s %20s","" + temp.getGenre(), temp.isCheckedOut() ? "Y" : "N", temp.isCheckedOut() ? temp.getCheckOutDate().toString() : "N/A", temp.isCheckedOut() ? "" + temp.getCheckOutUserID() : "N/A", temp.getName(), temp.getAuthor(), temp.getCondition().name(), temp.getYear() + "", leadingISBN,temp.isCheckedOut() ? temp.getDueDate().toString() : "N/A");
                    System.out.println();
                    temp = temp.getNextBook();
                }
                break;

            case CONDITION: 
                System.out.println("Sorting by Condition");
                System.out.printf("%-10s %20s %20s %20s %40s %20s %20s %20s %20s %20s","CONDITION", "Checked Out", "Check Out Date", "Checkout UserID", "Name", "Author", "Genre", "Year", "ISBN", "Due Date");
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");                
                System.out.println();
                while(temp != null){
                    leadingISBN = String.format("%013d", temp.getISBN());
                    System.out.printf("%-10s %20s %20s %20s %40s %20s %20s %20s %20s %20s","" + temp.getCondition().name(), temp.isCheckedOut() ? "Y" : "N", temp.isCheckedOut() ? temp.getCheckOutDate().toString() : "N/A", temp.isCheckedOut() ? "" + temp.getCheckOutUserID() : "N/A", temp.getName(), temp.getAuthor(), temp.getGenre(), temp.getYear() + "", leadingISBN, temp.isCheckedOut() ? temp.getDueDate().toString() : "N/A");
                    System.out.println();
                    temp = temp.getNextBook();
                }
                break;

            case YEAR:
                System.out.println("Sorting by Year");
                System.out.printf("%-10s %20s %20s %20s %40s %20s %20s %20s %20s %20s","YEAR", "Checked Out", "Check Out Date", "Checkout UserID", "Name", "Author", "Genre", "Condition", "ISBN", "Due Date");
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");     
                System.out.println();
                while(temp != null){
                    leadingISBN = String.format("%013d", temp.getISBN());
                    System.out.printf("%-10s %20s %20s %20s %40s %20s %20s %20s %20s %20s","" + temp.getYear(), temp.isCheckedOut() ? "Y" : "N", temp.isCheckedOut() ? temp.getCheckOutDate().toString() : "N/A", temp.isCheckedOut() ? "" + temp.getCheckOutUserID() : "N/A", temp.getName(), temp.getAuthor(), temp.getGenre(), temp.getCondition().name(), leadingISBN, temp.isCheckedOut() ? temp.getDueDate().toString() : "N/A");
                    System.out.println();
                    temp = temp.getNextBook();
                }
                break;

        }
    }
}
