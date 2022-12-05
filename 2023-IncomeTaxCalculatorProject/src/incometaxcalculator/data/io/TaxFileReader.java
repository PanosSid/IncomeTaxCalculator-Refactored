package incometaxcalculator.data.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongFileReceiptSeperatorException;
import incometaxcalculator.exceptions.WrongReceiptKindException;

public abstract class TaxFileReader {
    private TaxpayerManager taxpayerManager;

    public TaxFileReader() {
	this.taxpayerManager = TaxpayerManager.getInstance();
    }

    protected abstract boolean checkReceiptSeperatorTag(String receiptSeperator);

    protected abstract boolean hasAnotherReceipt(String line);

    protected abstract String getFieldFromLine(String fieldsLine) throws WrongFileFormatException;

    public void readTaxpayerAndReceipts(String fileName, int taxRegNum) throws Exception {
	Scanner inputStream = null;
	try {
	    inputStream = new Scanner(new FileInputStream(fileName));

	} catch (FileNotFoundException e) {
	    System.out.println("Problem opening file: " + fileName);
	    System.exit(0);
	}
	readTaxapyerInfo(inputStream, taxRegNum);
	readReceiptsOfTaxpayer(inputStream, taxRegNum);
    }

    private void readTaxapyerInfo(Scanner inputStream, int taxRegNum) throws Exception {
	String fullname = getFieldFromLine(inputStream.nextLine());
	int trn = Integer.parseInt(getFieldFromLine(inputStream.nextLine()));
	if (trn != taxRegNum) {
	    throw new Exception("Filename tax registration number is not equal to the one in the file");
	}
	String status = getFieldFromLine(inputStream.nextLine());
	float income = Float.parseFloat(getFieldFromLine(inputStream.nextLine()));
	inputStream.nextLine(); // skip a line
	taxpayerManager.createTaxpayer(fullname, taxRegNum, status, income);
    }

    private void readReceiptsOfTaxpayer(Scanner inputStream, int taxRegNum) throws WrongFileReceiptSeperatorException,
	    WrongFileFormatException, WrongReceiptKindException, WrongReceiptDateException {
	String receiptSeperator = inputStream.nextLine();
	if (!checkReceiptSeperatorTag(receiptSeperator))
	    throw new WrongFileReceiptSeperatorException();
	inputStream.nextLine(); // skip a line
	while (inputStream.hasNextLine()) {
	    String line = inputStream.nextLine();
	    if (!hasAnotherReceipt(line))
		break;
	    int receiptId = Integer.parseInt(getFieldFromLine(line));
	    String issueDate = getFieldFromLine(inputStream.nextLine());
	    String kind = getFieldFromLine(inputStream.nextLine());
	    float amount = Float.parseFloat(getFieldFromLine(inputStream.nextLine()));
	    String companyName = getFieldFromLine(inputStream.nextLine());
	    String country = getFieldFromLine(inputStream.nextLine());
	    String city = getFieldFromLine(inputStream.nextLine());
	    String street = getFieldFromLine(inputStream.nextLine());
	    int number = Integer.parseInt(getFieldFromLine(inputStream.nextLine()));
	    taxpayerManager.createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street,
		    number, taxRegNum);
	    inputStream.nextLine(); // skip a line
	}
    }

}