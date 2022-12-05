package incometaxcalculator.data.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.Taxpayer;
import incometaxcalculator.data.management.TaxpayerManager;

public abstract class TaxFileWriter {
    protected TaxpayerManager taxpayerManager;
    protected String pathToWriteInfo;   
    
    public TaxFileWriter() {
	this.taxpayerManager = TaxpayerManager.getInstance();
	pathToWriteInfo = System.getProperty("user.dir"); /// auto prepi na einai to location apo ekei pou to fortono
    }

    public abstract void generateFile(int taxRegistrationNumber) throws IOException;
//    public void generateFile(int taxRegistrationNumber) throws IOException;
    
    public abstract void updateInfoFile(List<String> taxpayerInfoData, Map<Integer, List<String>> receiptsDataOfTaxpayer) throws IOException;
}