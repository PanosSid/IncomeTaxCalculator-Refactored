package incometaxcalculator.data.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import incometaxcalculator.tags.Tag;

public class InfoWriter extends TaxFileWriter {
    private String fileNamePath;
    private List<Tag> infoTags;
    private List<Tag> receiptsTags;
   
    public InfoWriter(String fileNamePath, List<Tag> infoTags, List<Tag> receiptsTags) {
	this.fileNamePath = fileNamePath;
	this.infoTags = infoTags;
	this.receiptsTags = receiptsTags;
    }

    public void updateInfoFile(List<String> taxpayerInfoData, Map<Integer, List<String>> receiptsDataOfTaxpayer)
	    throws IOException {
	FileWriter infoFile = new FileWriter(fileNamePath);
	PrintWriter outputStream = new PrintWriter(infoFile);
	writeTaxpayerInfoData(taxpayerInfoData, outputStream);
	writeReceiptsData(receiptsDataOfTaxpayer, outputStream);
	outputStream.close();
    }
    


    private void writeTaxpayerInfoData(List<String> infoData, PrintWriter outputStream) {
	List<String> taggedTaxpayerInfo = new ArrayList<String>();
	for (int i = 0; i < infoData.size(); i++) {
	    Tag tag = infoTags.get(i);
	    taggedTaxpayerInfo.add(tag.addTagsToData(infoData.get(i)));
	}
	writeTaggedData(taggedTaxpayerInfo, outputStream);
    }

    private void writeReceiptsData(Map<Integer, List<String>> receiptsDataMap, PrintWriter outputStream) {
//	String receiptSeperator = 
	List<String> taggedReceipts = new ArrayList<String>();
	for (Integer id : receiptsDataMap.keySet()) {
	    List<String> receiptData = receiptsDataMap.get(id); // common
	    taggedReceipts.addAll(getTaggedData(receiptData, receiptsTags.subList(1, receiptsTags.size())));
	    taggedReceipts.add("");
	}
	outputStream.println();
	outputStream.println(receiptsTags.get(0).getHeaderTag());
	outputStream.println();
	writeTaggedData(taggedReceipts, outputStream);
	outputStream.print(receiptsTags.get(0).getFooterTag());
    }

    @Override
    public void generateFile(List<String> data) throws IOException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void writeDataToFile(String fileNamePath, List<String> dataCollection) {
	// TODO Auto-generated method stub
	
    }

}
