/**
 * A class that represents a stack of Returned Bookk logs
 */
public class ReturnStack{

    /** The first returnLog */
    private ReturnLog topLog;

    /**
     * Default contructor creates a empty stack
     */
    public ReturnStack(){
        topLog = null;
    }

    /**
     * Pushes a returned book's ReturnLog onto the Stack
     * if a returned book is late, everything in stack is checked in
     * @param returnISBN the ISBN of the book to be returned
     * @param returnUserID  the userID returning the book
     * @param returnDate the date the book is being returned
     * @param bookRepoRef reference to the corresponding BookRepository
     * @throws InvalidISBNException if the ISBN is longer than 13 digits
     * @throws BookNotCheckedOutException if the Book isn't checked out
     * @throws InvalidReturnDateException if the Return Date is before the checkOutDate
     * @throws BookCheckedOutBySomeoneElseException if the Book is not checked out by the same userID it is being returned with
     * @throws InvalidUserIDException if the userID is not 10 digits
     */
    public boolean pushLog(long returnISBN, long returnUserID, Date returnDate, BookRepository bookRepoRef) throws InvalidISBNException, BookNotCheckedOutException, InvalidReturnDateException, BookCheckedOutBySomeoneElseException, InvalidUserIDException{
        
        if(String.valueOf(returnISBN).length() > 13)
            throw new InvalidISBNException("Invalid ISBN");
        
        if(String.valueOf(returnUserID).length() != 10)
            throw new InvalidUserIDException("Invalid User ID");
        
        Book ref;

        try{
            ref = bookRepoRef.getBook(returnISBN);
        }
        catch(BookDoesNotExistException e){
            throw new InvalidISBNException("Book Does Not Exist!");
        }

        if(ref.isCheckedOut() == false)
            throw new BookNotCheckedOutException("Book is not checked out!");

        if(ref.getCheckOutUserID() != returnUserID)
            throw new BookCheckedOutBySomeoneElseException("Book Checked Out By Different User ID");

        
        Date checkOut = ref.getCheckOutDate();
    
        //checks if the returnDate is before the checkOutDate
        //since you can't return before the checkOutDate
        int result = Date.compare(returnDate.getYear(), checkOut.getYear());
        if(result == 0){
            result = Date.compare(returnDate.getMonth(), checkOut.getMonth());
            if(result == 0){
                result = Date.compare(returnDate.getDay(), checkOut.getDay());
            }
        }
        if(result < 0){
            throw new InvalidReturnDateException("Invalid Return Date");
        }
        
        Date dueDate = ref.getDueDate();
        
        //checks if the returnDate is before or after the dueDate
        //to see if the book was returned late or on time
        int res= Date.compare(returnDate.getYear(), dueDate.getYear());
        if(res == 0){
            res = Date.compare(returnDate.getMonth(), dueDate.getMonth());
            if(res == 0){
                res = Date.compare(returnDate.getDay(), dueDate.getDay());
            }
        }
        //meaning the returnDate was before or on the dueDate and so on time
        if(res <= 0){

            if(topLog == null){
                ReturnLog r = new ReturnLog(returnISBN, returnUserID, returnDate);
                topLog = r;
            }
 
            else{
                ReturnLog r = new ReturnLog(returnISBN, returnUserID, returnDate);
                r.setNextLog(topLog);
                topLog = r;
            }
            System.out.println(ref.getName() + " has been returned on time!");
            return true;
        }

        //case of the returnDate is after the dueDate so its later
        //checking everything in, in the stack if a book is returned late, including the late returned book
        //empties the stack too since everything is now checked in
        else{
            bookRepoRef.checkInBook(ref.getISBN());
            System.out.println(ref.getName() + " has been returned LATE! Checking everything in...");
            if(topLog != null)
                System.out.println("The Books Below Will Now Also Be Checked In!");
            //System.out.println("The Books Below Will Now Also Be Checked In!");
            while(topLog!= null){
                try{
                    popLog(bookRepoRef);
                }
                catch(EmptyStackException e){

                }
            }
            return true;
        }        
    }

    /**
     * Pops the last returned book's ReturnLog from top of ReturnStack
     * @param bookRepoRef reference to corresponding BookRepository
     * @throws EmptyStackException if there are no returnLogs in returnStack
     */
    public ReturnLog popLog(BookRepository bookRepoRef) throws EmptyStackException{
        if(topLog == null){
            throw new EmptyStackException("Returns is Empty!");
        }
        
        ReturnLog temp = topLog;
        topLog = topLog.getNextLog();
        bookRepoRef.checkInBook(temp.getISBN());
        try{
            System.out.println(bookRepoRef.getBook(temp.getISBN()).getName() + " has been checked in!");
        }
        catch(BookDoesNotExistException e){
            System.out.println("This Book Doesn't Exist!");
        }
        
        return temp;
        
    }

    /**
     * Prints out the last return
     * @param bookRepoRef reference to corresponding BookRepository
     */
    public void peekLastReturn(BookRepository bookRepoRef){
        if(topLog != null){
            try{
                System.out.println(bookRepoRef.getBook(topLog.getISBN()).getName() + " is the next book to be checked in");
            }
            catch(BookDoesNotExistException e){
                
            }
        }
        else{
            System.out.println("Returns is Empty!");
        }
    }

    /**
     * Prints the ReturnStack's data in tabular form
     */
    public void print(){
        ReturnLog temp = topLog;
        String leadingISBN;
        System.out.printf("%s %27s %20s", "ISBN", "USER ID", "Return Date");
        System.out.println();
        while(temp != null){
            leadingISBN = String.format("%013d", temp.getISBN());
            System.out.printf("%s %20s %20s", leadingISBN, temp.getUserID() + "", temp.getReturnDate().toString());
            System.out.println();
            temp = temp.getNextLog();
        }
        System.out.println();
    }

}
