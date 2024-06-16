public class BookAlreadyCheckedOutException extends Exception{

    public BookAlreadyCheckedOutException(String message){
        super(message);
    }
}