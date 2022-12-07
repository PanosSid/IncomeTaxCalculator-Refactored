package incometaxcalculator.data.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.tags.Tag;

public class LogWriter extends TaxFileWriter {
    protected boolean taxIncrease;
    protected String fileNamePath;
    private List<Tag> logTags;

    public LogWriter(String fileNamePath, boolean taxIncrease, List<Tag> logTags) {
	super();
	this.taxIncrease = taxIncrease;
	this.fileNamePath = fileNamePath;
	this.logTags = logTags;
    }

    public void generateFile(List<String> logData) throws IOException {
	FileWriter logFile = new FileWriter(fileNamePath);
	PrintWriter outputStream = new PrintWriter(logFile);
	List<String> logTaggedData = getTaggedData(logData, logTags);
	writeTaggedData(logTaggedData, outputStream);
	outputStream.close();
    }

    
    @Override
    public void updateInfoFile(List<String> taxpayerInfoData, Map<Integer, List<String>> receiptsDataOfTaxpayer)
	    throws IOException {
	// TODO Auto-generated method stub

    }

    @Override
    public void writeDataToFile(String fileNamePath, List<String> dataCollection) {
	// TODO Auto-generated method stub
	
    }

}
