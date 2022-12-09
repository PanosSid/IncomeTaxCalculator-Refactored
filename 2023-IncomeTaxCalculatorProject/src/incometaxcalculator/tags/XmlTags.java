package incometaxcalculator.tags;

import java.util.List;

public class XmlTags {
    public static String[] INFO_HEADERS = {
	    "<Name>",
	    "<AFM>",
	    "<Status>",
	    "<Income>",
	    };
    public static String[] INFO_FOOTERS = {
	    "</Name>",
	    "</AFM>",
	    "</Status>",
	    "</Income>",
	    };
    public static String[] RECEIPT_HEADERS =  {
	    "<Receipts>",
	    "<ReceiptID>",
	    "<Date>",
	    "<Kind>",
	    "<Amount>",
	    "<Company>",
	    "<Country>",
	    "<City>",
	    "<Street>",
	    "<Number>",
	    };
    public static String[] RECEIPT_FOOTERS = {
	    "</Receipts>",
	    "</ReceiptID>",
	    "</Date>",
	    "</Kind>",
	    "</Amount>",
	    "</Company>",
	    "</Country>",
	    "</City>",
	    "</Street>",
	    "</Number>",
	    };
    
    public static String[] LOG_HEADERS = {
	    "<Name>",
	    "<AFM>",
	    "<Income>",
	    "<BasicTax>",
	    "<TaxIncrease>",
	    "<TotalTax>",
	    "<Receipts>",
	    "<Entertainment>",
	    "<Basic>",
	    "<Travel>",
	    "<Health>",
	    "<Other>",
    } ;
    public static String[] LOG_FOOTERS = {
	    "</Name>",
	    "</AFM>",
	    "</Income>",
	    "</BasicTax>",
	    "</TaxIncrease>",
	    "</TotalTax>",
	    "</Receipts>",
	    "</Entertainment>",
	    "</Basic>",
	    "</Travel>",
	    "</Health>",
	    "</Other>",   
    };
    
	    
}
