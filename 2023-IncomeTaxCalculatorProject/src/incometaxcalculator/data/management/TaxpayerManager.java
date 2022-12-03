package incometaxcalculator.data.management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import incometaxcalculator.data.config.AppConfig;
import incometaxcalculator.data.io.FileReader;
import incometaxcalculator.data.io.FileReaderFactory;
import incometaxcalculator.data.io.FileWriter;
import incometaxcalculator.data.io.FileWriterFactory;

import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.TaxpayerAlreadyLoadedException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongFileReceiptSeperatorException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TaxpayerManager {
    private static TaxpayerManager instance ;
    private HashMap<Integer, Taxpayer> taxpayerHashMap;
    private FileWriterFactory fileWriterFactory; 
    private FileReaderFactory fileReaderFactory;
    public AppConfig appConfig ;
    
    
    private TaxpayerManager() {
	taxpayerHashMap = new LinkedHashMap<Integer, Taxpayer>(0);
	fileWriterFactory = new FileWriterFactory();
	fileReaderFactory = new FileReaderFactory();
	appConfig = new AppConfig();
    }
   
    public static synchronized TaxpayerManager getInstance() {
	 if (instance == null) {
	     instance = new TaxpayerManager();
	 }
	 return instance;
    }
        
    public static void tearDownTaxpayerManager() {
	instance = null;
    }
    
    public void createTaxpayer(String fullname, int taxRegistrationNumber, String status, float income)
	    throws WrongTaxpayerStatusException, TaxpayerAlreadyLoadedException {
	
	if (taxpayerHashMap.containsKey(taxRegistrationNumber)) {
	    throw new TaxpayerAlreadyLoadedException();
	} else {
	    TaxpayerCategory taxpayerCategory = appConfig.getTaxpayerCategoryByName(status);
	    Taxpayer taxpayer = new Taxpayer(fullname, taxRegistrationNumber, income, taxpayerCategory);
	    taxpayerHashMap.put(taxRegistrationNumber, taxpayer);  
	}
    }
    
    public void loadTaxpayer(String fileName)
	    throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException,
	    WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException, WrongFileReceiptSeperatorException,
	    TaxpayerAlreadyLoadedException {
	String ending[] = fileName.split("\\.");
	String fileType = ending[1];
	FileReader fileReader = fileReaderFactory.createFileReader(fileType);
	fileReader.readFile(fileName);
    }
    
    public void removeTaxpayer(int taxRegistrationNumber){
	taxpayerHashMap.remove(taxRegistrationNumber);
    }
    
    public void addReceipt(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegistrationNumber)
		    throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {
	
	if (containsReceipt(taxRegistrationNumber, receiptId)) {
	    throw new ReceiptAlreadyExistsException();
	}
	createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
		taxRegistrationNumber);
	updateFiles(taxRegistrationNumber);
    }

    public void createReceipt(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegistrationNumber)
	    throws WrongReceiptKindException, WrongReceiptDateException {
	Company company = new Company(companyName, country, city, street, number);
	Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, company);
	Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);
	taxpayer.addReceipt(receipt);
    }

    public void deleteReceiptFromTaxpayer(int receiptId, int trn) throws IOException {
	taxpayerHashMap.get(trn).removeReceipt(receiptId);
	updateFiles(trn);
    }
    
    public boolean containsReceipt(int taxRegistrationNumber, int receiptid) {
	return taxpayerHashMap.get(taxRegistrationNumber).hasReceiptId(receiptid);
    }

    private void updateFiles(int taxRegistrationNumber) throws IOException {
	String fileFormats[] = {"txt", "xml"};
	for (int i = 0; i < fileFormats.length; i++) {
	    String filename = taxRegistrationNumber + "_INFO." + fileFormats[i];
	    File infoFile = new File(filename);
	    if (infoFile.exists()) {
		FileWriter infoWriter = fileWriterFactory.createInfoFileWriter(fileFormats[i]);
		infoWriter.generateFile(taxRegistrationNumber);
	    }
	}
    }

    public void saveLogFile(int taxRegistrationNumber, String fileFormat) throws IOException, WrongFileFormatException {
	FileWriter fileWriter = fileWriterFactory.createLogFileWriter(fileFormat);
	fileWriter.generateFile(taxRegistrationNumber);
    }

    public Taxpayer getTaxpayer(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber);
    }

    public String getTaxpayerName(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber).getFullname();
    }
    
    public String getTaxpayerCategoryName(int taxRegistrationNumber) {
	return  taxpayerHashMap.get(taxRegistrationNumber).getTaxpayerCategoryName();
    }

    public String getTaxpayerIncome(int taxRegistrationNumber) {
	return "" + taxpayerHashMap.get(taxRegistrationNumber).getIncome();
    }

    public double getTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber) {	// LOG + REPORTS
	return taxpayerHashMap.get(taxRegistrationNumber).getVariationTaxOnReceipts();
    }

    public int getTaxpayerTotalReceiptsGathered(int taxRegistrationNumber) {	//LOG
	return taxpayerHashMap.get(taxRegistrationNumber).getTotalReceiptsGathered();
    }

    public float getTaxpayerAmountOfReceiptKind(int taxRegistrationNumber, String kind) { // LOG + REPORTS
	return taxpayerHashMap.get(taxRegistrationNumber).getAmountOfReceiptKind(kind);
    }

    public double getTaxpayerTotalTax(int taxRegistrationNumber) {	// LOG + REPORTS
	return taxpayerHashMap.get(taxRegistrationNumber).getTotalTax();
    }

    public double getTaxpayerBasicTax(int taxRegistrationNumber) {	// LOG + REPORTS
	return taxpayerHashMap.get(taxRegistrationNumber).getBasicTax();
    }

    public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber) {
	return taxpayerHashMap.get(taxRegistrationNumber).getReceiptHashMap();
    }
    
    public List<Receipt> getReceiptListOfTaxpayer(int taxRegistrationNumber) {
	Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);
	Map<Integer, Receipt> receiptMap = taxpayer.getReceiptHashMap();
	return new ArrayList<Receipt>(receiptMap.values());
    }

    public String[][] getNameAndTrnOfLoadedTaxpayers() {
	int numberOfloadedTaxpayers = taxpayerHashMap.size();
	String namesAndTrn[][] = new String[numberOfloadedTaxpayers][2];
	int i = 0;
	for (Integer trn : taxpayerHashMap.keySet()) {
	    String name = taxpayerHashMap.get(trn).getFullname();
	    namesAndTrn[i][0] = name;
	    namesAndTrn[i][1] = ""+trn;
	    i++;
	}
	return namesAndTrn;
    }
    
    public String[] getLastLoadedTaxpayerNameAndTrn() {
	int numberOfloadedTaxpayers = taxpayerHashMap.size();
	List<Integer> lKeys = new ArrayList<Integer>(taxpayerHashMap.keySet());
	Integer lastTrn = lKeys.get(numberOfloadedTaxpayers -1);
	String lastName = taxpayerHashMap.get(lastTrn).getFullname();
	String[] nameAndTrn = {lastName, ""+lastTrn};
	return nameAndTrn;
    }

}