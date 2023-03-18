package incometaxcalculator.model;

import java.util.Arrays;
import java.util.Objects;

public class TaxpayerCategory {
	private String categoryName;
	private double incomeUpperLimit[];
	private double correspondingTax[];
	private double taxPercentage[];

	public TaxpayerCategory(String categoryName, double[] incomeUpperLimit, double[] correspondingTax,
			double[] taxPercentage) {
		super();
		this.categoryName = categoryName;
		this.incomeUpperLimit = incomeUpperLimit;
		this.correspondingTax = correspondingTax;
		this.taxPercentage = taxPercentage;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public double[] getIncomeUpperLimit() {
		return incomeUpperLimit;
	}

	public void setIncomeUpperLimit(double[] incomeUpperLimit) {
		this.incomeUpperLimit = incomeUpperLimit;
	}

	public double[] getCorrespondingTax() {
		return correspondingTax;
	}

	public void setCorrespondingTax(double[] correspondingTax) {
		this.correspondingTax = correspondingTax;
	}

	public double[] getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(double[] taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaxpayerCategory other = (TaxpayerCategory) obj;
		return Objects.equals(categoryName, other.categoryName)
				&& Arrays.equals(correspondingTax, other.correspondingTax)
				&& Arrays.equals(incomeUpperLimit, other.incomeUpperLimit)
				&& Arrays.equals(taxPercentage, other.taxPercentage);
	}

	@Override
	public String toString() {
		return "TaxpayerCategory [categoryName=" + categoryName + ", incomeUpperLimit="
				+ Arrays.toString(incomeUpperLimit) + ", correspondingTax=" + Arrays.toString(correspondingTax)
				+ ", taxPercentage=" + Arrays.toString(taxPercentage) + "]";
	}

}
