package incometaxcalculator.io.exceptions;

public class WrongFileReceiptSeperatorException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public WrongFileReceiptSeperatorException() {
	super("Wrong taxpayer info and receipts seperator.");
    }
}
