package incometaxcalculator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import incometaxcalculator.io.TaxFileManager;
import incometaxcalculator.io.exceptions.WrongFileFormatException;
import incometaxcalculator.io.exceptions.WrongReceiptDateException;
import incometaxcalculator.io.exceptions.WrongReceiptKindException;
import incometaxcalculator.model.Taxpayer;
import incometaxcalculator.model.TaxpayerManager;
import incometaxcalculator.model.exceptions.ReceiptAlreadyExistsException;


public class MainManager implements IncomeTaxManager {
    private static MainManager instance; 
    private TaxpayerManager taxpayerManager;
    private TaxFileManager fileManager;

    private MainManager() {	
	taxpayerManager = new TaxpayerManager();
	fileManager = new TaxFileManager();
    }
    
    public static synchronized MainManager getInstance() {
 	if (instance == null) {
 	    instance = new MainManager();
 	}
 	return instance;
     }
    
    public TaxpayerManager getTaxpayerManger() {
	return taxpayerManager;
    }
    
    public TaxFileManager getTaxFileManager() {
	return fileManager;
    }

    @Override
    public void loadTaxpayer(String fileName) throws Exception {
	Map<String, List<String>> infoFileContents = fileManager.readTaxpayerFromFile(fileName);
	taxpayerManager.laodReadTaxpayer(fileManager.getTaxRegNumFromFileNamePath(fileName), infoFileContents);
    }

    @Override
    public void removeTaxpayer(int taxRegNum) {
	taxpayerManager.removeTaxpayer(taxRegNum);
	fileManager.removeTaxpayerFilePath(taxRegNum);
    }

    @Override
    public void addReceiptToTaxpayer(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegNum)
	    throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {
	taxpayerManager.addReceiptToTaxapyer2(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
		taxRegNum);
	List<String> taxpayerInfoData = taxpayerManager.getTaxpayerInfoData(taxRegNum);
	Map<Integer, List<String>> receiptsDataOfTaxpayer = taxpayerManager.getReceiptsDataOfTaxpayer(taxRegNum);
	fileManager.updateTaxpayerFiles(taxRegNum, taxpayerInfoData, receiptsDataOfTaxpayer);
    }

    @Override
    public void deleteReceiptFromTaxpayer(int receiptId, int taxRegNum) throws IOException {
	taxpayerManager.deleteReceiptFromTaxpayer(receiptId, taxRegNum);
	List<String> taxpayerInfoData = taxpayerManager.getTaxpayerInfoData(taxRegNum);
	Map<Integer, List<String>> receiptsDataOfTaxpayer = taxpayerManager.getReceiptsDataOfTaxpayer(taxRegNum);
	fileManager.updateTaxpayerFiles(taxRegNum, taxpayerInfoData, receiptsDataOfTaxpayer);
    }

    @Override
    public void saveLogFile(int taxRegNum, String filePath, String fileFormat)
	    throws IOException, WrongFileFormatException {
	List<String> logData = taxpayerManager.getTaxpayer(taxRegNum).getDataForLog();
	boolean taxIncrease = taxpayerManager.isTaxIncreasingForTaxpayer(taxRegNum);
	String fileNamePath = filePath + "\\" + taxRegNum + "_LOG.";
	fileManager.saveLogeFile(fileNamePath, fileFormat, logData, taxIncrease);
    }
    
    
//    public void loadAppSettings() {
//	fileManager.loadTaxpayerAndReceiptParams();
////	Map<String, TaxpayerCategory> categoriesMap = fileManager.getTaxpayerCategoriesFromFile();
////	taxpayerManager.setTaxpayerCategories(categoriesMap);
//    }
    
    public void setTaxpayerManager(TaxpayerManager taxpayerManager) {
	this.taxpayerManager = taxpayerManager;
	
    }

    public Taxpayer getTaxpayer(int trn) {
	return taxpayerManager.getTaxpayer(trn);
	
    }

    public String[] getLastLoadedTaxpayerNameAndTrn() {
	return taxpayerManager.getLastLoadedTaxpayerNameAndTrn();
    }
    
    public List<String> getFileFormats(){
	return fileManager.getFileFormats();
    }
    
//    public Taxpayer getTaxpayer(int taxRegNum) {
//	return taxpayerManager.getTaxpayer(taxRegNum);
//	
//    }

}
