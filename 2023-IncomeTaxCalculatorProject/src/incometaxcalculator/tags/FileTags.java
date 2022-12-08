package incometaxcalculator.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileTags {
    private String fileFormat;
    private List<Tag> infoTags;
    private List<Tag> receiptTags;
    private List<Tag> logTags;
    
    public FileTags(String fileFormat) {
	this.fileFormat = fileFormat;
	FileTagFactory tagFactory = new FileTagFactory();
	infoTags = tagFactory.createListOfTags(fileFormat, "info");
	receiptTags = tagFactory.createListOfTags(fileFormat, "receipts");
	logTags = tagFactory.createListOfTags(fileFormat, "log");
    }
    
    public String getFileFormat() {
        return fileFormat;
    }
    
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
    
    
}
