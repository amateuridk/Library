/**
 * A class that represents the return log of a Book
 */
public class ReturnLog{

    /** the ISBN of the returned book */
    private long ISBN;

    /** the userID of who is returning book */
    private long userID;

    /** the Date the Book is returned */
    private Date returnDate;

    /** the next Book in the log */
    private ReturnLog nextLog;

    /**
     * Default constructor creates a "blank" log
     */
    public ReturnLog(){
        nextLog = null;
        ISBN = -1;
        userID = -1;
        returnDate = null;
    }

    /**
     * Creates a return long with given attributes
     * @param ISBN the ISBN of the book being returned
     * @param userID the returner's user ID
     * @param returnDate the return Date 
     */
    public ReturnLog(long ISBN, long userID, Date returnDate){
        this.ISBN = ISBN;
        this.userID = userID;
        this.returnDate = returnDate;
        nextLog = null;
    }

    /** Getters and Setters for attributes */

    public void setISBN(long ISBN){
        this.ISBN = ISBN;
    }

    public long getISBN(){
        return ISBN;
    }

    public void setUserID(long userID){
        this.userID = userID;
    }

    public long getUserID(){
        return userID;
    }

    public void setReturnDate(Date returnDate){
        this.returnDate = returnDate;
    }

    public Date getReturnDate(){
        return returnDate;
    }

    public void setNextLog(ReturnLog nextLog){
        this.nextLog = nextLog;
    }

    public ReturnLog getNextLog(){
        return nextLog;
    }

}