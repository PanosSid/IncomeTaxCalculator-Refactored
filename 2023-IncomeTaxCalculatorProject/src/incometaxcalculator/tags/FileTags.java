package incometaxcalculator.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileTags {
    private String fileFormat;
//    private Map<String, Tag> infoTags;
//    private Map<String, Tag> receiptTags;
//    private Map<String, Tag> logTags;
    private List<Tag> infoTags;
    private List<Tag> receiptTags;
    private List<Tag> logTags;
    
    public FileTags(String fileFormat) {
	this.fileFormat = fileFormat;
	initInfoTags();
	initReceiptTags();
	initLogTags();
    }
    
    public String getFileFormat() {
        return fileFormat;
    }

//    public Map<String, Tag> getInfoTags() {
//        return infoTags;
//    }
//
//    public Map<String, Tag> getReceiptTags() {
//        return receiptTags;
//    }
//
//    public Map<String, Tag> getLogTags() {
//        return logTags;
//    }
    
    public List<Tag> getInfoTags() {
	return infoTags;
    }
    
    public List<Tag> getReceiptTags() {
	return receiptTags;
    }
    
    public List<Tag> getLogTags() {
	List<Tag> fullLogTags = new ArrayList<Tag>();
	fullLogTags.add(infoTags.get(0));	// name 
	fullLogTags.add(infoTags.get(1));	// afm
	fullLogTags.add(infoTags.get(3));	// income
	fullLogTags.addAll(logTags);
	return fullLogTags;
    }
    

    private void initInfoTags() {
//	String infoTagNames[] = {"Name", "TRN", "Status", "Income"};
//	String infoTagData[] =  {"Name", "AFM", "Status", "Income"};
//	infoTags = tagFactory.createMapOfTags(fileFormat, infoTagNames, infoTagData);	
	FileTagFactory tagFactory = new FileTagFactory();
//	infoTags = tagFactory.createListOfTags(fileFormat, infoTagNames, infoTagData);
	infoTags = tagFactory.createListOfTags(fileFormat, "info");
    }
    

    private void initReceiptTags() {
//	String reciptTagNames[] = {"Receipts", "Receipt ID", "Date","Kind",
//		"Amount", "Company", "Country", "City", "Street", "Number"};
//	String receiptTagData[] =  {"Receipts", "Receipt ID", "Date","Kind",
//		"Amount", "Company", "Country", "City", "Street", "Number"};
	FileTagFactory tagFactory = new FileTagFactory();
//	receiptTags = tagFactory.createListOfTags(fileFormat, reciptTagNames, receiptTagData);	
	receiptTags = tagFactory.createListOfTags(fileFormat, "receipts");
    }

     
    private void initLogTags() {
//	String logTagNames[] = {"Basic Tax", "Tax Increase", "Total Tax","TotalReceiptsGathered",
//		"Entertainment", "Basic","Travel", "Health", "Other" };
//	String logTagData[] =  {"Basic Tax", "Tax Increase", "Total Tax","TotalReceiptsGathered",
//		"Entertainment", "Basic","Travel", "Health", "Other" };
	FileTagFactory tagFactory = new FileTagFactory();
//	logTags = tagFactory.createListOfTags(fileFormat, logTagNames, logTagData);	
	logTags = tagFactory.createListOfTags(fileFormat, "log");
    }
    
    
    
    
}
