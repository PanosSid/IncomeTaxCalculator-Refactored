package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;

public class FileWriterFactory {
    
    // NOTE exei ousia na yparxei mia geniki pou na kanei delagete se alles ???
    // mallon oxi an den ftiaksoume enums gia to fileType fileFormat
//    public FileWriter createFileWriter(String fileType, String fileFormat) {
//	if (fileType.equals("INFO")) {
//	    return createInfoFileWriter(fileFormat);
//	} else if (fileType.equals("LOG")) {
//	    return createLogFileWriter(fileFormat);
//	} else {
//	    return null ;
////	    throw new Exception("Wrong file format given to FileWriterFactory");
//	}   
//    }
    
    public FileWriter createInfoFileWriter(String fileFormat) {
	if (fileFormat.equals("txt")) {
	    return new TXTInfoWriter();
	} else if (fileFormat.equals("xml")) {
	    return new XMLInfoWriter();
	} else {
	    return null ;
//	    throw new Exception("Wrong file format given to FileWriterFactory");
	}
    }
    
    public FileWriter createLogFileWriter(String fileFormat) {
	if (fileFormat.equals("txt")) {
	    return new TXTLogWriter();
	} else if (fileFormat.equals("xml")) {
	    return new XMLLogWriter();
	} else {
	    return null ;
//	    throw new Exception("Wrong file format given to FileWriterFactory");
	}
    }
    
    
    
    
}
