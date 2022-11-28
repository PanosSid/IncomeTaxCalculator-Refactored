package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileEndingException;

public class FileReaderFactory {
    
    public FileReader createFileReader(String fileFormat, TaxpayerManager taxpayerManager) throws WrongFileEndingException {
	if (fileFormat.equals("txt")) {
	    return new TXTFileReader(taxpayerManager);
	} else if (fileFormat.equals("xml")) {
	    return new XMLFileReader(taxpayerManager);
	} else {
	    throw new WrongFileEndingException();
	}
    }
}
