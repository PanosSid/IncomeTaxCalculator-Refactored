package acceptance;

public class TestFileContents {
    public static String[] fileFormats = { "txt", "xml" };

    public static final String TXT_INITIAL_INFO = "Name: Robert Martin\r\n" + "AFM: 111111111\r\n"
	    + "Status: Married Filing Jointly\r\n" + "Income: 100000.0\r\n" + "\r\n" + "Receipts:\r\n" + "\r\n"
	    + "Receipt ID: 1\r\n" + "Date: 10/10/2010\r\n" + "Kind: Basic\r\n" + "Amount: 100.0\r\n"
	    + "Company: aCompany1\r\n" + "Country: aCountry1\r\n" + "City: aCity1\r\n" + "Street: aStreet1\r\n"
	    + "Number: 10\r\n" + "\r\n" + "Receipt ID: 2\r\n" + "Date: 20/2/2020\r\n" + "Kind: Travel\r\n"
	    + "Amount: 200.0\r\n" + "Company: aCompany2\r\n" + "Country: aCountry2\r\n" + "City: aCity2\r\n"
	    + "Street: aStreet2\r\n" + "Number: 20\r\n" + "\r\n" + "";

    public static final String TXT_AFTER_ADD_INFO = "Name: Robert Martin\r\n" + "AFM: 111111111\r\n"
	    + "Status: Married Filing Jointly\r\n" + "Income: 100000.0\r\n" + "\r\n" + "Receipts:\r\n" + "\r\n"
	    + "Receipt ID: 1\r\n" + "Date: 10/10/2010\r\n" + "Kind: Basic\r\n" + "Amount: 100.0\r\n"
	    + "Company: aCompany1\r\n" + "Country: aCountry1\r\n" + "City: aCity1\r\n" + "Street: aStreet1\r\n"
	    + "Number: 10\r\n" + "\r\n" + "Receipt ID: 2\r\n" + "Date: 20/2/2020\r\n" + "Kind: Travel\r\n"
	    + "Amount: 200.0\r\n" + "Company: aCompany2\r\n" + "Country: aCountry2\r\n" + "City: aCity2\r\n"
	    + "Street: aStreet2\r\n" + "Number: 20\r\n" + "\r\n" + "Receipt ID: 3\r\n" + "Date: 30/3/2003\r\n"
	    + "Kind: Basic\r\n" + "Amount: 300.0\r\n" + "Company: aCompany1\r\n" + "Country: aCountry1\r\n"
	    + "City: aCity1\r\n" + "Street: aStreet1\r\n" + "Number: 10\r\n" + "\r\n" + "";

    public static final String TXT_AFTER_DEL_INFO = "Name: Robert Martin\r\n" + "AFM: 111111111\r\n"
	    + "Status: Married Filing Jointly\r\n" + "Income: 100000.0\r\n" + "\r\n" + "Receipts:\r\n" + "\r\n"
	    + "Receipt ID: 1\r\n" + "Date: 10/10/2010\r\n" + "Kind: Basic\r\n" + "Amount: 100.0\r\n"
	    + "Company: aCompany1\r\n" + "Country: aCountry1\r\n" + "City: aCity1\r\n" + "Street: aStreet1\r\n"
	    + "Number: 10\r\n" + "\r\n" + "";

    public static final String TXT_LOG = "Name: Robert Martin\r\n" + "AFM: 111111111\r\n" + "Income: 100000.0\r\n"
	    + "Basic Tax: 6436.64\r\n" + "Tax Increase: 514.9312\r\n" + "Total Tax: 6951.5712\r\n"
	    + "TotalReceiptsGathered: 2\r\n" + "Entertainment: 0.0\r\n" + "Basic: 100.0\r\n" + "Travel: 200.0\r\n"
	    + "Health: 0.0\r\n" + "Other: 0.0";

    public static final String TXT_LOG_AFTER_ADD = "Name: Robert Martin\r\n" + "AFM: 111111111\r\n"
	    + "Income: 100000.0\r\n" + "Basic Tax: 6436.64\r\n" + "Tax Increase: 514.9312\r\n"
	    + "Total Tax: 6951.5712\r\n" + "TotalReceiptsGathered: 3\r\n" + "Entertainment: 0.0\r\n"
	    + "Basic: 400.0\r\n" + "Travel: 200.0\r\n" + "Health: 0.0\r\n" + "Other: 0.0\r\n" + "";

    public static final String XML_INITIAL_INFO = "<Name> Robert Martin </Name>\r\n" + "<AFM> 111111111 </AFM>\r\n"
	    + "<Status> Married Filing Jointly </Status>\r\n" + "<Income> 100000.0 </Income>\r\n" + "\r\n"
	    + "<Receipts>\r\n" + "\r\n" + "<ReceiptID> 1 </ReceiptID>\r\n" + "<Date> 10/10/2010 </Date>\r\n"
	    + "<Kind> Basic </Kind>\r\n" + "<Amount> 100.0 </Amount>\r\n" + "<Company> aCompany1 </Company>\r\n"
	    + "<Country> aCountry1 </Country>\r\n" + "<City> aCity1 </City>\r\n" + "<Street> aStreet1 </Street>\r\n"
	    + "<Number> 10 </Number>\r\n" + "\r\n" + "<ReceiptID> 2 </ReceiptID>\r\n" + "<Date> 20/2/2020 </Date>\r\n"
	    + "<Kind> Travel </Kind>\r\n" + "<Amount> 200.0 </Amount>\r\n" + "<Company> aCompany2 </Company>\r\n"
	    + "<Country> aCountry2 </Country>\r\n" + "<City> aCity2 </City>\r\n" + "<Street> aStreet2 </Street>\r\n"
	    + "<Number> 20 </Number>\r\n" + "\r\n" + "</Receipts>";

    public static final String XML_AFTER_ADD_INFO = "<Name> Robert Martin </Name>\r\n" + "<AFM> 111111111 </AFM>\r\n"
	    + "<Status> Married Filing Jointly </Status>\r\n" + "<Income> 100000.0 </Income>\r\n" + "\r\n"
	    + "<Receipts>\r\n" + "\r\n" + "<ReceiptID> 1 </ReceiptID>\r\n" + "<Date> 10/10/2010 </Date>\r\n"
	    + "<Kind> Basic </Kind>\r\n" + "<Amount> 100.0 </Amount>\r\n" + "<Company> aCompany1 </Company>\r\n"
	    + "<Country> aCountry1 </Country>\r\n" + "<City> aCity1 </City>\r\n" + "<Street> aStreet1 </Street>\r\n"
	    + "<Number> 10 </Number>\r\n" + "\r\n" + "<ReceiptID> 2 </ReceiptID>\r\n" + "<Date> 20/2/2020 </Date>\r\n"
	    + "<Kind> Travel </Kind>\r\n" + "<Amount> 200.0 </Amount>\r\n" + "<Company> aCompany2 </Company>\r\n"
	    + "<Country> aCountry2 </Country>\r\n" + "<City> aCity2 </City>\r\n" + "<Street> aStreet2 </Street>\r\n"
	    + "<Number> 20 </Number>\r\n" + "\r\n" + "<ReceiptID> 3 </ReceiptID>\r\n" + "<Date> 30/3/2003 </Date>\r\n"
	    + "<Kind> Basic </Kind>\r\n" + "<Amount> 300.0 </Amount>\r\n" + "<Company> aCompany1 </Company>\r\n"
	    + "<Country> aCountry1 </Country>\r\n" + "<City> aCity1 </City>\r\n" + "<Street> aStreet1 </Street>\r\n"
	    + "<Number> 10 </Number>\r\n" + "\r\n" + "</Receipts>";

    public static final String XML_AFTER_DEL_INFO = "<Name> Robert Martin </Name>\r\n" + "<AFM> 111111111 </AFM>\r\n"
	    + "<Status> Married Filing Jointly </Status>\r\n" + "<Income> 100000.0 </Income>\r\n" + "\r\n"
	    + "<Receipts>\r\n" + "\r\n" + "<ReceiptID> 1 </ReceiptID>\r\n" + "<Date> 10/10/2010 </Date>\r\n"
	    + "<Kind> Basic </Kind>\r\n" + "<Amount> 100.0 </Amount>\r\n" + "<Company> aCompany1 </Company>\r\n"
	    + "<Country> aCountry1 </Country>\r\n" + "<City> aCity1 </City>\r\n" + "<Street> aStreet1 </Street>\r\n"
	    + "<Number> 10 </Number>\r\n" + "\r\n" + "</Receipts>";

    public static final String XML_LOG = "<Name> Robert Martin </Name>\r\n" + "<AFM> 111111111 </AFM>\r\n"
	    + "<Income> 100000.0 </Income>\r\n" + "<BasicTax> 6436.64 </BasicTax>\r\n"
	    + "<TaxIncrease> 514.9312 </TaxIncrease>\r\n" + "<TotalTax> 6951.5712 </TotalTax>\r\n"
	    + "<Receipts> 2 </Receipts>\r\n" + "<Entertainment> 0.0 </Entertainment>\r\n" + "<Basic> 100.0 </Basic>\r\n"
	    + "<Travel> 200.0 </Travel>\r\n" + "<Health> 0.0 </Health>\r\n" + "<Other> 0.0 </Other>";

    public static final String XML_LOG_AFTER_ADD = "<Name> Robert Martin </Name>\r\n" + "<AFM> 111111111 </AFM>\r\n"
	    + "<Income> 100000.0 </Income>\r\n" + "<BasicTax> 6436.64 </BasicTax>\r\n"
	    + "<TaxIncrease> 514.9312 </TaxIncrease>\r\n" + "<TotalTax> 6951.5712 </TotalTax>\r\n"
	    + "<Receipts> 3 </Receipts>\r\n" + "<Entertainment> 0.0 </Entertainment>\r\n" + "<Basic> 400.0 </Basic>\r\n"
	    + "<Travel> 200.0 </Travel>\r\n" + "<Health> 0.0 </Health>\r\n" + "<Other> 0.0 </Other>\r\n" + "";

    // TODO EDO NA ALLAKSO TA RETURN NULL
    public static String getTestFileContents(String fileType, String operation) {
	if (fileType.equals("txt")) {
	    if (operation.equals("initial")) {
		return TXT_INITIAL_INFO;
	    } else if (operation.equals("add")) {
		return TXT_AFTER_ADD_INFO;
	    } else if (operation.equals("delete")) {
		return TXT_AFTER_DEL_INFO;
	    } else if (operation.equals("log")) {
		return TXT_LOG;
	    } else if (operation.equals("log_add")) {
		return TXT_LOG_AFTER_ADD;
	    } else {
//		throw new Exception("Operation listed is not implemented");
		return null;
	    }
	} else if (fileType.equals("xml")) {
	    if (operation.equals("initial")) {
		return XML_INITIAL_INFO;
	    } else if (operation.equals("add")) {
		return XML_AFTER_ADD_INFO;
	    } else if (operation.equals("delete")) {
		return XML_AFTER_DEL_INFO;
	    } else if (operation.equals("log")) {
		return XML_LOG;
	    } else if (operation.equals("log_add")) {
		return XML_LOG_AFTER_ADD;
	    } else {
		// throw new Exception("Operation listed is not implemented");
		return null;
	    }
	} else {
	    return null;
	}

    }


}
