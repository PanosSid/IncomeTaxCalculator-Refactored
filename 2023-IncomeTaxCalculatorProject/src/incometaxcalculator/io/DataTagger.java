package incometaxcalculator.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* Utility data tagger class*/
public class DataTagger {
    
    public static List<String> tagData(List<String> data, List<String> headers, List<String> footers) {
	List<String> taggedData = new ArrayList<String>();
	for (int i = 0; i < data.size(); i++) {
	    String dataWithTags = headers.get(i) + " " + data.get(i) + " " + footers.get(i);
	    taggedData.add( dataWithTags.trim());
	}
	return taggedData;
    }
    
    public static List<String> getTaggedTaxpayerAsList(List<String> taxpayerInfoData, Map<Integer,
	    List<String>> receiptsDataOfTaxpayer, List<List<String>> tags) {
	List<String> infoHeaders = tags.get(0);
	List<String> infoFooters = tags.get(1);
	List<String> receiptHeaders = tags.get(2);
	List<String> receiptFooters = tags.get(3);;
	return joinTwoList(getTaggedInfoData(taxpayerInfoData, infoHeaders, infoFooters),
		getReceiptMapAsTaggedList(receiptsDataOfTaxpayer, receiptHeaders, receiptFooters));
    }
    
    private static List<String> getTaggedInfoData(List<String> taxpayerInfoData,List<String> infoHeaders,
	    List<String> infoFooters) {
  	return tagData(taxpayerInfoData, infoHeaders, infoFooters);
    }
    
    private static List<String> getReceiptMapAsTaggedList(Map<Integer, List<String>> receiptsDataMap,
	    List<String> receiptHeaders, List<String> receiptFooters) {
	final int RECEIPT_DECLERATION = 0 ;
	List<String> justReceiptHeaders =  receiptHeaders.subList(1, receiptHeaders.size());	//exclude receipts decleration
	List<String> justReceiptFooters =  receiptFooters.subList(1, receiptHeaders.size());
	List<String> taggedReceipts = new ArrayList<String>();
	taggedReceipts.add("");
	taggedReceipts.add(receiptHeaders.get(RECEIPT_DECLERATION));
	taggedReceipts.add("");
	for (Integer id : receiptsDataMap.keySet()) {
	    List<String> receiptData = receiptsDataMap.get(id); 
	    taggedReceipts.addAll(tagData(receiptData, justReceiptHeaders,justReceiptFooters));
	    taggedReceipts.add("");
	}
	taggedReceipts.add(receiptFooters.get(RECEIPT_DECLERATION));
	return taggedReceipts;
    }
    
    private static List<String> joinTwoList(List<String> infoData, List<String> receiptsData){
	List<String> allTaxpayerData = new ArrayList<String>(infoData);
	allTaxpayerData.addAll(receiptsData);
	return allTaxpayerData;
    }
   
        
}
