package incometaxcalculator.data.management;

public class ReceiptFields {
    public static final int RECEIPT_ID = 0;
    
//    RECEIPT_ID(0),
//    ISSUE_DATE(1),
//    AMOUNT(2),
//    KIND(3),
//    COMPANY_NAME(4),
//    COMPANY_COUNTRY(5),
//    COMPANY_CITY(6),
//    COMPANY_STREET(7),
//    COMPANY_STREET_NUMBER(8);
//    
    private final int value;

    ReceiptFields(final int newValue) {
        value = newValue;
    }

    public int getValue() {
	
	return value; 
    }
}
