package incometaxcalculator.data.management;

public enum TaxpayerFields {
    NAME(0),
    AFM(1),
    STATUS(2),
    INCOME(3);
    private final int value;

    TaxpayerFields(final int newValue) {
        value = newValue;
    }

    public int getValue() {
	return value; 
    }
    
}
