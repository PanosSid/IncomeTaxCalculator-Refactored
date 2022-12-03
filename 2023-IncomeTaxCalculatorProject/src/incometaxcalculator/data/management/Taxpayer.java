package incometaxcalculator.data.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import incometaxcalculator.data.config.AppConfig;
import incometaxcalculator.exceptions.WrongReceiptKindException;

public class Taxpayer {

    protected final String fullname;
    protected final int taxRegistrationNumber;
    protected final float income;
    private TaxpayerCategory taxpayerCategory;
    
    private Map<String, Float> amountPerReceiptsKind;
    
    private HashMap<Integer, Receipt> receiptHashMap = new HashMap<Integer, Receipt>(0);

    public Taxpayer(String fullname, int taxRegistrationNumber, float income, TaxpayerCategory taxpayerCategory) {
	this.fullname = fullname;
	this.taxRegistrationNumber = taxRegistrationNumber;
	this.income = income;
	this.taxpayerCategory = taxpayerCategory;
	initAmountPerReceiptKindMap();
    }
    
    private void initAmountPerReceiptKindMap() {
	List<String> receiptKinds = AppConfig.getReceiptKinds();
	amountPerReceiptsKind = new HashMap<String, Float>(receiptKinds.size());
	for (String kind : receiptKinds) {
	    amountPerReceiptsKind.put(kind, (float) 0.0);
	}
    }
    
    public List<String> getReceiptKinds(){
	List<String> receiptKinds = new ArrayList<String>();
	receiptKinds.addAll(amountPerReceiptsKind.keySet());
	return receiptKinds;	
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
    
    public TaxpayerCategory getTaxpayerCategory() {
	return taxpayerCategory;
    }
    
    public int getTotalReceiptsGathered() {
	return receiptHashMap.size();
    }
    
    public float getAmountOfReceiptKind(String kind) {
	return amountPerReceiptsKind.get(kind);
    }

    public double calculateBasicTax() {
	double incomeUpperLimit[] = taxpayerCategory.getIncomeUpperLimit();
	double taxPercentage[] = taxpayerCategory.getTaxPercentage();
	double correspondingTax[] =taxpayerCategory.getCorrespondingTax();
	
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
	receiptHashMap.put(receipt.getId(), receipt);
	String kind = receipt.getKind();	
	amountPerReceiptsKind.put(kind, amountPerReceiptsKind.get(kind)+ receipt.getAmount());
    }
    
    public void removeReceipt(int receiptId) {
	Receipt receipt = receiptHashMap.get(receiptId);
	String kind = receipt.getKind();
	amountPerReceiptsKind.put(kind, amountPerReceiptsKind.get(kind) - receipt.getAmount());
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
	for (String kind : amountPerReceiptsKind.keySet()) {
	    sum += amountPerReceiptsKind.get(kind);
	}
	return sum;
    }

    public double getBasicTax() {
	return calculateBasicTax();
    }
    
    public String getTaxpayerCategoryName() {
	return taxpayerCategory.getCategoryName();
    }
    
    public boolean hasReceiptId(int receiptId) {
	return receiptHashMap.containsKey(receiptId);
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
	return Objects.equals(amountPerReceiptsKind, other.amountPerReceiptsKind)
		&& Objects.equals(fullname, other.fullname)
		&& Float.floatToIntBits(income) == Float.floatToIntBits(other.income)
		&& Objects.equals(receiptHashMap, other.receiptHashMap)
		&& taxRegistrationNumber == other.taxRegistrationNumber
		&& Objects.equals(taxpayerCategory, other.taxpayerCategory);
    }

    @Override
    public String toString() {
	String s = "Taxpayer [fullname=" + fullname + ", taxRegistrationNumber=" + taxRegistrationNumber + ", income="
		+ income + ", amountPerReceiptsKind=" + amountPerReceiptsKind.toString()
		+ ", receiptHashMap=" /* + receiptHashMap + "]" */;
	for (Integer id : receiptHashMap.keySet()) {
	    String key = id.toString();
	    String value = receiptHashMap.get(id).toString();
	    s += "(" + key + "-> " + value + ")";
	}
	return s;
    }

}