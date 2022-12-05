package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import incometaxcalculator.data.management.TaxpayerManager;

public class TXTLogWriter extends LogWriter {

    public TXTLogWriter() {
	super();
    }

    @Override
    protected String getFileName(int taxRegistrationNumber) {
	return taxRegistrationNumber+"_LOG.txt";
    }
    
    @Override
    protected List<String> getLogTags() {
	List<String> txtLogLines = new ArrayList<String>();
	txtLogLines.add("Name: ");
	txtLogLines.add("AFM: ");
	txtLogLines.add("Income: ");
	txtLogLines.add("Basic Tax: ");
	if (taxIncrease == true) {
	    txtLogLines.add("Tax Increase: ");
	} else {
	    txtLogLines.add("Tax Decrease: ");
	}
	txtLogLines.add("Total Tax: ");
	txtLogLines.add("TotalReceiptsGathered: ");
	txtLogLines.add("Entertainment: ");
	txtLogLines.add("Basic: ");
	txtLogLines.add("Travel: ");
	txtLogLines.add("Health: ");
	txtLogLines.add("Other: ");
	return txtLogLines;
    }

    @Override
    protected String mergeTagWithData(String tag, String data) {
	return tag + data;
    }
    
}
