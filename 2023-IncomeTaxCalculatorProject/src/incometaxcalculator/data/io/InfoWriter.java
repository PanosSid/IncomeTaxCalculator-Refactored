package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;

import incometaxcalculator.data.management.TaxpayerManager;

public abstract class InfoWriter extends FileWriter {
    
//    protected TaxpayerManager taxpayerManager;
    
    public InfoWriter(TaxpayerManager taxpayerManager) {
	super(taxpayerManager);
//	this.taxpayerManager = taxpayerManager;
	// TODO Auto-generated constructor stub
    }
    
    public void generateFile(int taxRegistrationNumber) throws IOException {
	String fileName = getFileName(taxRegistrationNumber);
	java.io.FileWriter infoFile = new java.io.FileWriter(fileName);
	PrintWriter outputStream = new PrintWriter(infoFile);
	writeTaxpayerInfoToFile(taxRegistrationNumber, outputStream);
	writeTaxpayersReceiptsToFile(taxRegistrationNumber, outputStream);
	outputStream.close();
    }
    
    protected abstract String getFileName(int taxRegistrationNumber);

    protected abstract void writeTaxpayerInfoToFile(int taxRegistrationNumber, PrintWriter outputStream);

    protected abstract void writeTaxpayersReceiptsToFile(int taxRegistrationNumber, PrintWriter outputStream);



    
    
    
    
}
