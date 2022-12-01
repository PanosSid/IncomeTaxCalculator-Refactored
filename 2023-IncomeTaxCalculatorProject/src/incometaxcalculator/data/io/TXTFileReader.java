package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileFormatException;

public class TXTFileReader extends FileReader {
    
    public TXTFileReader(TaxpayerManager taxpayerManager) {
	super(taxpayerManager);
	
    }
    
    @Override
    public boolean checkReceiptSeperatorTag(String receiptSeperator) {
	if (receiptSeperator.equals("Receipts:")) {
	    return true;
	}
	return false;
    }

    @Override
    public int getReceiptIdFromLine(String line) {
	String lineFields[] = line.split(":");
	if (lineFields[0].equals("Receipt ID")) {
	    int receiptId = Integer.parseInt(lineFields[1].trim());
	    return receiptId;
	}
	return -1;
    }
    
    protected String getValueOfField(String fieldsLine) throws WrongFileFormatException {
	if (isEmpty(fieldsLine)) {
	    throw new WrongFileFormatException();
	}
	try {
	    String values[] = fieldsLine.split(" ", 2);
	    values[1] = values[1].trim();
	    return values[1];
	} catch (NullPointerException e) {
	    throw new WrongFileFormatException();
	}
    }


}