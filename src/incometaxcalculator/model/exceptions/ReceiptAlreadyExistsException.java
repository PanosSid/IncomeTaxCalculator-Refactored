package incometaxcalculator.model.exceptions;

public class ReceiptAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 6939510790282609245L;

    public ReceiptAlreadyExistsException() {
	super("Receipt already exists.");
    }
}
