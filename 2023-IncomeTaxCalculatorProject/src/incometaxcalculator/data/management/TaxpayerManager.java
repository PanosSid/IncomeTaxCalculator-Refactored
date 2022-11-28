package incometaxcalculator.data.management;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import incometaxcalculator.data.io.FileReader;
import incometaxcalculator.data.io.FileReaderFactory;
import incometaxcalculator.data.io.FileWriter;
import incometaxcalculator.data.io.FileWriterFactory;

import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongFileReceiptSeperatorException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TaxpayerManager {
    private HashMap<Integer, Taxpayer> taxpayerHashMap = new HashMap<Integer, Taxpayer>(0);
    private HashMap<Integer, Integer> receiptOwnerTRN = new HashMap<Integer, Integer>(0);
    private TaxpayerFactory taxpayerFactory = new TaxpayerFactory();
    private FileWriterFactory fileWriterFactory = new FileWriterFactory(); 
    private FileReaderFactory fileReaderFactory = new FileReaderFactory();

    public void createTaxpayer(String fullname, int taxRegistrationNumber, String status, float income)
	    throws WrongTaxpayerStatusException {
	Taxpayer taxpayer = taxpayerFactory.createTaxpayer(fullname, taxRegistrationNumber, status, income);
	taxpayerHashMap.put(taxRegistrationNumber, taxpayer);
    }

    public void createReceipt(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegistrationNumber)
	    throws WrongReceiptKindException, WrongReceiptDateException {
	Company company = new Company(companyName, country, city, street, number);
	Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, company);
	Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);
	taxpayer.addReceipt(receipt);
	receiptOwnerTRN.put(receiptId, taxRegistrationNumber);
    }

    public void removeTaxpayer(int taxRegistrationNumber) {
	Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);
	taxpayerHashMap.remove(taxRegistrationNumber);
	HashMap<Integer, Receipt> receiptsHashMap = taxpayer.getReceiptHashMap();
	Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
	while (iterator.hasNext()) {
	    HashMap.Entry<Integer, Receipt> entry = iterator.next();
	    Receipt receipt = entry.getValue();
	    receiptOwnerTRN.remove(receipt.getId());
	}
    }

    public void addReceipt(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegistrationNumber)
	    throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {

	if (containsReceipt(receiptId)) {
	    throw new ReceiptAlreadyExistsException();
	}
	createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
		taxRegistrationNumber);
	updateFiles(taxRegistrationNumber);
    }

    public void removeReceipt(int receiptId) throws IOException, WrongReceiptKindException {
	taxpayerHashMap.get(receiptOwnerTRN.get(receiptId)).removeReceipt(receiptId);
	updateFiles(receiptOwnerTRN.get(receiptId));
	receiptOwnerTRN.remove(receiptId);
    }

    private void updateFiles(int taxRegistrationNumber) throws IOException {
	String fileFormats[] = {"txt", "xml"};
	for (int i = 0; i < fileFormats.length; i++) {
	    String filename = taxRegistrationNumber + "_INFO." + fileFormats[i];
	    File infoFile = new File(filename);
	    if (infoFile.exists()) {
		FileWriter infoWriter = fileWriterFactory.createInfoFileWriter(fileFormats[i], this);
		infoWriter.generateFile(taxRegistrationNumber);
	    }
	}
    }

    public void saveLogFile(int taxRegistrationNumber, String fileFormat) throws IOException, WrongFileFormatException {
	FileWriter fileWriter = fileWriterFactory.createLogFileWriter(fileFormat, this);
	fileWriter.generateFile(taxRegistrationNumber);
    }

    public boolean containsTaxpayer(int taxRegistrationNumber) {
	if (taxpayerHashMap.containsKey(taxRegistrationNumber)) {
	    return true;
	}
	return false;
    }

    // kaleitai mono sto gui opote logia tha diagrafei
    public boolean containsTaxpayer() {
	if (taxpayerHashMap.isEmpty()) {
	    return false;
	}
	return true;
    }

    public boolean containsReceipt(int id) {
	if (receiptOwnerTRN.containsKey(id)) {
	    return true;
	}
	return false;

    }

    public Taxpayer getTaxpayer(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber);
    }

    public void loadTaxpayer(String fileName)
	    throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException,
	    WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException, WrongFileReceiptSeperatorException {
	String ending[] = fileName.split("\\.");
	String fileType = ending[1];
	FileReader fileReader = fileReaderFactory.createFileReader(fileType, this);
	fileReader.readFile(fileName);
    }

    public String getTaxpayerName(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber).getFullname();
    }

    public String getTaxpayerStatus(int taxRegistrationNumber) {
	if (taxpayerHashMap.get(taxRegistrationNumber) instanceof MarriedFilingJointlyTaxpayer) {
	    return "Married Filing Jointly";
	} else if (taxpayerHashMap.get(taxRegistrationNumber) instanceof MarriedFilingSeparatelyTaxpayer) {
	    return "Married Filing Separately";
	} else if (taxpayerHashMap.get(taxRegistrationNumber) instanceof SingleTaxpayer) {
	    return "Single";
	} else {
	    return "Head of Household";
	}
    }

    public String getTaxpayerIncome(int taxRegistrationNumber) {
	return "" + taxpayerHashMap.get(taxRegistrationNumber).getIncome();
    }

    public double getTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber).getVariationTaxOnReceipts();
    }

    public int getTaxpayerTotalReceiptsGathered(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber).getTotalReceiptsGathered();
    }

    public float getTaxpayerAmountOfReceiptKind(int taxRegistrationNumber, short kind) {
	return taxpayerHashMap.get(taxRegistrationNumber).getAmountOfReceiptKind(kind);
    }

    public double getTaxpayerTotalTax(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber).getTotalTax();
    }

    public double getTaxpayerBasicTax(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber).getBasicTax();
    }

    public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber).getReceiptHashMap();
    }

}