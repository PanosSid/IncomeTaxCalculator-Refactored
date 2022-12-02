package incometaxcalculator.data.io;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import incometaxcalculator.data.management.TaxpayerManager;

public class XMLLogWriter extends LogWriter {

    public XMLLogWriter() {
	super();
    }

    @Override
    protected String getFileName(int taxRegistrationNumber) {
	return taxRegistrationNumber+"_LOG.xml";
    }
    
    @Override
    protected List<String> getLogTags() {
	List<String> xmlHeaderTags = new  ArrayList<String>(12);
	xmlHeaderTags.add("<Name> ");
	xmlHeaderTags.add("<AFM> ");
	xmlHeaderTags.add("<Income> ");
	xmlHeaderTags.add("<BasicTax> ");
	if (taxIncrease == true) {
	    xmlHeaderTags.add( "<TaxIncrease> ");
	} else {
	    xmlHeaderTags.add( "<TaxDecrease> ");
	}
	xmlHeaderTags.add("<TotalTax> ");
	xmlHeaderTags.add("<Receipts> ");
	xmlHeaderTags.add("<Entertainment> ");
	xmlHeaderTags.add("<Basic> ");
	xmlHeaderTags.add("<Travel> ");
	xmlHeaderTags.add("<Health> ");
	xmlHeaderTags.add("<Other> ");
	return xmlHeaderTags;                                                            
    }
    
    @Override
    protected String mergeTagWithData(String headerTag, String data) {
	String footerTag = converHeaderToFooterTag(headerTag);
	return headerTag + data + footerTag;
    }
    
    private String converHeaderToFooterTag(String header) {
	String tmpHeader = header.substring(1, header.length()-1);
	return " </"+tmpHeader;
    }

}
