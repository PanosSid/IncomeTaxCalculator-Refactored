package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public abstract class InfoWriter extends FileWriter {

    public InfoWriter() {
	super();
    }

    protected abstract String getFileName(int taxRegistrationNumber);

    protected abstract List<String> getInfoTags();

    protected abstract List<String> getReceiptTags();

    protected abstract String mergeTagWithData(String tag, String data);

    protected abstract void writeReciptFooter(PrintWriter outputStream);

    public void generateFile(int taxRegistrationNumber) throws IOException {
	String fileName = getFileName(taxRegistrationNumber);
	java.io.FileWriter infoFile = new java.io.FileWriter(fileName);
	PrintWriter outputStream = new PrintWriter(infoFile);

	List<String> infoData = getTaxpayerInfoData(taxRegistrationNumber); // common
	List<String> infoTags = getInfoTags();
	List<String> taggedTaxpayerInfo = addTagsToData(infoTags, infoData);
	writeTaggedDataToInfoFile(taggedTaxpayerInfo, outputStream);

	List<String> receiptTags = getReceiptTags();
	List<Receipt> receiptsList = taxpayerManager.getReceiptListOfTaxpayer(taxRegistrationNumber); 
	writeReceiptsToInfoFile(receiptTags, receiptsList, outputStream);
	outputStream.close();
    }

    private List<String> getTaxpayerInfoData(int taxRegistrationNumber) {
	List<String> infoData = new ArrayList<String>();
	infoData.add("" + taxpayerManager.getTaxpayerName(taxRegistrationNumber));
	infoData.add("" + taxRegistrationNumber);
	infoData.add("" + taxpayerManager.getTaxpayerStatus(taxRegistrationNumber));
	infoData.add("" + taxpayerManager.getTaxpayerIncome(taxRegistrationNumber));
	infoData.add("");
	infoData.add("");
	infoData.add("");
	return infoData;
    }

    private List<String> addTagsToData(List<String> tags, List<String> data) {
	List<String> mergedDataWithTags = new ArrayList<String>();
	for (int i = 0; i < tags.size(); i++) {
	    String logLine = mergeTagWithData(tags.get(i), data.get(i));
	    mergedDataWithTags.add(logLine);
	}
	return mergedDataWithTags;
    }

    private void writeReceiptsToInfoFile(List<String> receiptTags, List<Receipt> receiptsList,
	    PrintWriter outputStream) {
	for (int i = 0; i < receiptsList.size(); i++) {
	    List<String> receiptData = getReceiptData(receiptsList.get(i)); // common
	    List<String> taggedReceipt = addTagsToData(receiptTags, receiptData);
	    writeTaggedDataToInfoFile(taggedReceipt, outputStream);
	}
	writeReciptFooter(outputStream);
    }

    private List<String> getReceiptData(Receipt receipt) {
	List<String> receiptData = new ArrayList<String>();
	receiptData.add("" + receipt.getId());
	receiptData.add("" + receipt.getIssueDate());
	receiptData.add("" + receipt.getKind());
	receiptData.add("" + receipt.getAmount());
	receiptData.add("" + receipt.getCompanyName());
	receiptData.add("" + receipt.getCompanyCountry());
	receiptData.add("" + receipt.getCompanyCity());
	receiptData.add("" + receipt.getCompanyStreet());
	receiptData.add("" + receipt.getCompanyNumber());
	receiptData.add("");
	return receiptData;
    }

    private void writeTaggedDataToInfoFile(List<String> infoLines, PrintWriter outputStream) {
	for (String line : infoLines) {
	    outputStream.println(line);
	}
    }

}
