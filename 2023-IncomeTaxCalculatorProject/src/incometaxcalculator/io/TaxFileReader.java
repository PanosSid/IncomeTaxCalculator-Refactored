package incometaxcalculator.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import incometaxcalculator.io.exceptions.FileHasWrongTagsException;
import incometaxcalculator.io.exceptions.WrongFileFormatException;
import incometaxcalculator.io.exceptions.WrongFileReceiptSeperatorException;
import incometaxcalculator.io.exceptions.WrongReceiptDateException;
import incometaxcalculator.io.exceptions.WrongReceiptKindException;

public class TaxFileReader {
    public List<String> infoHeaders;
    public List<String> infoFooters;
    public List<String> receiptHeaders;
    public List<String> receiptFooters;

    public TaxFileReader(List<String> infoHeaders, List<String> infoFooters, List<String> receiptHeaders,
	    List<String> receiptFooters) {
	super();
	this.infoHeaders = infoHeaders;
	this.infoFooters = infoFooters;
	this.receiptHeaders = receiptHeaders;
	this.receiptFooters = receiptFooters;
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
	List<String> infoHeaderTags = infoHeaders;
	List<String> infoFooterTags = infoFooters;

	String fullname = removeTagsFromLine(inputStream.nextLine(), infoHeaderTags.get(0), infoFooterTags.get(0));
	String trn = removeTagsFromLine(inputStream.nextLine(), infoHeaderTags.get(1), infoFooterTags.get(1));
	if (Integer.parseInt(trn) != taxRegNum) {
	    throw new Exception("Filename tax registration number is not equal to the one in the file");
	}
	String status = removeTagsFromLine(inputStream.nextLine(), infoHeaderTags.get(2), infoFooterTags.get(2));
	String income = removeTagsFromLine(inputStream.nextLine(), infoHeaderTags.get(3), infoFooterTags.get(3));
	inputStream.nextLine(); // skip a line
	return getDataAsAListOfStrings(fullname, trn, status, income);
    }

    private Map<String, List<String>> readReceiptsOfTaxpayer(Scanner inputStream, int taxRegNum)
	    throws WrongFileReceiptSeperatorException, WrongFileFormatException, WrongReceiptKindException,
	    WrongReceiptDateException, FileHasWrongTagsException {
	if (!doesLineContainHeaderTag(inputStream.nextLine(), receiptHeaders.get(0)))
	    throw new WrongFileReceiptSeperatorException();
	inputStream.nextLine(); // skip a line
	HashMap<String, List<String>> receiptMap = new HashMap<String, List<String>>();
	String line = null;
	while (inputStream.hasNextLine()) {
	    line = inputStream.nextLine();
	    if (line.equals(receiptFooters.get(0)))
		break;
	    List<String> receiptData = new ArrayList<String>();
	    String receiptId = removeTagsFromLine(line, receiptHeaders.get(1), receiptFooters.get(1));
	    receiptData.add(receiptId);
	    for (int i = 2; i < 10; i++) {
		receiptData.add(removeTagsFromLine(inputStream.nextLine(),
			receiptHeaders.get(i), receiptFooters.get(i)));
	    }
	    receiptMap.put(receiptId, receiptData);
	    inputStream.nextLine(); // skip a line
	}

	if (!doesLineContainFooterTag(line, receiptFooters.get(0)))
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

    public String removeTagsFromLine(String line, String header, String footer)
	    throws FileHasWrongTagsException {
	if (!doesLineContainTags(line, header, footer)) {
	    throw new FileHasWrongTagsException();
	}
	return line.replaceAll(header, "").replaceAll(footer, "").trim();
    }

    public boolean doesLineContainTags(String line, String header, String footer) {
	return line.startsWith(header) && line.endsWith(footer);
    }

    public boolean doesLineContainHeaderTag(String line, String header) {
	return line.startsWith(header);
    }

    public boolean doesLineContainFooterTag(String line, String footer) {
	return line.endsWith(footer);
    }

}