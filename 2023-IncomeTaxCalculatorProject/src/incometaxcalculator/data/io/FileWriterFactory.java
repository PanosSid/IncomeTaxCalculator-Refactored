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
    
    public TaxFileWriter createInfoFileWriter(String fileNamePath, String fileFormat) {
	if (fileFormat.equals("txt")) {
	    return new TXTInfoWriter(fileNamePath+"."+fileFormat);
	} else if (fileFormat.equals("xml")) {
	    return new XMLInfoWriter(fileNamePath+"."+fileFormat);
	} else {
	    return null ;
//	    throw new Exception("Wrong file format given to FileWriterFactory");
	}
    }
    
    public TaxFileWriter createLogFileWriter(String fileNamePath, String fileFormat) {
	if (fileFormat.equals("txt")) {
	    return new TXTLogWriter(fileNamePath+"."+fileFormat);
	} else if (fileFormat.equals("xml")) {
	    return new XMLLogWriter(fileNamePath+"."+fileFormat);
	} else {
	    return null ;
//	    throw new Exception("Wrong file format given to FileWriterFactory");
	}
    }
    
    
    
    
}
