package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import incometaxcalculator.data.management.TaxpayerManager;

public abstract class LogWriter extends FileWriter {
    
//    protected TaxpayerManager taxpayerManager;
   protected final int TAX_INCREASE_INDEX = 4;
   protected boolean taxIncrease; 
   protected static final short ENTERTAINMENT = 0;
   protected static final short BASIC = 1;
   protected static final short TRAVEL = 2;
   protected static final short HEALTH = 3;
   protected static final short OTHER = 4;

    public LogWriter(TaxpayerManager taxpayerManager) {
	super(taxpayerManager);
//	this.taxpayerManager = taxpayerManager;
	
    }
    
    protected abstract String getFileName(int taxRegistrationNumber);
    
    protected abstract List<String> getLogTags(); 
    
    protected abstract String mergeTagWithData(String tag, String data);
    
    public void generateFile(int taxRegistrationNumber) throws IOException {
	String fileName = getFileName(taxRegistrationNumber);
	java.io.FileWriter logFile = new java.io.FileWriter(fileName);
	PrintWriter outputStream = new PrintWriter(logFile);
	List<String> logData = getLogData(taxRegistrationNumber);
	List<String> logTags = getLogTags();
	List<String> logTaggedData = addTagsToData(logTags, logData);
	writeLogToFile(logTaggedData, outputStream);
	outputStream.close();
    }

    private void writeLogToFile(List<String> logFileLines, PrintWriter outputStream) {
	for (String line: logFileLines) {
	    outputStream.println(line);
	}
    }
    
    private List<String> addTagsToData(List<String> tags, List<String> data) {
	List<String> mergedDataWithTags = new ArrayList<String>();
	for (int i = 0; i < tags.size(); i++) {
	    String logLine = mergeTagWithData(tags.get(i), data.get(i));
	    mergedDataWithTags.add(logLine);
	}
	return mergedDataWithTags ;
    }


    private List<String> getLogData(int taxRegistrationNumber) {
	List<String> logData = new ArrayList<String>();
	logData.add("" + taxpayerManager.getTaxpayerName(taxRegistrationNumber));
	logData.add("" + taxRegistrationNumber);
	logData.add("" + taxpayerManager.getTaxpayerIncome(taxRegistrationNumber));
	logData.add("" + taxpayerManager.getTaxpayerBasicTax(taxRegistrationNumber));	
	if (taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) {
	    taxIncrease = true;
	} else {
	    taxIncrease = false;
	}
	logData.add("" + taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber));
	
	logData.add("" + taxpayerManager.getTaxpayerTotalTax(taxRegistrationNumber));
	logData.add("" + taxpayerManager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER));
	return logData;
	
    }
    

    
}
