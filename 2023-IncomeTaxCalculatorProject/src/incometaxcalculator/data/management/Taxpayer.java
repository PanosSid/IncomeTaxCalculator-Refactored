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
    private HashMap<Integer, Receipt> receiptHashMap = new HashMap<Integer, Receipt>(0);
    double incomeUpperLimit[] = new double[4];	// MAGIC NUMBER ???
    double correspondingTax[] = new double[5];
    double taxPercentage[] = new double[5];


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
	return receiptHashMap.size();
    }
    
    public float getAmountOfReceiptKind(short kind) {
	return amountPerReceiptsKind[kind];
    }

    public double calculateBasicTax() {
	if (income < incomeUpperLimit[0]) {
	    return correspondingTax[0] + taxPercentage[0] * income;
	} else if (income < incomeUpperLimit[1]) {
	    return correspondingTax[1] + taxPercentage[1] * (income - incomeUpperLimit[0]);
	} else if (income < incomeUpperLimit[2]) {
	    return correspondingTax[2] + taxPercentage[2] * (income - incomeUpperLimit[1]);
	} else if (income < incomeUpperLimit[3]) {
	    return correspondingTax[3] + taxPercentage[3] * (income - incomeUpperLimit[2]);
	} else {
	    return correspondingTax[4] + taxPercentage[4] * (income - incomeUpperLimit[3]);
	}
    }
    
    
    public void addReceipt(Receipt receipt) throws WrongReceiptKindException {
	String receiptKinds[] = {"Entertainment", "Basic", "Travel", "Health", "Other"};
	for (int i = 0; i < receiptKinds.length; i++) {
	    String kindOfReceipt = receipt.getKind();
	    if (kindOfReceipt.equals(receiptKinds[i]))
		    amountPerReceiptsKind[i] += receipt.getAmount();
	}
	receiptHashMap.put(receipt.getId(), receipt);
    }

    public void removeReceipt(int receiptId) throws WrongReceiptKindException {
	Receipt receipt = receiptHashMap.get(receiptId);
	String receiptKinds[] = {"Entertainment", "Basic", "Travel", "Health", "Other"};	
	for (int i = 0; i < receiptKinds.length; i++) {
	    String kindOfReceipt = receipt.getKind();
	    if (kindOfReceipt.equals(receiptKinds[i]))
		amountPerReceiptsKind[i] -= receipt.getAmount();    
	}
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
		&& taxRegistrationNumber == other.taxRegistrationNumber;
//		&& totalReceiptsGathered == other.totalReceiptsGathered;
    }

    @Override
    public String toString() {
	String s = "Taxpayer [fullname=" + fullname + ", taxRegistrationNumber=" + taxRegistrationNumber + ", income="
		+ income + ", amountPerReceiptsKind=" + Arrays.toString(amountPerReceiptsKind)
		+ ", receiptHashMap=" /* + receiptHashMap + "]" */;
	for (Integer id : receiptHashMap.keySet()) {
	    String key = id.toString();
	    String value = receiptHashMap.get(id).toString();
	    s += "(" + key + "-> " + value + ")";
	}
	return s;
    }

}