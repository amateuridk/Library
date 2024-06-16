/**
 * A class representing a Date
 */
public class Date {
    
    /** the month part of Date */
    private int month;
 
    /** the day part of Date */
    private int day;

    /** the year part of Date */
    private int year;

    /**
     * Default no args constructor creating a "blank" Date
     */
    public Date(){
        day = month = year = 0;
    }

    /**
     * Constructor to create Date with given attributes
     * @param month month(mm)
     * @param day day(dd)
     * @param year year(yyyy)
     */
    public Date(int month, int day, int year){
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Getters and Setters for Date attributes 
     */

    public void setMonth(int newMonth){
        month = newMonth;
    }

    public void setDay(int newDay){
        day = newDay;
    }

    public void setYear(int newYear){
        year = newYear;
    }

    public int getMonth(){
        return month;
    }

    public int getDay(){
        return day;
    }

    public int getYear(){
        return year;
    }

    /**
     * Compares 2 parameters
     * @param x comparison 1
     * @param y comparison 2
     * @return 0 if x == y, -1 if x < y, 1 if x > y
     */
    public static int compare(int x, int y){
        if(x == y)
            return 0;
        else if(x < y)
            return -1;
        else
            return 1;
    }

    /**
     * Returns a String representation of a Date object in the form(mm/dd/yyyy)
     */
    public String toString(){
        return getMonth() + "/" + getDay() + "/" + getYear();
    }

}
