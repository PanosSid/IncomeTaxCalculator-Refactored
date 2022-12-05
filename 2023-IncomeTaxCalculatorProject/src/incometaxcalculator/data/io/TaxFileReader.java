package incometaxcalculator.data.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongFileReceiptSeperatorException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;

public abstract class TaxFileReader {
    
    public TaxFileReader() {}

    protected abstract boolean checkReceiptSeperatorTag(String receiptSeperator);

    protected abstract boolean hasAnotherReceipt(String line);

    protected abstract String getFieldFromLine(String fieldsLine) throws WrongFileFormatException;

    public Map<String, List<String>> readTaxpayerAndReceipts(String fileName, int taxRegNum) throws Exception {
	Scanner inputStream = null;
	try {
	    inputStream = new Scanner(new FileInputStream(fileName));

	} catch (FileNotFoundException e) {
	    System.out.println("Problem opening file: " + fileName);
	    System.exit(0);
	}
	List<String> taxpayerInfoData = readTaxapyerInfo(inputStream, taxRegNum);
	Map<String, List<String>> taxpayerAndReceiptsData = readReceiptsOfTaxpayer(inputStream, taxRegNum);
	taxpayerAndReceiptsData.put("taxpayerInfo", taxpayerInfoData);
	return taxpayerAndReceiptsData;	
    }

    private List<String> readTaxapyerInfo(Scanner inputStream, int taxRegNum) throws Exception {
	String fullname = getFieldFromLine(inputStream.nextLine());
	String trn = getFieldFromLine(inputStream.nextLine());
	if (Integer.parseInt(trn) != taxRegNum) {
	    throw new Exception("Filename tax registration number is not equal to the one in the file");
	}
	String status = getFieldFromLine(inputStream.nextLine());
	String income = getFieldFromLine(inputStream.nextLine());
	inputStream.nextLine(); // skip a line
	return getDataAsAListOfStrings(fullname, trn, status, income);
    }

    private Map<String, List<String>> readReceiptsOfTaxpayer(Scanner inputStream, int taxRegNum)
	    throws WrongFileReceiptSeperatorException, WrongFileFormatException, WrongReceiptKindException, WrongReceiptDateException {
	String receiptSeperator = inputStream.nextLine();
	if (!checkReceiptSeperatorTag(receiptSeperator))
	    throw new WrongFileReceiptSeperatorException();
	inputStream.nextLine(); // skip a line
	
	HashMap<String, List<String>> receiptMap = new HashMap<String, List<String>>();
	while (inputStream.hasNextLine()) {
	    String line = inputStream.nextLine();
	    if (!hasAnotherReceipt(line))
		break;
	    String receiptId = getFieldFromLine(line);
	    String issueDate = getFieldFromLine(inputStream.nextLine());
	    String kind = getFieldFromLine(inputStream.nextLine());
	    String amount = getFieldFromLine(inputStream.nextLine());
	    String companyName = getFieldFromLine(inputStream.nextLine());
	    String country = getFieldFromLine(inputStream.nextLine());
	    String city = getFieldFromLine(inputStream.nextLine());
	    String street = getFieldFromLine(inputStream.nextLine());
	    String number = getFieldFromLine(inputStream.nextLine());
	    inputStream.nextLine(); // skip a line
	    receiptMap.put(receiptId, getDataAsAListOfStrings(receiptId, issueDate, kind, amount,
		    companyName, country, city, street, number));
	}
	return receiptMap;
    }
    
    private List<String> getDataAsAListOfStrings(String... data){
	List<String> taxpayerInfoData =  new ArrayList<String>();
	for (String d : data) {
	    taxpayerInfoData.add(d);
	}
	return taxpayerInfoData;
    }

}