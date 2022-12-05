package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileFormatException;

public class TXTFileReader extends TaxFileReader {

    public TXTFileReader() {
	super();
    }

    @Override
    protected String getFieldFromLine(String fieldsLine) throws WrongFileFormatException {
	if (fieldsLine.isEmpty()) {
	    throw new WrongFileFormatException();
	}
	try {
	    String values[] = fieldsLine.split(":", 2);
	    values[1] = values[1].trim();
	    return values[1];
	} catch (NullPointerException e) {
	    throw new WrongFileFormatException();
	}
    }

    @Override
    public boolean checkReceiptSeperatorTag(String receiptSeperator) {
	if (receiptSeperator.equals("Receipts:")) {
	    return true;
	}
	return false;
    }

    @Override
    protected boolean hasAnotherReceipt(String line) {
	if (line == null || line.isEmpty()) {
	    return false;
	}
	return true;
    }
}