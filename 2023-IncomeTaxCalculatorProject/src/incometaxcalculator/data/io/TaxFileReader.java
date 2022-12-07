package incometaxcalculator.data.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.FileHasWrongTagsException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongFileReceiptSeperatorException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.tags.Tag;

public class TaxFileReader {
    protected List<Tag> infoTags;
    protected List<Tag> receiptsTags;

    public TaxFileReader(List<Tag> infoTags, List<Tag> receiptsTags) {
	this.infoTags = infoTags;
	this.receiptsTags = receiptsTags;
    }
    
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
	String fullname = infoTags.get(0).removeTagsFromLine(inputStream.nextLine());
	String trn = infoTags.get(1).removeTagsFromLine(inputStream.nextLine());
	if (Integer.parseInt(trn) != taxRegNum) {
	    throw new Exception("Filename tax registration number is not equal to the one in the file");
	}
	String status = infoTags.get(2).removeTagsFromLine(inputStream.nextLine());
	String income = infoTags.get(3).removeTagsFromLine(inputStream.nextLine());
	inputStream.nextLine(); // skip a line
	return getDataAsAListOfStrings(fullname, trn, status, income);
    }

    private Map<String, List<String>> readReceiptsOfTaxpayer(Scanner inputStream, int taxRegNum)
	    throws WrongFileReceiptSeperatorException, WrongFileFormatException, WrongReceiptKindException,
	    WrongReceiptDateException, FileHasWrongTagsException {
	if (!receiptsTags.get(0).doesLineContainHeaderTag(inputStream.nextLine()))	
	    throw new WrongFileReceiptSeperatorException();
	inputStream.nextLine(); // skip a line
	HashMap<String, List<String>> receiptMap = new HashMap<String, List<String>>();
	String line = null;
	while (inputStream.hasNextLine()) {
	    line = inputStream.nextLine();
	    if (line.equals(receiptsTags.get(0).getFooterTag()))
		break;
	    String receiptId = receiptsTags.get(1).removeTagsFromLine(line);
	    String issueDate = receiptsTags.get(2).removeTagsFromLine(inputStream.nextLine());
	    String kind = receiptsTags.get(3).removeTagsFromLine(inputStream.nextLine());
	    String amount = receiptsTags.get(4).removeTagsFromLine(inputStream.nextLine());
	    String companyName = receiptsTags.get(5).removeTagsFromLine(inputStream.nextLine());
	    String country = receiptsTags.get(6).removeTagsFromLine(inputStream.nextLine());
	    String city = receiptsTags.get(7).removeTagsFromLine(inputStream.nextLine());
	    String street = receiptsTags.get(8).removeTagsFromLine(inputStream.nextLine());
	    String number = receiptsTags.get(9).removeTagsFromLine(inputStream.nextLine());
	    inputStream.nextLine(); // skip a line
	    receiptMap.put(receiptId, getDataAsAListOfStrings(receiptId, issueDate, kind, amount, companyName, country,
		    city, street, number));
	}
	if (!receiptsTags.get(0).doesLineContainFooterTag(line))
	    throw new WrongFileReceiptSeperatorException();
	return receiptMap;
    }

    private List<String> getDataAsAListOfStrings(String... data) {
	List<String> dataList = new ArrayList<String>();
	for (String d : data) {
	    dataList.add(d);
	}
	return dataList;
    }

}