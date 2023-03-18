package incometaxcalculator.model.exceptions;

public class TaxpayerAlreadyLoadedException extends Exception {

	private static final long serialVersionUID = 6939510790282609245L;

	public TaxpayerAlreadyLoadedException() {
		super("Taxpayer already Loaded");
	}
}
