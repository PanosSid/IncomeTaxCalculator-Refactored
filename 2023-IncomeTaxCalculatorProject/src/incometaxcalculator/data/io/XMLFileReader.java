package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileFormatException;

public class XMLFileReader extends FileReader {
    
    public XMLFileReader(TaxpayerManager taxpayerManager) {
	super(taxpayerManager);
    }

    @Override
    public boolean checkReceiptSeperator(String receiptSeperator) {
	if (receiptSeperator.equals("<Receipts>")) {
	    return true;
	}
	return false;
    }

    @Override
    public int getReceiptIdFromLine(String line) {
	String lineFields[] = line.split("\\s+");
	if (lineFields[0].equals("<ReceiptID>") &&
			lineFields[2].equals("</ReceiptID>")) {
	    int receiptId = Integer.parseInt(lineFields[1].trim());
	    return receiptId;
	}
	return -1;
    }
//    protected int checkForReceipt(BufferedReader inputStream) throws NumberFormatException, IOException {
//	String line;
//	while (!isEmpty(line = inputStream.readLine())) {
//	    String values[] = line.split(" ", 3);
//	    if (values[0].equals("<ReceiptID>")) {
//		int receiptId = Integer.parseInt(values[1].trim());
//		return receiptId;
//	    }
//	}
//	return -1;
//    }

    protected String getValueOfField(String fieldsLine) throws WrongFileFormatException {
	if (isEmpty(fieldsLine)) {
	    throw new WrongFileFormatException();
	}
	try {
	    String valueWithTail[] = fieldsLine.split(" ", 2);
	    String valueReversed[] = new StringBuilder(valueWithTail[1]).reverse().toString().trim().split(" ", 2);
	    return new StringBuilder(valueReversed[1]).reverse().toString();
	} catch (NullPointerException e) {
	    throw new WrongFileFormatException();
	}
    }

   

}
