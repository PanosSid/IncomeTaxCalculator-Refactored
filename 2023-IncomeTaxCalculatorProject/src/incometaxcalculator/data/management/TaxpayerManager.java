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
    
    private int getTaxRegNumFromFileNamePath(String fileName) {
	String bySlash[] = fileName.split("\\\\");
	String ending = bySlash[bySlash.length-1];
	ending.substring(0, 9);	
	return Integer.parseInt(ending.substring(0, 9));
    }
    
//    private String getPathWithoutFileFormat(String filePath) {
//	
//	return 
//    }
    
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
	    String filename = filePathsOfTaxpayer.get(taxRegistrationNumber) + "." + fileFormats[i];
//	    String filename = "\\resources\\INFO files\\"+taxRegistrationNumber + "_INFO." + fileFormats[i];
	    File infoFile = new File(filename);
	    if (infoFile.exists()) {
//		infoWriter.generateFile(taxRegistrationNumber);
//		TaxFileWriter infoWriter = fileWriterFactory.createInfoFileWriter(fileFormats[i]);
		String fileNamePath = filePathsOfTaxpayer.get(taxRegistrationNumber);
		TaxFileWriter infoWriter = fileWriterFactory.createInfoFileWriter(fileNamePath, fileFormats[i]);
		Map<Integer, List<String>> receiptsDataOfTaxpayer = taxpayerHashMap.get(taxRegistrationNumber).getReceiptsDataOfTaxpayer();
		List<String> taxpayerInfoData  = taxpayerHashMap.get(taxRegistrationNumber).getTaxpayerInfoData();
		infoWriter.updateInfoFile(taxpayerInfoData, receiptsDataOfTaxpayer);
	    }
	}
    }

    public void saveLogFile(int taxRegNum, String filePath, String fileFormat) throws IOException, WrongFileFormatException {
//	String fileNamePath = filePathsOfTaxpayer.get(taxRegNum).replaceFirst("_INFO", "_LOG");
	filePath = filePath + "\\"+taxRegNum+"_LOG";
	System.out.println(filePath);
	TaxFileWriter logWriter = fileWriterFactory.createLogFileWriter(filePath, fileFormat);
//	TaxFileWriter fileWriter = fileWriterFactory.createLogFileWriter(fileFormat);
	logWriter.generateFile(taxRegNum);
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