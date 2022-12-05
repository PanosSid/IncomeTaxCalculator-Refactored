package incometaxcalculator.exceptions;

public class WrongFileNameFormatException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public WrongFileNameFormatException() {
	super("File name and taxpayer tax registration number doesnt match");
	// TODO Auto-generated constructor stub
    }
    
}
