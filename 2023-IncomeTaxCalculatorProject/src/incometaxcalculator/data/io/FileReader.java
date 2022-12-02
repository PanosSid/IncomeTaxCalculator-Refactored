package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.TaxpayerAlreadyLoadedException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongFileReceiptSeperatorException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public abstract class FileReader {
    
    private TaxpayerManager taxpayerManager;
    
    public FileReader() {
	this.taxpayerManager = TaxpayerManager.getInstance();
    }

    protected abstract String getValueOfField(String fieldsLine) throws WrongFileFormatException;
    
   // protected abstract boolean checkReceiptIdTag(String receiptSeperator);
    
    protected abstract boolean checkReceiptSeperatorTag(String receiptSeperator);
    
    protected abstract int getReceiptIdFromLine(String line);

    public void readFile(String fileName) throws NumberFormatException, IOException, WrongTaxpayerStatusException,
	    WrongFileFormatException, WrongReceiptKindException, WrongReceiptDateException, WrongFileReceiptSeperatorException,
	    TaxpayerAlreadyLoadedException {

	BufferedReader inputStream = new BufferedReader(new java.io.FileReader(fileName));
	String fullname = getValueOfField(inputStream.readLine());
	int taxRegistrationNumber = Integer.parseInt(getValueOfField(inputStream.readLine()));
	String status = getValueOfField(inputStream.readLine());
	float income = Float.parseFloat(getValueOfField(inputStream.readLine()));
	taxpayerManager.createTaxpayer(fullname, taxRegistrationNumber, status, income);
	
	String receiptsSeperator = getNextNotBlankLine(inputStream);
	if (checkReceiptSeperatorTag(receiptsSeperator)) {
	    while (readReceipt(inputStream, taxRegistrationNumber))
		    ;
	} else if (receiptsSeperator != null && !checkReceiptSeperatorTag(receiptsSeperator)) {
	    throw new WrongFileReceiptSeperatorException();
	}	
    }

    protected boolean readReceipt(BufferedReader inputStream, int taxRegistrationNumber)
	    throws WrongFileFormatException, IOException, WrongReceiptKindException, WrongReceiptDateException {
	String line = getNextNotBlankLine(inputStream);
	if (line == null) {	// eof 
	    return false;
	}
	int receiptId = getReceiptIdFromLine(line);
	
	if (receiptId < 0) {
	    return false;
	}
	
	String issueDate = getValueOfField(inputStream.readLine());
	String kind = getValueOfField(inputStream.readLine());
	float amount = Float.parseFloat(getValueOfField(inputStream.readLine()));
	String companyName = getValueOfField(inputStream.readLine());
	String country = getValueOfField(inputStream.readLine());
	String city = getValueOfField(inputStream.readLine());
	String street = getValueOfField(inputStream.readLine());
	int number = Integer.parseInt(getValueOfField(inputStream.readLine()));
	taxpayerManager.createReceipt(receiptId, issueDate, amount, kind, companyName,
			country, city, street, number, taxRegistrationNumber);
	return true;
    }
    
    
    
    protected String getNextNotBlankLine(BufferedReader inputStream) throws IOException {
	String line;
	while ((line = inputStream.readLine()) != null) {
	    String trimmedLine = line.trim();
	    if (!trimmedLine.equals("")) 
		break;
	}
	return line;
    }
    
    	
    protected boolean isEmpty(String line) {
	if (line == null) {
	    return true;
	} else {
	    return false;
	}
    }

}