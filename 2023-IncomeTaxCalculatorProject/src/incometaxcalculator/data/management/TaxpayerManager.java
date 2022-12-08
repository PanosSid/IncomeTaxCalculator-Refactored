package incometaxcalculator.data.management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import incometaxcalculator.data.config.AppConfig;
import incometaxcalculator.data.io.InfoWriter;
import incometaxcalculator.data.io.LogWriter;
import incometaxcalculator.data.io.TaxFileReader;
import incometaxcalculator.data.io.TaxFileWriter;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.TaxpayerAlreadyLoadedException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;
import incometaxcalculator.tags.FileTags;

public class TaxpayerManager {
    public AppConfig appConfig;
    private Map<Integer, Taxpayer> taxpayerHashMap;

    public TaxpayerManager() {
	taxpayerHashMap = new LinkedHashMap<Integer, Taxpayer>(0);
	appConfig = new AppConfig();
    }

//    public static void tearDownTaxpayerManager() {
//	instance = null;
//    }

    public Map<Integer, Taxpayer> getTaxpayerHashMap() {
	return taxpayerHashMap;
    }

    public void laodReadTaxpayer(int trn, Map<String, List<String>> infoFileContents) // TODO renaming
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
	if (taxpayerHashMap.containsKey(taxRegNum)) {
	    throw new TaxpayerAlreadyLoadedException();
	} else {
	    TaxpayerCategory taxpayerCategory = appConfig.getTaxpayerCategoryByName(status);
	    Taxpayer taxpayer = new Taxpayer(fullname, taxRegNum, income, taxpayerCategory);
	    taxpayerHashMap.put(taxRegNum, taxpayer);
	}
    }

    private void loadReceiptsData(int taxRegNum, Map<String, List<String>> receiptsMap) // TODO loop ? + enum
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

    public void removeTaxpayer(int taxRegNum) {
	taxpayerHashMap.remove(taxRegNum);
//	filePathsMap.remove(taxRegNum);
    }

    void createReceipt2(int receiptId, String issueDate, float amount, String kind, String companyName, String country,
	    String city, String street, int number, int taxRegNum)
	    throws WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {
	if (containsReceipt(taxRegNum, receiptId)) {
	    throw new ReceiptAlreadyExistsException();
	}
	Company company = new Company(companyName, country, city, street, number);
	Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, company);
	Taxpayer taxpayer = taxpayerHashMap.get(taxRegNum);
	taxpayer.addReceipt(receipt);
    }

    
    public void deleteReceiptFromTaxpayer2(int receiptId, int trn) throws IOException {
	taxpayerHashMap.get(trn).removeReceipt(receiptId);
    }

    private boolean containsReceipt(int taxRegNum, int receiptid) {
	return taxpayerHashMap.get(taxRegNum).hasReceiptId(receiptid);
    }

    public List<String> getLogData(int taxRegNum) {
	List<String> logData = new ArrayList<String>();
	logData.add("" + getTaxpayerName(taxRegNum));
	logData.add("" + taxRegNum);
	logData.add("" + getTaxpayerIncome(taxRegNum));
	logData.add("" + getTaxpayerBasicTax(taxRegNum));
	logData.add("" + getTaxpayerVariationTaxOnReceipts(taxRegNum));
	logData.add("" + getTaxpayerTotalTax(taxRegNum));
	logData.add("" + getTaxpayerTotalReceiptsGathered(taxRegNum));

//	Map<String, Float> amountPerReceipKind = getAllReceiptsAmountOfTaxpayer(taxRegNum);
//	for (String kindName : amountPerReceipKind.keySet()) {
//	    logData.add("" + amountPerReceipKind.get(kindName));
//	}

	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegNum, "Entertainment"));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegNum, "Basic"));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegNum, "Travel"));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegNum, "Health"));
	logData.add("" + getTaxpayerAmountOfReceiptKind(taxRegNum, "Other"));
	return logData;

    }

    public Taxpayer getTaxpayer(int taxRegNum) {
	return taxpayerHashMap.get(taxRegNum);
    }

    public String getTaxpayerName(int taxRegNum) {
	return taxpayerHashMap.get(taxRegNum).getFullname();
    }

    public String getTaxpayerCategoryName(int taxRegNum) {
	return taxpayerHashMap.get(taxRegNum).getTaxpayerCategoryName();
    }

    public String getTaxpayerIncome(int taxRegNum) {
	return "" + taxpayerHashMap.get(taxRegNum).getIncome();
    }

    public double getTaxpayerVariationTaxOnReceipts(int taxRegNum) { // LOG + REPORTS
	return taxpayerHashMap.get(taxRegNum).getVariationTaxOnReceipts();
    }

    public int getTaxpayerTotalReceiptsGathered(int taxRegNum) { // LOG
	return taxpayerHashMap.get(taxRegNum).getTotalReceiptsGathered();
    }

    public float getTaxpayerAmountOfReceiptKind(int taxRegNum, String kind) { // LOG + REPORTS
	return taxpayerHashMap.get(taxRegNum).getAmountOfReceiptKind(kind);
    }

    public double getTaxpayerTotalTax(int taxRegNum) { // LOG + REPORTS
	return taxpayerHashMap.get(taxRegNum).getTotalTax();
    }

    public double getTaxpayerBasicTax(int taxRegNum) { // LOG + REPORTS
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

    public String[] getLastLoadedTaxpayerNameAndTrn() {
	int numberOfloadedTaxpayers = taxpayerHashMap.size();
	List<Integer> lKeys = new ArrayList<Integer>(taxpayerHashMap.keySet());
	Integer lastTrn = lKeys.get(numberOfloadedTaxpayers - 1);
	String lastName = taxpayerHashMap.get(lastTrn).getFullname();
	String[] nameAndTrn = { lastName, "" + lastTrn };
	return nameAndTrn;
    }

    public List<String> getTaxpayerInfoData(int taxRegNum) {
	return taxpayerHashMap.get(taxRegNum).getTaxpayerInfoData();
    }

    public Map<Integer, List<String>> getReceiptsDataOfTaxpayer(int taxRegNum) {
	return taxpayerHashMap.get(taxRegNum).getReceiptsDataOfTaxpayer();
    }

    public boolean isTaxIncreasing(int taxRegNum) {
	if (getTaxpayerVariationTaxOnReceipts(taxRegNum) > 0)
	    return true;
	return  false;
    }
}