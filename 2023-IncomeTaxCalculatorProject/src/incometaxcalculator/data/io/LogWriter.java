package incometaxcalculator.data.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import incometaxcalculator.data.management.TaxpayerManager;

public abstract class LogWriter implements TaxFileWriter {
    protected boolean taxIncrease;
    protected String fileNamePath;
    private List<String> logData;

    public LogWriter(String fileNamePath, boolean taxIncrease, List<String> logData) {
	super();
	this.fileNamePath = fileNamePath;
	this.taxIncrease = taxIncrease;
	this.logData = logData;
    }

    protected abstract String getFileName(int taxRegistrationNumber);

    protected abstract List<String> getLogTags();

    protected abstract String mergeTagWithData(String tag, String data);

    public void generateFile(int taxRegistrationNumber) throws IOException {
	FileWriter logFile = new FileWriter(fileNamePath);
	System.out.println(fileNamePath);
	PrintWriter outputStream = new PrintWriter(logFile);
	List<String> logTags = getLogTags();
	List<String> logTaggedData = addTagsToData(logTags, logData);
	writeLogToFile(logTaggedData, outputStream);
	outputStream.close();
    }

    private void writeLogToFile(List<String> logFileLines, PrintWriter outputStream) {
	for (String line : logFileLines) {
	    outputStream.println(line);
	}
    }

    private List<String> addTagsToData(List<String> tags, List<String> data) {
	List<String> mergedDataWithTags = new ArrayList<String>();
	for (int i = 0; i < tags.size(); i++) {
	    String logLine = mergeTagWithData(tags.get(i), data.get(i));
	    mergedDataWithTags.add(logLine);
	}
	return mergedDataWithTags;
    }

    @Override
    public void updateInfoFile(List<String> taxpayerInfoData, Map<Integer, List<String>> receiptsDataOfTaxpayer)
	    throws IOException {
	// TODO Auto-generated method stub

    }

}
