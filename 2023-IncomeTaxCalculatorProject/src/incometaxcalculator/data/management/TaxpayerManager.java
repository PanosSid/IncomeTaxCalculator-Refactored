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
import incometaxcalculator.data.io.TaxFileReader;
import incometaxcalculator.data.io.FileReaderFactory;
import incometaxcalculator.data.io.TaxFileWriter;
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
    private Map<Integer, String> filePathsOfTaxpayer;	// value = file path without file extention
    
    private TaxpayerManager() {
	taxpayerHashMap = new LinkedHashMap<Integer, Taxpayer>(0);
	fileWriterFactory = new FileWriterFactory();
	fileReaderFactory = new FileReaderFactory();
	filePathsOfTaxpayer = new HashMap<Integer, String>();
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
    
    public void createTaxpayer(String fullname, int taxRegNum, String status, float income)
	    throws WrongTaxpayerStatusException, TaxpayerAlreadyLoadedException {
	
	if (taxpayerHashMap.containsKey(taxRegNum)) {
	    throw new TaxpayerAlreadyLoadedException();
	} else {
	    TaxpayerCategory taxpayerCategory = appConfig.getTaxpayerCategoryByName(status);
	    Taxpayer taxpayer = new Taxpayer(fullname, taxRegNum, income, taxpayerCategory);
	    taxpayerHashMap.put(taxRegNum, taxpayer);  
	}
    }
    
    public void loadTaxpayer(String fileName)
	    throws Exception {
	String ending[] = fileName.split("\\.");
	String fileType = ending[1];
	String filePathWithoutFileFormat = ending[0];
	TaxFileReader fileReader = fileReaderFactory.createFileReader(fileType);
	int trn = getTaxRegNumFromFileNamePath(fileName);
	filePathsOfTaxpayer.put(trn, filePathWithoutFileFormat);
	Map<String, List<String>> infoFileContents = fileReader.readTaxpayerAndReceipts(fileName, trn);
	laodReadTaxpayer(trn, infoFileContents);
    }
    
    private int getTaxRegNumFromFileNamePath(String fileName) {
	String bySlash[] = fileName.split("\\\\");
	String ending = bySlash[bySlash.length-1];
	ending.substring(0, 9);	
	return Integer.parseInt(ending.substring(0, 9));
    }
    
    private void laodReadTaxpayer(int trn, Map<String, List<String>> infoFileContents) 
	    	throws TaxpayerAlreadyLoadedException, WrongReceiptDateException, WrongReceiptKindException {
	loadTaxpayerData(infoFileContents.get("taxpayerInfo"));
	infoFileContents.remove("taxpayerInfo");
	loadReceiptsData(trn, infoFileContents);
    }
    
    private void loadTaxpayerData(List<String> taxpayerInfoData) throws TaxpayerAlreadyLoadedException {
	String fullname = taxpayerInfoData.get(0);
	int taxRegNum = Integer.parseInt(taxpayerInfoData.get(1));
	String status = taxpayerInfoData.get(2);
	float income = Float.parseFloat(taxpayerInfoData.get(3));
	if (!taxpayerHashMap.containsKey(taxRegNum)) {
	    TaxpayerCategory taxpayerCategory = appConfig.getTaxpayerCategoryByName(status);
	    Taxpayer taxpayer = new Taxpayer(fullname, taxRegNum, income, taxpayerCategory);
	    taxpayerHashMap.put(taxRegNum, taxpayer);  
	} else {
	    throw new TaxpayerAlreadyLoadedException();
	}
    }
    
    private void loadReceiptsData(int taxRegNum, Map<String, List<String>> receiptsMap) 
	    throws WrongReceiptDateException, WrongReceiptKindException {
	for (String strId : receiptsMap.keySet()) {
	    List<String> receiptData = receiptsMap.get(strId);
	    int receiptId = Integer.parseInt(receiptData.get(0));
	    String issueDate = receiptData.get(1);
	    String kind = receiptData.get(2);
	    float amount = Float.parseFloat(receiptData.get(3));
	    String companyName = receiptData.get(4);
	    String country = receiptData.get(5);
	    String city = receiptData.get(6);
	    String street = receiptData.get(7);
	    int number = Integer.parseInt(receiptData.get(8));
	    Company company = new Company(companyName, country, city, street, number);
	    Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, company);
	    Taxpayer taxpayer = taxpayerHashMap.get(taxRegNum);
	    taxpayer.addReceipt(receipt);	    
	}
	
    }
    
    public void getInfoFilePathOfTaxpayer(int trn, String newFilePath) {
	filePathsOfTaxpayer.put(trn, newFilePath);
    }
    
    public void changeInfoFilePathOfTaxpayer(int trn, String newFilePath) {
	filePathsOfTaxpayer.put(trn, newFilePath);
    }

    public void removeTaxpayer(int taxRegNum){
	taxpayerHashMap.remove(taxRegNum);
	filePathsOfTaxpayer.remove(taxRegNum);
    }
    
    public void addReceipt(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegNum)
		    throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {
	
	if (containsReceipt(taxRegNum, receiptId)) {
	    throw new ReceiptAlreadyExistsException();
	}
	createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
		taxRegNum);
	updateFiles(taxRegNum);
    }

    public void createReceipt(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegNum)
	    throws WrongReceiptKindException, WrongReceiptDateException {
	Company company = new Company(companyName, country, city, street, number);
	Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, company);
	Taxpayer taxpayer = taxpayerHashMap.get(taxRegNum);
	taxpayer.addReceipt(receipt);
    }

    public void deleteReceiptFromTaxpayer(int receiptId, int trn) throws IOException {
	taxpayerHashMap.get(trn).removeReceipt(receiptId);
	updateFiles(trn);
    }
    
    public boolean containsReceipt(int taxRegNum, int receiptid) {
	return taxpayerHashMap.get(taxRegNum).hasReceiptId(receiptid);
    }

    private void updateFiles(int taxRegNum) throws IOException {
	String fileFormats[] = {"txt", "xml"};
	for (int i = 0; i < fileFormats.length; i++) {
	    String filename = filePathsOfTaxpayer.get(taxRegNum) + "." + fileFormats[i];
	    File infoFile = new File(filename);
	    if (infoFile.exists()) {
		String fileNamePath = filePathsOfTaxpayer.get(taxRegNum);
		TaxFileWriter infoWriter = fileWriterFactory.createInfoFileWriter(fileNamePath, fileFormats[i]);
		Map<Integer, List<String>> receiptsDataOfTaxpayer = taxpayerHashMap.get(taxRegNum).getReceiptsDataOfTaxpayer();
		List<String> taxpayerInfoData  = taxpayerHashMap.get(taxRegNum).getTaxpayerInfoData();
		infoWriter.updateInfoFile(taxpayerInfoData, receiptsDataOfTaxpayer);
	    }
	}
    }

    public void saveLogFile(int taxRegNum, String filePath, String fileFormat) throws IOException, WrongFileFormatException {
	filePath = filePath + "\\"+taxRegNum+"_LOG";
	System.out.println(filePath);
	boolean taxIncrease;
	if (getTaxpayerVariationTaxOnReceipts(taxRegNum) > 0) {
	    taxIncrease = true;
	} else {
	    taxIncrease = false;
	}
	TaxFileWriter logWriter = fileWriterFactory.createLogFileWriter(filePath, fileFormat, taxIncrease, getLogData(taxRegNum));
	logWriter.generateFile(taxRegNum);
    }
    
    private List<String> getLogData(int taxRegistrationNumber) {
	List<String> logData = new ArrayList<String>();
	logData.add("" + getTaxpayerName(taxRegistrationNumber));
	logData.add("" + taxRegistrationNumber);
	logData.add("" + getTaxpayerIncome(taxRegistrationNumber));
	logData.add("" + getTaxpayerBasicTax(taxRegistrationNumber));	
	logData.add("" + getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber));
	logData.add("" + getTaxpayerTotalTax(taxRegistrationNumber));
	logData.add("" + getTaxpayerTotalReceiptsGathered(taxRegistrationNumber));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Entertainment"));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Basic"));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Travel"));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Health"));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Other"));
	return logData;
	
    }

    public Taxpayer getTaxpayer(int taxRegNum) {
	return taxpayerHashMap.get(taxRegNum);
    }

    public String getTaxpayerName(int taxRegNum) {
	return taxpayerHashMap.get(taxRegNum).getFullname();
    }
    
    public String getTaxpayerCategoryName(int taxRegNum) {
	return  taxpayerHashMap.get(taxRegNum).getTaxpayerCategoryName();
    }

    public String getTaxpayerIncome(int taxRegNum) {
	return "" + taxpayerHashMap.get(taxRegNum).getIncome();
    }

    public double getTaxpayerVariationTaxOnReceipts(int taxRegNum) {	// LOG + REPORTS
	return taxpayerHashMap.get(taxRegNum).getVariationTaxOnReceipts();
    }

    public int getTaxpayerTotalReceiptsGathered(int taxRegNum) {	//LOG
	return taxpayerHashMap.get(taxRegNum).getTotalReceiptsGathered();
    }

    public float getTaxpayerAmountOfReceiptKind(int taxRegNum, String kind) { // LOG + REPORTS
	return taxpayerHashMap.get(taxRegNum).getAmountOfReceiptKind(kind);
    }

    public double getTaxpayerTotalTax(int taxRegNum) {	// LOG + REPORTS
	return taxpayerHashMap.get(taxRegNum).getTotalTax();
    }

    public double getTaxpayerBasicTax(int taxRegNum) {	// LOG + REPORTS
	return taxpayerHashMap.get(taxRegNum).getBasicTax();
    }

    public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegNum) {
	return taxpayerHashMap.get(taxRegNum).getReceiptHashMap();
    }
    
    public List<Receipt> getReceiptListOfTaxpayer(int taxRegNum) {
	Taxpayer taxpayer = taxpayerHashMap.get(taxRegNum);
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