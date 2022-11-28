package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public class TXTInfoWriter extends InfoWriter {

    public TXTInfoWriter(TaxpayerManager taxpayerManager) {
	super(taxpayerManager);
    }

/*
    public void generateFile(int taxRegistrationNumber) throws IOException {
	
	PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_INFO.txt"));
	outputStream.println("Name: " + taxpayerManager.getTaxpayerName(taxRegistrationNumber));
	outputStream.println("AFM: " + taxRegistrationNumber);
	outputStream.println("Status: " + taxpayerManager.getTaxpayerStatus(taxRegistrationNumber));
	outputStream.println("Income: " + taxpayerManager.getTaxpayerIncome(taxRegistrationNumber));
	outputStream.println();// den mas emfanize to \n se aplo notepad
	outputStream.println("Receipts:");
	outputStream.println();
	generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
	outputStream.close();
    }

    private void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream) {

	HashMap<Integer, Receipt> receiptsHashMap = taxpayerManager.getReceiptHashMap(taxRegistrationNumber);
	Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
	while (iterator.hasNext()) {
	    HashMap.Entry<Integer, Receipt> entry = iterator.next();
	    Receipt receipt = entry.getValue();
	    outputStream.println("Receipt ID: " + receipt.getId());
	    outputStream.println("Date: " + receipt.getIssueDate());
	    outputStream.println("Kind: " + receipt.getKind());
	    outputStream.println("Amount: " + receipt.getAmount());
	    outputStream.println("Company: " + receipt.getCompanyName());
	    outputStream.println("Country: " + receipt.getCompanyCountry());
	    outputStream.println("City: " + receipt.getCompanyCity());
	    outputStream.println("Street: " + receipt.getCompanyStreet());
	    outputStream.println("Number: " + receipt.getCompanyNumber());
	    outputStream.println();
	}
    }
*/

    @Override
    protected String getFileName(int taxRegistrationNumber) {
	return taxRegistrationNumber+"_INFO.txt";
    }

    @Override
    protected void writeTaxpayerInfoToFile(int taxRegistrationNumber, PrintWriter outputStream) {
	outputStream.println("Name: " + taxpayerManager.getTaxpayerName(taxRegistrationNumber));
	outputStream.println("AFM: " + taxRegistrationNumber);
	outputStream.println("Status: " + taxpayerManager.getTaxpayerStatus(taxRegistrationNumber));
	outputStream.println("Income: " + taxpayerManager.getTaxpayerIncome(taxRegistrationNumber));
	outputStream.println();
	outputStream.println("Receipts:");
	outputStream.println();
	
    }

    @Override
    protected void writeTaxpayersReceiptsToFile(int taxRegistrationNumber, PrintWriter outputStream) {
	HashMap<Integer, Receipt> receiptsHashMap = taxpayerManager.getReceiptHashMap(taxRegistrationNumber);
	Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
	while (iterator.hasNext()) {
	    HashMap.Entry<Integer, Receipt> entry = iterator.next();
	    Receipt receipt = entry.getValue();
	    outputStream.println("Receipt ID: " + receipt.getId());
	    outputStream.println("Date: " + receipt.getIssueDate());
	    outputStream.println("Kind: " + receipt.getKind());
	    outputStream.println("Amount: " + receipt.getAmount());
	    outputStream.println("Company: " + receipt.getCompanyName());
	    outputStream.println("Country: " + receipt.getCompanyCountry());
	    outputStream.println("City: " + receipt.getCompanyCity());
	    outputStream.println("Street: " + receipt.getCompanyStreet());
	    outputStream.println("Number: " + receipt.getCompanyNumber());
	    outputStream.println();
	}
	
    }

}