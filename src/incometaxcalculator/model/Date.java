package incometaxcalculator.model;

public class Date {

    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
	this.day = day;
	this.month = month;
	this.year = year;
    }

    public int getDay() {
	return day;
    }

    public int getMonth() {
	return month;
    }

    public int getYear() {
	return year;
    }

    public String toString() {
	return day + "/" + month + "/" + year;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Date other = (Date) obj;
	return day == other.day && month == other.month && year == other.year;
    }

}