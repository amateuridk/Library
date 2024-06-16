import java.util.Scanner;

/**
 * A class that allows user to interact with the Book Repository and Return Stack
 */
public class LibraryManager{

    private static BookRepository bookRepository;
    private static ReturnStack returnStack;

    /**
     * Allows the user to choose options to interact with either the Book Repository or Return Stack
     * @param args
     */
    public static void main(String[] args) {
        
        bookRepository = new BookRepository();
        returnStack = new ReturnStack();

        System.out.println("Starting...");

        while(true){
            
            printMenu();

            Scanner scan = new Scanner(System.in);

            System.out.println("Please select what to manage");
            String selection = scan.nextLine();

            switch(selection.toUpperCase()){
                
                case "B":
                    System.out.println("Please select an option:");
                    String s = scan.nextLine();
                    
                    switch(s.toUpperCase()){
                        
                        case "C":
                            System.out.println("Please provide a library User ID");
                            String userID = scan.nextLine();
                            System.out.println("Please provide an ISBN number");
                            String ISBN = scan.nextLine();
                            System.out.println("Please enter a check out date(today)(mm/dd/yyyy)");
                            String checkDate = scan.nextLine();
                            System.out.println("Please provide a return date(mm/dd/yyyy)");    
                            String dueDate = scan.nextLine();
                            
                            String[] dueDateSeperated = dueDate.split("/");
                            String[] checkDateSeperated = checkDate.split("/");
                            

                            try{
                                int month = Integer.parseInt(dueDateSeperated[0]);
                                int day = Integer.parseInt(dueDateSeperated[1]);
                                int year = Integer.parseInt(dueDateSeperated[2]);
                                Date due = new Date(month, day,year);

                                int monthCheck = Integer.parseInt(checkDateSeperated[0]);
                                int dayCheck = Integer.parseInt(checkDateSeperated[1]);
                                int yearCheck = Integer.parseInt(checkDateSeperated[2]);
                                Date check = new Date(monthCheck, dayCheck, yearCheck);

                                bookRepository.checkOutBook(Long.parseLong(ISBN), Long.parseLong(userID), due, check);
                                Book answer = bookRepository.getBook(Long.parseLong(ISBN));
                                System.out.println(answer.getName() + " has been checked out by " + answer.getCheckOutUserID() + " and must be returned by " + dueDate);
                            }
                            catch(BookAlreadyCheckedOutException e){
                                System.out.println(e.getMessage());
                                break;
                            }
                            catch(NumberFormatException n){
                                System.out.println("A data field was entered incorrectly!");
                                break;
                            }
                            catch(BookDoesNotExistException a){
                                System.out.println(a.getMessage());
                                break;
                            }
                            catch(ArrayIndexOutOfBoundsException a){
                                System.out.println("The Date was entered incorrectly!");
                                break;
                            }
                            catch(InvalidISBNException y){
                                System.out.println(y.getMessage());
                                break;
                            }
                            catch(InvalidUserIDException u){
                                System.out.println(u.getMessage());
                                break;
                            }
                    
                            break;
            
                        case "N":
                            System.out.println("Please provide an ISBN number");
                            String ISBN1 = scan.nextLine();
                            System.out.println("Please provide a Name");
                            String name1 = scan.nextLine();
                            System.out.println("Please provide an Author");
                            String author1 = scan.nextLine();
                            System.out.println("Please provide a Genre");
                            String genre1 = scan.nextLine();
                            System.out.println("Please provide a Condition");
                            String condition1 = scan.nextLine();
                            System.out.println("Please provide Year Published");
                            String yearPublished1 = scan.nextLine();

                            try{
                                bookRepository.addBook(Long.parseLong(ISBN1), name1, author1, genre1, Book.Condition.valueOf(condition1.toUpperCase()), Integer.parseInt(yearPublished1));
                            }
                            catch(BookAlreadyExistsException b){
                                System.out.println(b.getMessage());
                                break;
                            }
                            catch(NumberFormatException n){
                                System.out.println("A data field was entered incorrectly!");
                                break;
                            }
                            catch(IllegalArgumentException i){
                                System.out.println("The Condition data field was entered incorrectly");
                                break;
                            }
                            catch(InvalidISBNException e){
                                System.out.println(e.getMessage());
                                break;
                            }

                            System.out.println(name1 + " has been successfully added to the Book Repository!");
                            break;

                        case "R":
                            System.out.println("Please provide an ISBN number");
                            String ISBN2 = scan.nextLine();
                            String name;
                            try{
                                name = bookRepository.getBook(Long.parseLong(ISBN2)).getName();
                                bookRepository.removeBook(Long.parseLong(ISBN2));
                            }
                            catch(NumberFormatException e){
                                System.out.println("The ISBN is entered incorrectly!");
                                break;
                            }
                            catch(BookDoesNotExistException b){
                                System.out.println(b.getMessage());
                                break;
                            }
                            catch(InvalidISBNException i){
                                System.out.println(i.getMessage());
                                break;
                            }
                            
                            System.out.println(name + " has been successfully removed from Book Repository!");
                            break;

                        case "P":
                            System.out.println("Please select a shelf to print");
                            String shelfNumber = scan.nextLine();
                            bookRepository.print(Integer.parseInt(shelfNumber));
                            break;

                        case "S":
                            System.out.println("Please select a shelf to sort");
                            String shelfNum = scan.nextLine();
                            System.out.println("Please select a sorting criteria");
                            String sort = scan.nextLine();
                            String sortC;
                            if(sort.toUpperCase().equals("I"))
                                sortC = "ISBN";
                            else if(sort.toUpperCase().equals("N"))
                                sortC = "NAME";
                            else if(sort.toUpperCase().equals("A"))
                                sortC = "AUTHOR";
                            else if(sort.toUpperCase().equals("G"))
                                sortC = "GENRE";
                            else if(sort.toUpperCase().equals("Y"))
                                sortC = "YEAR";
                            else if(sort.toUpperCase().equals("C"))
                                sortC = "CONDITION";
                            else
                                sortC = "invalid";

                            try{
                                bookRepository.sortShelf(Integer.parseInt(shelfNum), sortC);
                                System.out.println("Shelf " + shelfNum + " has been sorted by " + sortC);
                            }
                            catch(InvalidSortCriteriaException e){
                                System.out.println(e.getMessage());
                                break;
                            }
                            break;
                        
                            default:
                            System.out.println("NOT AN OPTION");
                            break;
                    }
                    
                    break;

                case "R":
                    System.out.println("Please select an option:");
                    String str = scan.nextLine();

                    switch(str.toUpperCase()){

                        case "R":
                        System.out.println("Please provide the ISBN of the book you're returning");
                        String i = scan.nextLine();
                        System.out.println("Please provide your UserID");
                        String u = scan.nextLine();
                        System.out.println("Please enter current date(mm/dd/yyyy)");
                        String d = scan.nextLine();

                        try{
                            Long ISBN = Long.parseLong(i);
                            Long userID = Long.parseLong(u);
                           
                            String[] date = d.split("/");
                            int month = Integer.parseInt(date[0]);
                            int day = Integer.parseInt(date[1]);
                            int year = Integer.parseInt(date[2]);
                            Date returnDate = new Date(month,day,year);

                            returnStack.pushLog(ISBN, userID, returnDate, bookRepository);
                        }

                        catch(NumberFormatException n){
                            System.out.println("A data field was entered incorrectly!");
                            break;
                        }
                        catch(ArrayIndexOutOfBoundsException a){
                            System.out.println("Date was entered incorrectly");
                            break;
                        }
                        catch(InvalidISBNException er){
                            System.out.println(er.getMessage());
                            break;
                        }
                        catch(InvalidUserIDException err){
                            System.out.println(err.getMessage());
                            break;
                        }
                        catch(BookNotCheckedOutException b){
                            System.out.println(b.getMessage());
                            break;
                        }
                        catch(InvalidReturnDateException c){
                            System.out.println(c.getMessage());
                            break;

                        }
                        catch(BookCheckedOutBySomeoneElseException y){
                            System.out.println(y.getMessage());
                            break;
                        }
                        break;

                    case "L":                    
                        returnStack.peekLastReturn(bookRepository);
                        break;
                    
                    case "C":
                        try{
                            returnStack.popLog(bookRepository);
                        }
                        catch(EmptyStackException x){
                            System.out.println(x.getMessage());
                            break;
                        }
                        break;

                    case "P":
                        returnStack.print();
                    
                    break;

                    default:
                    System.out.println("That is not an option");
                    break;
                    
                    }

                    break;

                case "Q":
                    System.out.println("Program Terminated");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Not an Option!");
                    break;

            }
            
        }

    }

    /**
     * Prints out available menu options to user
     */
    public static void printMenu(){
        System.out.println();
        System.out.println("Menu:");
        System.out.println();
        System.out.println("   (B) - Manage Book Repository");
        System.out.println("        (C) - Checkout Book");
        System.out.println("        (N) - Add New Book");
        System.out.println("        (R) - Remove Book");
        System.out.println("        (P) - Print Repository");
        System.out.println("        (S) - Sort Shelf");
        System.out.println("            (I) - ISBN Number");
        System.out.println("            (N) - Name");
        System.out.println("            (A) - Author");
        System.out.println("            (G) - Genre");
        System.out.println("            (Y) - Year");
        System.out.println("            (C) - Condition");
        System.out.println();
        System.out.println("    (R) - Manage Return Stack");
        System.out.println("        (R) - Return Book");
        System.out.println("        (L) - See Last Return");
        System.out.println("        (C) - Check In Last Return");
        System.out.println("        (P) - Print Return Stack");
        System.out.println();
        System.out.println("    (Q) - Quit");
    }
}
