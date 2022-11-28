package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public class XMLInfoWriter extends InfoWriter {

    public XMLInfoWriter(TaxpayerManager taxpayerManager) {
	super(taxpayerManager);
    }
/*
    public void generateFile(int taxRegistrationNumber) throws IOException {

	PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_INFO.xml"));
	outputStream.println("<Name> " + taxpayerManager.getTaxpayerName(taxRegistrationNumber) + " </Name>");
	outputStream.println("<AFM> " + taxRegistrationNumber + " </AFM>");
	outputStream.println("<Status> " + taxpayerManager.getTaxpayerStatus(taxRegistrationNumber) + " </Status>");
	outputStream.println("<Income> " + taxpayerManager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
	outputStream.println();// den mas emfanize to \n se aplo notepad
	outputStream.println("<Receipts>");
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
	    outputStream.println("<ReceiptID> " + receipt.getId() + " </ReceiptID>");
	    outputStream.println("<Date> " + receipt.getIssueDate()+ " </Date>");
	    outputStream.println("<Kind> " + receipt.getKind() + " </Kind>");
	    outputStream.println("<Amount> " + receipt.getAmount() + " </Amount>");
	    outputStream.println("<Company> " + receipt.getCompanyName() + " </Company>");
	    outputStream.println("<Country> " + receipt.getCompanyCountry() + " </Country>");
	    outputStream.println("<City> " + receipt.getCompanyCity() + " </City>");
	    outputStream.println("<Street> " + receipt.getCompanyStreet() + " </Street>");
	    outputStream.println("<Number> " + receipt.getCompanyNumber() + " </Number>");
	    outputStream.println();
	}
    }
*/
    
    @Override
    protected String getFileName(int taxRegistrationNumber) {
	return taxRegistrationNumber+"_INFO.xml";
    }

    @Override
    protected void writeTaxpayerInfoToFile(int taxRegistrationNumber, PrintWriter outputStream) {
	outputStream.println("<Name> " + taxpayerManager.getTaxpayerName(taxRegistrationNumber) + " </Name>");
	outputStream.println("<AFM> " + taxRegistrationNumber + " </AFM>");
	outputStream.println("<Status> " + taxpayerManager.getTaxpayerStatus(taxRegistrationNumber) + " </Status>");
	outputStream.println("<Income> " + taxpayerManager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
	outputStream.println();// den mas emfanize to \n se aplo notepad
	outputStream.println("<Receipts>");
	outputStream.println();
    }

    @Override
    protected void writeTaxpayersReceiptsToFile(int taxRegistrationNumber, PrintWriter outputStream) {
	HashMap<Integer, Receipt> receiptsHashMap = taxpayerManager.getReceiptHashMap(taxRegistrationNumber);
	Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
	while (iterator.hasNext()) {
	    HashMap.Entry<Integer, Receipt> entry = iterator.next();
	    Receipt receipt = entry.getValue();
	    outputStream.println("<ReceiptID> " + receipt.getId() + " </ReceiptID>");
	    outputStream.println("<Date> " + receipt.getIssueDate()+ " </Date>");
	    outputStream.println("<Kind> " + receipt.getKind() + " </Kind>");
	    outputStream.println("<Amount> " + receipt.getAmount() + " </Amount>");
	    outputStream.println("<Company> " + receipt.getCompanyName() + " </Company>");
	    outputStream.println("<Country> " + receipt.getCompanyCountry() + " </Country>");
	    outputStream.println("<City> " + receipt.getCompanyCity() + " </City>");
	    outputStream.println("<Street> " + receipt.getCompanyStreet() + " </Street>");
	    outputStream.println("<Number> " + receipt.getCompanyNumber() + " </Number>");
	    outputStream.println();
	}
	
    }

}