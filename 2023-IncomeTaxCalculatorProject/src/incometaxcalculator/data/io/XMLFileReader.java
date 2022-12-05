package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileFormatException;

public class XMLFileReader extends TaxFileReader {

    public XMLFileReader() {
	super();
    }

    @Override
    public boolean checkReceiptSeperatorTag(String receiptSeperator) {
	if (receiptSeperator.equals("<Receipts>")) {
	    return true;
	}
	return false;
    }

    protected String getFieldFromLine(String fieldsLine) throws WrongFileFormatException {
	if (fieldsLine.isEmpty()) {
	    throw new WrongFileFormatException();
	}
	try {
	    return fieldsLine.replaceAll("\\<(.*?)\\>", "").trim();
	} catch (NullPointerException e) {
	    throw new WrongFileFormatException();
	}
    }

    @Override
    protected boolean hasAnotherReceipt(String line) {
	if (line.trim().equals("</Receipts>")) {
	    return false;
	}
	return true;
    }

}
