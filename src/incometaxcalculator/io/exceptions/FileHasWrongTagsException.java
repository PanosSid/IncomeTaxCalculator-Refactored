package incometaxcalculator.io.exceptions;

public class FileHasWrongTagsException extends Exception {
	private static final long serialVersionUID = 6939510790282609245L;

	public FileHasWrongTagsException() {
		super("Info file has wrong tags!");
	}
}
