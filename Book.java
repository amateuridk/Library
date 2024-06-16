/**
 * A class representing a Book
 */
public class Book {

    /** The attributes of a Book object */
    
    private String name;
    private String author;
    private String genre;

    enum Condition{
       NEW, GOOD, BAD, REPLACE
    }

    private Condition bookCondition;

    private long ISBN;

    private long checkOutUserID;

    private int yearPublished;

    private Date checkOutDate;

    private Date dueDate;

    private Book nextBook;

    private boolean checkedOut;

    /**
     * Default no args constructor that creates a "blank" Book
     */
    public Book(){
        name = author = genre = null;
        bookCondition = null;
        ISBN = -1;
        checkOutUserID = -1;
        yearPublished = -1;
        checkOutDate = null;
        nextBook = null;
        checkedOut = false;     //false by default
        dueDate = null;
    }
    
    /**
     * Constructor that creates a Book object with the given attributes
     * @param name title of book
     * @param author author of book
     * @param genre genre of book
     * @param bookCondition condition of book(NEW,GOOD,BAD,REPLACE)
     * @param ISBN ISBN of book
     * @param yearPublished year of book
     */
    public Book(String name, String author, String genre, Condition bookCondition, long ISBN, int yearPublished){
        this.bookCondition = bookCondition;
        this.name = name;
        this.author = author;
        this.genre = genre;
        
        this.ISBN = ISBN;
        this.yearPublished = yearPublished;
        checkedOut = false;     //false by default;
        checkOutDate = null;
        nextBook = null;
        checkOutUserID = -1;
        dueDate = null;
    }
    
    /**
     * Getters and Setters for various Book attributes
     */
    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }

    public Date getDueDate(){
        return dueDate;
    }
    public long getCheckOutUserID(){
        return checkOutUserID;
    }

    public void setCheckOutUserID(long newID){
        checkOutUserID = newID;
    }

    public Book getNextBook(){
        return nextBook;
    }

    public void setNextBook(Book next){
        nextBook = next;
    }

    public Date getCheckOutDate(){
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOut){
        checkOutDate = checkOut;
    }

    public boolean isCheckedOut(){
        return checkedOut;
    }

    public void setCheckedOut(boolean check){
        checkedOut = check;
    }
    
    public Condition getCondition(){
        return bookCondition;
    }

    public long getISBN(){
        return ISBN;
    }

    public String getName(){
        return name;
    }

    public String getAuthor(){
        return author;
    }

    public String getGenre(){
        return genre;
    }

    public int getYear(){
        return yearPublished;
    }

}
