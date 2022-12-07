package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import incometaxcalculator.tags.Tag;

public abstract class TaxFileWriter {
   
    public abstract void generateFile(List<String> data) throws IOException;
//    public void generateFile(int taxRegistrationNumber) throws IOException;
    
    public abstract void updateInfoFile(List<String> taxpayerInfoData, Map<Integer, List<String>> receiptsDataOfTaxpayer) throws IOException;
    
    protected List<String> getTaggedData(List<String> data, List<Tag> tags) {
	List<String> taggedData = new ArrayList<String>();
	for (int i = 0; i < data.size(); i++) {
	    taggedData.add(tags.get(i).addTagsToData(data.get(i)));
	}
	return taggedData;
    }

    protected void writeTaggedData(List<String> infoLines, PrintWriter outputStream) {
	for (String line : infoLines) {
	    outputStream.println(line);
	}
    }
    
    public abstract void writeDataToFile(String fileNamePath, List<String> dataCollection);
	
}