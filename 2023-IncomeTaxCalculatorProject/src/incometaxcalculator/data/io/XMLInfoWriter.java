package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public class XMLInfoWriter extends InfoWriter {

    public XMLInfoWriter(TaxpayerManager taxpayerManager) {
	super(taxpayerManager);
    }
    
    @Override
    protected String getFileName(int taxRegistrationNumber) {
	return taxRegistrationNumber+"_INFO.xml";
    }
    
    @Override
    protected List<String> getInfoTags() {
	List<String> xmlInfoTags = new ArrayList<String>();
	xmlInfoTags.add("<Name> ");
	xmlInfoTags.add("<AFM> " );
	xmlInfoTags.add("<Status> ");
	xmlInfoTags.add("<Income> ");
	xmlInfoTags.add("");
	xmlInfoTags.add("<Receipts>");
	xmlInfoTags.add("");
	return xmlInfoTags;
    }
    
    @Override
    protected List<String> getReceiptTags() {
	List<String> xmlReceiptTags = new ArrayList<String>();
	xmlReceiptTags.add("<ReceiptID> ");
	xmlReceiptTags.add("<Date> " );
	xmlReceiptTags.add("<Kind> " );
	xmlReceiptTags.add("<Amount> "); 
	xmlReceiptTags.add("<Company> ");
	xmlReceiptTags.add("<Country> ");
	xmlReceiptTags.add("<City> ");
	xmlReceiptTags.add("<Street> "); 
	xmlReceiptTags.add("<Number> "); 
	xmlReceiptTags.add("");	
	return xmlReceiptTags;
    }

    @Override
    protected String mergeTagWithData(String headerTag, String data) {
	String footerTag = converHeaderToFooterTag(headerTag);
	return headerTag + data + footerTag;
    }
    
    private String converHeaderToFooterTag(String header) {
	if (header.length() > 0 && !header.equals("<Receipts>")) {
	    String tmpHeader = header.substring(1, header.length()-1);
	    return " </"+tmpHeader;	    
	}
	return "";
    }

    @Override
    protected void writeReciptFooter(PrintWriter outputStream) {
	outputStream.println("</Receipts>");
    }
}