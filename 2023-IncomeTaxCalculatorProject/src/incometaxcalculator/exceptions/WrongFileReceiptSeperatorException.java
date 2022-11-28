package incometaxcalculator.exceptions;

public class WrongFileReceiptSeperatorException extends Exception {
    public WrongFileReceiptSeperatorException() {
	super("Wrong taxpayer info and receipts seperator.");
    }
}
