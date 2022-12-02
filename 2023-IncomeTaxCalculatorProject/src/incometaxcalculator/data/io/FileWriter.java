package incometaxcalculator.data.io;

import java.io.IOException;
import java.util.HashMap;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.Taxpayer;
import incometaxcalculator.data.management.TaxpayerManager;

public abstract class FileWriter {
    protected TaxpayerManager taxpayerManager;
    
    public FileWriter() {
	this.taxpayerManager = TaxpayerManager.getInstance();
    }

    public abstract void generateFile(int taxRegistrationNumber) throws IOException;
//    public void generateFile(int taxRegistrationNumber) throws IOException;

}