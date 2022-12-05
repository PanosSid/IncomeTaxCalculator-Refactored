package incometaxcalculator.data.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import incometaxcalculator.data.management.TaxpayerManager;

public abstract class LogWriter extends TaxFileWriter {
   protected boolean taxIncrease; 

    public LogWriter() {
	super();
	super.pathToWriteInfo += "\\resources\\LOG files\\";
    }
    
    protected abstract String getFileName(int taxRegistrationNumber);
    
    protected abstract List<String> getLogTags(); 
    
    protected abstract String mergeTagWithData(String tag, String data);
    
    public void generateFile(int taxRegistrationNumber) throws IOException {
//	String fileName = getFileName(taxRegistrationNumber);
	String namePath = pathToWriteInfo+ getFileName(taxRegistrationNumber);
	FileWriter logFile = new FileWriter(namePath);
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
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Entertainment"));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Basic"));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Travel"));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Health"));
	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, "Other"));
	
//	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT));
//	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC));
//	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL));
//	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH));
//	logData.add("" + taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER));
	return logData;
	
    }
    
    @Override
    public void updateInfoFile(List<String> taxpayerInfoData, Map<Integer, List<String>> receiptsDataOfTaxpayer)
	    throws IOException {
	// TODO Auto-generated method stub
	
    }
    

    
}
