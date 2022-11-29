package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public class TXTInfoWriter extends InfoWriter {

    public TXTInfoWriter(TaxpayerManager taxpayerManager) {
	super(taxpayerManager);
    }

    @Override
    protected String getFileName(int taxRegistrationNumber) {
	return taxRegistrationNumber+"_INFO.txt";
    }

    @Override
    protected List<String> getInfoTags() {
	List<String> txtInfoTags = new ArrayList<String>();
	txtInfoTags.add("Name: ");
	txtInfoTags.add("AFM: ");
	txtInfoTags.add("Status: ");
	txtInfoTags.add("Income: ");
	txtInfoTags.add("");
	txtInfoTags.add("Receipts:");
	txtInfoTags.add("");
	return txtInfoTags;
    }
    
    @Override
    protected List<String> getReceiptTags(){
	List<String> receiptTags = new ArrayList<String>();
	receiptTags.add("Receipt ID: ");
	receiptTags.add("Date: ");
	receiptTags.add("Kind: ");
	receiptTags.add("Amount: "); 
	receiptTags.add("Company: ");
	receiptTags.add("Country: ");
	receiptTags.add("City: ");
	receiptTags.add("Street: ");
	receiptTags.add("Number: ");
	receiptTags.add("");
	return receiptTags;
    }

    @Override
    protected String mergeTagWithData(String tag, String data) {
	return tag + data;
    }

    @Override
    protected void writeReciptFooter(PrintWriter outputStream) {
	// dummy implementation do nothing
	
    }
    
    

   

}