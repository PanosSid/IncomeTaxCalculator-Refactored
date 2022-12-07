package incometaxcalculator.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileTagFactory {
    private String[] infoTagData = {"Name", "AFM", "Status", "Income"};
    private String[] receiptTagData = {"Receipts", "Receipt ID", "Date","Kind",
		"Amount", "Company", "Country", "City", "Street", "Number"};
    private String[] logTagData = {"Basic Tax", "Tax Increase", "Total Tax","TotalReceiptsGathered",
		"Entertainment", "Basic","Travel", "Health", "Other" };
    
//    public Map<String, Tag> createMapOfTags(String fileFormat, String tagNames[], String tagData[]) {
//   	Map<String, Tag> tagsMap = new HashMap<String, Tag>();
//      	for (int i = 0; i < tagNames.length; i++) {
//      	    tagsMap.put(tagNames[i], createTag(fileFormat, tagNames[i], tagData[i]));
//      	}
//      	return tagsMap;	
//       }

    public List<Tag> createListOfTags(String fileFormat, String type) {
	if (type.equals("info")) {
	   return  createListOfTags(fileFormat, infoTagData);
	} else if (type.equals("receipts")) {
	   return createListOfTags(fileFormat, receiptTagData);
	} else if (type.equals("log")) {
	    if (fileFormat.equals("xml"))
		logTagData[3] = "Receipts";
	   return createListOfTags(fileFormat, logTagData);
	}
	return null;
    }
    
    public List<Tag> createListOfTags(String fileFormat, String tagData[]) {
	List<Tag> tagsList = new ArrayList<Tag>();
	for (int i = 0; i < tagData.length; i++) {
	    tagsList.add(createTag(fileFormat, tagData[i]));
	}
	return tagsList;
    }

    public Tag createTag(String fileFormat, String string) {
	if (fileFormat.equals("txt")) {
	    return new TXTTag(string);
	} else if (fileFormat.equals("xml")) {
	    return new XMLTag(string);
	}
	return null;
	
    }

    
   
}
