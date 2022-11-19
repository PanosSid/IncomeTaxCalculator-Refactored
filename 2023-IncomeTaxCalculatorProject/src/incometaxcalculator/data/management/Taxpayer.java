package incometaxcalculator.data.management;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import incometaxcalculator.exceptions.WrongReceiptKindException;

public abstract class Taxpayer {

    protected final String fullname;
    protected final int taxRegistrationNumber;
    protected final float income;
    private float amountPerReceiptsKind[] = new float[5];
    private int totalReceiptsGathered = 0;
    private HashMap<Integer, Receipt> receiptHashMap = new HashMap<Integer, Receipt>(0);
    private static final short ENTERTAINMENT = 0;
    private static final short BASIC = 1;
    private static final short TRAVEL = 2;
    private static final short HEALTH = 3;
    private static final short OTHER = 4;

    public abstract double calculateBasicTax();

    protected Taxpayer(String fullname, int taxRegistrationNumber, float income) {
	this.fullname = fullname;
	this.taxRegistrationNumber = taxRegistrationNumber;
	this.income = income;
    }
    
    public String getFullname() {
	return fullname;
    }
    
    public int getTaxRegistrationNumber() {
	return taxRegistrationNumber;
    }
    
    public float getIncome() {
	return income;
    }
    
    public HashMap<Integer, Receipt> getReceiptHashMap() {
	return receiptHashMap;
    }

    public int getTotalReceiptsGathered() {
	return totalReceiptsGathered;
    }
    
    public float getAmountOfReceiptKind(short kind) {
	return amountPerReceiptsKind[kind];
    }
    
    public void addReceipt(Receipt receipt) throws WrongReceiptKindException {
	String receiptKinds[] = {"Entertainment", "Basic", "Travel", "Health", "Other"};
	for (int i = 0; i < receiptKinds.length; i++) {
	    String kindOfReceipt = receipt.getKind();
	    if (kindOfReceipt.equals(receiptKinds[i]))
		    amountPerReceiptsKind[i] += receipt.getAmount();
	}
	receiptHashMap.put(receipt.getId(), receipt);
	totalReceiptsGathered++;
    }

    public void removeReceipt(int receiptId) throws WrongReceiptKindException {
	Receipt receipt = receiptHashMap.get(receiptId);
	String receiptKinds[] = {"Entertainment", "Basic", "Travel", "Health", "Other"};	
	for (int i = 0; i < receiptKinds.length; i++) {
	    String kindOfReceipt = receipt.getKind();
	    if (kindOfReceipt.equals(receiptKinds[i]))
		amountPerReceiptsKind[i] -= receipt.getAmount();    
	}
	totalReceiptsGathered--;
	receiptHashMap.remove(receiptId);
    }

    public double getTotalTax() {
	return calculateBasicTax() + getVariationTaxOnReceipts();
    }
    
    public double getVariationTaxOnReceipts() {
	float totalAmountOfReceipts = getTotalAmountOfReceipts();
	double incomePercentages[] = {0.2, 0.4, 0.6};
	double taxFactor[] = {0.08, 0.04, -0.15};
	double taxFactorAboveSixtyPercent = -0.3;
	for (int i = 0; i <incomePercentages.length; i++) {
	    if (totalAmountOfReceipts <  income*incomePercentages[i])
		return calculateBasicTax() * taxFactor[i];
	}
	return calculateBasicTax()*taxFactorAboveSixtyPercent;
    }

    private float getTotalAmountOfReceipts() {	 
	int sum = 0;
	for (int i = 0; i < 5; i++) {	// MAGIC NUMBER !!!!
	    sum += amountPerReceiptsKind[i];
	}
	return sum;
    }

    public double getBasicTax() {
	return calculateBasicTax();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Taxpayer other = (Taxpayer) obj;
	return Arrays.equals(amountPerReceiptsKind, other.amountPerReceiptsKind)
		&& Objects.equals(fullname, other.fullname)
		&& Float.floatToIntBits(income) == Float.floatToIntBits(other.income)
		&& Objects.equals(receiptHashMap, other.receiptHashMap)
		&& taxRegistrationNumber == other.taxRegistrationNumber
		&& totalReceiptsGathered == other.totalReceiptsGathered;
    }

    @Override
    public String toString() {
	String s = "Taxpayer [fullname=" + fullname + ", taxRegistrationNumber=" + taxRegistrationNumber + ", income="
		+ income + ", amountPerReceiptsKind=" + Arrays.toString(amountPerReceiptsKind)
		+ ", totalReceiptsGathered=" + totalReceiptsGathered + ", receiptHashMap=" /* + receiptHashMap + "]" */;
	for (Integer id : receiptHashMap.keySet()) {
	    String key = id.toString();
	    String value = receiptHashMap.get(id).toString();
	    s += "(" + key + "-> " + value + ")";
	}
	return s;
    }

}