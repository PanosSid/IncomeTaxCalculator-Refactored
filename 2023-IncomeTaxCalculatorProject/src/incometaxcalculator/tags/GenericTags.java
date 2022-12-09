package incometaxcalculator.tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericTags {
    
    private String fileFormat;
    private List<String> infoHeaders;
    private List<String> infoFooters;
    private List<String> receiptHeaders;
    private List<String> receiptFooters;
    private List<String> logHeaders;
    private List<String> logFooters;

    public GenericTags(String fileFormat) {
	if (fileFormat.equals("txt")) {
	    infoHeaders = Arrays.asList(TxtTags.INFO_HEADERS);
	    infoFooters = Arrays.asList(TxtTags.INFO_FOOTERS);
	    receiptHeaders = Arrays.asList(TxtTags.RECEIPT_HEADERS);
	    receiptFooters = Arrays.asList(TxtTags.RECEIPT_FOOTERS);
	    logHeaders = Arrays.asList(TxtTags.LOG_HEADERS);
	    logFooters =Arrays.asList(TxtTags.LOG_FOOTERS);
	} else if (fileFormat.equals("xml")) {
	    infoHeaders = Arrays.asList(XmlTags.INFO_HEADERS);
	    infoFooters = Arrays.asList(XmlTags.INFO_FOOTERS);
	    receiptHeaders = Arrays.asList(XmlTags.RECEIPT_HEADERS);
	    receiptFooters = Arrays.asList(XmlTags.RECEIPT_FOOTERS);
	    logHeaders = Arrays.asList(XmlTags.LOG_HEADERS);
	    logFooters =Arrays.asList(XmlTags.LOG_FOOTERS);
	}
    }
    
    public String getFileFormat() {
	return fileFormat;
    }

    public List<String> getInfoHeaders() {
        return infoHeaders;
    }

    public List<String> getInfoFooters() {
        return infoFooters;
    }

    public List<String> getReceiptHeaders() {
        return receiptHeaders;
    }

    public List<String> getReceiptFooters() {
        return receiptFooters;
    }

    public List<String> getLogHeaders() {
        return logHeaders;
    }

    public List<String> getLogFooters() {
        return logFooters;
    }
    
    public List<List<String>> getTaxpayerAllInfoTags() {
	List<List<String>> tags = new ArrayList<>();
	tags.add(infoHeaders);
	tags.add(infoFooters);
	tags.add(receiptHeaders);
	tags.add(receiptFooters);
	return tags;
    }
    
}
