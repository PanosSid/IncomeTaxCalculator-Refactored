package incometaxcalculator.data.management;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import incometaxcalculator.data.io.TaxFileManager;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;

public class MainManager {
    private static MainManager instance; 
    private TaxpayerManager taxpayerManager;
    private TaxFileManager fileManager;

    private MainManager() {	// TODO use dependecy injection? maybe no 
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

    public void loadTaxpayer(String fileName) throws Exception {
	Map<String, List<String>> infoFileContents = fileManager.readTaxpayerFromFile(fileName);
	taxpayerManager.laodReadTaxpayer(fileManager.getTaxRegNumFromFileNamePath(fileName), infoFileContents);
    }

    public void removeTaxpayer(int taxRegNum) {
	taxpayerManager.removeTaxpayer(taxRegNum);
	fileManager.removeTaxpayerFilePath(taxRegNum);
    }

    public void addReceiptToTaxpayer(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegNum)
	    throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {
	taxpayerManager.createReceipt2(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
		taxRegNum);
	List<String> taxpayerInfoData = taxpayerManager.getTaxpayerInfoData(taxRegNum);
	Map<Integer, List<String>> receiptsDataOfTaxpayer = taxpayerManager.getReceiptsDataOfTaxpayer(taxRegNum);
	fileManager.updateTaxpayerFiles(taxRegNum, taxpayerInfoData, receiptsDataOfTaxpayer);
    }

    public void deleteReceiptFromTaxpayer(int receiptId, int taxRegNum) throws IOException {
	taxpayerManager.deleteReceiptFromTaxpayer2(receiptId, taxRegNum);
	List<String> taxpayerInfoData = taxpayerManager.getTaxpayerInfoData(taxRegNum);
	Map<Integer, List<String>> receiptsDataOfTaxpayer = taxpayerManager.getReceiptsDataOfTaxpayer(taxRegNum);
	fileManager.updateTaxpayerFiles(taxRegNum, taxpayerInfoData, receiptsDataOfTaxpayer);
    }

    public void saveLogFile(int taxRegNum, String filePath, String fileFormat)
	    throws IOException, WrongFileFormatException {
	List<String> logData = taxpayerManager.getLogData(taxRegNum);
	boolean taxIncrease = taxpayerManager.isTaxIncreasing(taxRegNum);
	String fileNamePath = filePath + "\\" + taxRegNum + "_LOG.";
	fileManager.saveLogeFile(fileNamePath, fileFormat, logData, taxIncrease);
    }

    public void setTaxpayerManager(TaxpayerManager taxpayerManager) {
	this.taxpayerManager = taxpayerManager;
	
    }
    
    public Taxpayer getTaxpayer(int taxRegNum) {
	return taxpayerManager.getTaxpayer(taxRegNum);
	
    }

}
