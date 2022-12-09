package incometaxcalculator.data.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import incometaxcalculator.tags.DataTagger;

public class InfoDataTagger extends DataTagger {
//    private List<String> infoHeaders;
//    private List<String> infoFooters;
//    private List<String> receiptHeaders;
//    private List<String> receiptFooters;
//    
//    
//    public List<String> getTaggedTaxpayerAsList(List<String> taxpayerInfoData, Map<Integer, List<String>> receiptsDataOfTaxpayer) {
//	return joinTwoList(getTaggedInfoData(taxpayerInfoData), getReceiptMapAsTaggedList(receiptsDataOfTaxpayer));
//    }
//    
//    private List<String> getTaggedInfoData(List<String> taxpayerInfoData) {
//  	return tagData(taxpayerInfoData, infoHeaders, infoFooters);
//    }
//    
//    private List<String> getReceiptMapAsTaggedList(Map<Integer, List<String>> receiptsDataMap) {
//	final int RECEIPT_DECLERATION = 0 ;
//	List<String> justReceiptHeaders =  receiptHeaders.subList(1, receiptHeaders.size());	//exclude receipts decleration
//	List<String> justReceiptFooters =  receiptFooters.subList(1, receiptHeaders.size());
//	List<String> taggedReceipts = new ArrayList<String>();
//	taggedReceipts.add("");
//	taggedReceipts.add(receiptHeaders.get(RECEIPT_DECLERATION));
//	taggedReceipts.add("");
//	for (Integer id : receiptsDataMap.keySet()) {
//	    List<String> receiptData = receiptsDataMap.get(id); 
//	    taggedReceipts.addAll(tagData(receiptData, justReceiptHeaders,justReceiptFooters));
//	    taggedReceipts.add("");
//	}
//	taggedReceipts.add(receiptFooters.get(RECEIPT_DECLERATION));
//	return taggedReceipts;
//    }
//    
//    private List<String> joinTwoList(List<String> infoData, List<String> receiptsData){
//	List<String> allTaxpayerData = new ArrayList<String>(infoData);
//	allTaxpayerData.addAll(receiptsData);
//	return allTaxpayerData;
//    }
    
}
