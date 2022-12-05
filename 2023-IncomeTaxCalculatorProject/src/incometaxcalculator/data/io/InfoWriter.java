package incometaxcalculator.data.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public abstract class InfoWriter extends TaxFileWriter {
    protected String fileNamePath;
    
    public InfoWriter(String fileNamePath) {
//	super();
	this.fileNamePath = fileNamePath;
    }

//    protected abstract String getFileName(int taxRegistrationNumber);
    protected abstract String getFileFormat();

    protected abstract List<String> getInfoTags();

    protected abstract List<String> getReceiptTags();

    protected abstract String mergeTagWithData(String tag, String data);

    protected abstract String getReceiptSeperatorHeader();
    
    protected abstract String getReceiptSeperatorFooter();
    
    public void updateInfoFile(List<String> taxpayerInfoData,
	    Map<Integer, List<String>> receiptsDataOfTaxpayer ) throws IOException {
	FileWriter infoFile = new FileWriter(fileNamePath);
	PrintWriter outputStream = new PrintWriter(infoFile);
	writeTaxpayerInfoData(taxpayerInfoData, outputStream);
	writeReceiptsData(receiptsDataOfTaxpayer, outputStream);	
	outputStream.close();
    }
    
    private void writeTaxpayerInfoData(List<String> infoData, PrintWriter outputStream) {
	List<String> infoTags = getInfoTags();
	List<String> taggedTaxpayerInfo = addTagsToData(infoTags, infoData);
	writeTaggedDataToInfoFile(taggedTaxpayerInfo, outputStream);
    }
    
    private void writeReceiptsData(Map<Integer, List<String>> receiptsDataOfTaxpayer,
	    	PrintWriter outputStream) {
	List<String> receiptTags = getReceiptTags();
	List<String> taggedReceipts = getTaggedReceiptsData(receiptTags, receiptsDataOfTaxpayer);
	
	outputStream.println();
	outputStream.println(getReceiptSeperatorHeader());
	outputStream.println();
	writeTaggedDataToInfoFile(taggedReceipts, outputStream);
	outputStream.print(getReceiptSeperatorFooter());
    }
    
    private List<String> getTaggedReceiptsData(List<String> receiptTags,
	    			Map<Integer, List<String>> receiptsDataMap) {
	List<String> taggedReceipts = new ArrayList<String>();
	for (Integer id : receiptsDataMap.keySet()) {
	    List<String> receiptData = receiptsDataMap.get(id); // common
	    List<String> taggedReceiptData = addTagsToData(receiptTags, receiptData);
	    taggedReceipts.addAll(taggedReceiptData);
	    taggedReceipts.add("");
	}
	return taggedReceipts;
    }
  
    private List<String> addTagsToData(List<String> tags, List<String> data) {
	List<String> mergedDataWithTags = new ArrayList<String>();
	for (int i = 0; i < tags.size(); i++) {
	    String logLine = mergeTagWithData(tags.get(i), data.get(i));
	    mergedDataWithTags.add(logLine);
	}
	return mergedDataWithTags;
    }

    private void writeTaggedDataToInfoFile(List<String> infoLines, PrintWriter outputStream) {
	for (String line : infoLines) {
	    outputStream.println(line);
	}
    }
    
    @Override
    public void generateFile(int taxRegistrationNumber) throws IOException {
	// TODO Auto-generated method stub
	
    }


}
