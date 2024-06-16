/**
 * A class that contains all the Shelf objects
 */
public class BookRepository{

    /** Array to store shelves */
    private Shelf [] shelves;

    /**
     * Constructor to create 10 Shelf objects in shelves[]
     */
    public BookRepository(){
        shelves = new Shelf[10];
        for(int x = 0; x < shelves.length;x++)
            shelves[x] = new Shelf();
    }

    /**
     * Returns a Book object from a specified ISBN
     * @param getISBN the Book object's ISBN
     * @return Book
     * @throws BookDoesNotExistException if the ISBN is not found
     */
    public Book getBook(long getISBN) throws BookDoesNotExistException{

        //gets the first # of the ISBN since it determines the shelf number
        String ISBN = String.valueOf(getISBN);
        String shelfNumber = ISBN.substring(0,1);
        int shelfN = Integer.parseInt(shelfNumber);

        //if the ISBN is less than 13 digits, it has to have leading 0s added
        //meaning it will have to be in Shelf #0
        if(String.valueOf(getISBN).length() < 13){      
            shelfN = 0;                                 
        }

        Book temp = shelves[shelfN].getHeadBook();

        while(temp != null){
            if(temp.getISBN() == getISBN)
                return temp;
            temp = temp.getNextBook();
        }
        
        //throwing exception since if the while loop didn't return before this line
        //that means it didn't find the book
        throw new BookDoesNotExistException("Book Does Not Exist!");
        
            
    }

    /**
     * Checks in book with the ISBN specified
     * @param checkedInISBN the ISBN of the book to be checked in
     */
    public void checkInBook(long checkedInISBN){

        //gets the first # of the ISBN since it determines the shelf number
        String ISBN = String.valueOf(checkedInISBN);
        String shelfNumber = ISBN.substring(0,1);
        int shelfN = Integer.parseInt(shelfNumber);

        //if the ISBN is less than 13 digits, it has to have leading 0s added
        //meaning it will have to be in Shelf #0
        if(String.valueOf(checkedInISBN).length() < 13){        
            shelfN = 0;
        }
        
        Book temp = shelves[shelfN].getHeadBook();

        //sets the checkedOut, checkOutUserID, checkOutDate, dueDate to false/-1/null
        //to indicate that the Book is no longer checkedOut 
        while(temp != null){
            if(temp.getISBN() == checkedInISBN){
                temp.setCheckedOut(false);
                temp.setCheckOutUserID(-1);
                temp.setCheckOutDate(null);
                temp.setDueDate(null);
            }
            temp = temp.getNextBook();
        }
    }
    
    /**
     * Checks out the book with the ISBN specified
     * @param checkedOutISBN the Book to check out
     * @param checkOutUserID the userID checking the Book out
     * @param dueDate the date the Book is due by
     * @param checkOutDate the date the Book is being checked out
     * @throws BookAlreadyCheckedOutException if the Book is already checkedOut
     * @throws BookDoesNotExistException if the Book is not located
     * @throws InvalidISBNException if the ISBN is too long
     * @throws InvalidUserIDException if the userID is not 10 digits long
     */
    public void checkOutBook(long checkedOutISBN, long checkOutUserID, Date dueDate, Date checkOutDate) throws BookAlreadyCheckedOutException, BookDoesNotExistException, InvalidISBNException, InvalidUserIDException{
        
        if(String.valueOf(checkedOutISBN).length() > 13)
            throw new InvalidISBNException("Invalid ISBN!");

        if(String.valueOf(checkOutUserID).length() != 10)
            throw new InvalidUserIDException("Invalid UserID");


        //gets the first # of the ISBN since it determines the shelf number
        String ISBN = String.valueOf(checkedOutISBN);
        String shelfNumber = ISBN.substring(0,1);
        int shelfN = Integer.parseInt(shelfNumber);

        if(String.valueOf(checkedOutISBN).length() < 13){   //any ISBN less than 13 digits, is padded with leading 0s, so it has to be in shelf 0;
            shelfN = 0;
        }

        Book temp = shelves[shelfN].getHeadBook();

        while(temp != null){

            if(temp.getISBN() == checkedOutISBN){
                if(temp.isCheckedOut())
                    throw new BookAlreadyCheckedOutException("Book Already Checked Out!");          //check if book is already checkedOut
                else{
                    temp.setCheckOutDate(checkOutDate);
                    temp.setCheckedOut(true);
                    temp.setCheckOutUserID(checkOutUserID);
                    temp.setDueDate(dueDate);   
                    break;
                }
            }
            temp = temp.getNextBook();
        }

        //since we break out of while loop when we find the book, if temp == null, we went through the whole loop without finding the book
        if(temp == null)
            throw new BookDoesNotExistException("Book Does Not Exist!");
    }

    /**
     * Adds Book to BookRepository on the correct shelf
     * @param addISBN ISBN of Book
     * @param addName Name of Book
     * @param addAuthor Author of Book
     * @param addGenre Genre of Book
     * @param addCondition Condition of Book(NEW,GOOD,BAD,REPLACE)
     * @param addYearPublished Year of Book
     * @throws BookAlreadyExistsException if the ISBN already exists
     * @throws InvalidISBNException if the ISBN is too long
     */
    public void addBook(long addISBN, String addName, String addAuthor, String addGenre, Book.Condition addCondition, int addYearPublished) throws BookAlreadyExistsException, InvalidISBNException{
        
        if(String.valueOf(addISBN).length() > 13)
            throw new InvalidISBNException("Invalid ISBN");

        Book add = new Book(addName, addAuthor, addGenre, addCondition, addISBN, addYearPublished);

        //checks for a duplicate ISBN
        for(int x = 0; x < shelves.length; x++){
            Book temp = shelves[x].getHeadBook();
            while(temp != null){
                if(temp.getISBN() == addISBN)
                    throw new BookAlreadyExistsException("Book Already Exists!");
                temp = temp.getNextBook();
            }
            
        }

        //gets the first # of the ISBN since it determines the shelf number
        String ISBN = String.valueOf(addISBN);
        String shelfNumber = ISBN.substring(0,1);
        int shelfN = Integer.parseInt(shelfNumber);

        if(String.valueOf(addISBN).length() < 13)       //any ISBN less than 13 digits, is padded with leading 0s, so it has to added to shelf 0;
            shelfN = 0;                                 

        //Calls method addBook on the correct Shelf Number
        shelves[shelfN].addBook(add);

        //sorts the shelf after a Book is added based on the shelf's sorting criteria
        shelves[shelfN].sort(shelves[shelfN].getSortCriteria());
    }

    /**
     * Removes a Book with the given ISBN
     * @param removeISBN the ISBN of Book to be removed
     * @throws BookDoesNotExistException if the ISBN is not found
     * @throws InvalidISBNException if the ISBN is too long
     */
    public void removeBook(long removeISBN) throws BookDoesNotExistException, InvalidISBNException{
        
        //gets the first # of the ISBN since it determines the shelf number
        String ISBN = String.valueOf(removeISBN);
        String shelfNumber = ISBN.substring(0,1);
        int shelfN = Integer.parseInt(shelfNumber);

        if(String.valueOf(removeISBN).length() < 13)        //any ISBN less than 13 digits, is padded with leading 0s, so it has to be in shelf 0;
            shelfN = 0;

        try{
            shelves[shelfN].removeBook(ISBN);
        }

        catch(BookDoesNotExistException e){
            throw new BookDoesNotExistException("Book Does Not Exist!");
        }

        catch(InvalidISBNException i){
            throw new InvalidISBNException("Invalid IBSN");
        }
    }

    /**
     * Sorts a specified shelf with a given criteria
     * @param shelfInd the shelf number to sort(0-9)
     * @param sortCriteria the criteria to sort by(ISBN, Name, Author, Genre, Year, Condition)
     * @throws InvalidSortCriteriaException if the sortCriteria is not an option
     */
    public void sortShelf(int shelfInd, String sortCriteria) throws InvalidSortCriteriaException{
        
        try{
            //gets the sorting criteria of the specified shelf, since each shelf could have a 
            //different sorting criteria
            Shelf.SortCriteria s = Shelf.SortCriteria.valueOf(sortCriteria.toUpperCase());      
            shelves[shelfInd].sort(s);
        }

        //if sortCriteria is not found in the enum, it throws an illegalArgumentException
        catch(IllegalArgumentException e){
                throw new InvalidSortCriteriaException("Invalid Sort Criteria!");
        }

    }

    /**
     * Prints a specified shelf with its first column dependent on its sorting criteria 
     * @param shelfNumber the shelfNumber to print(0-9)
     */
    public void print(int shelfNumber){
        shelves[shelfNumber].print(shelves[shelfNumber].getSortCriteria());
    }
}