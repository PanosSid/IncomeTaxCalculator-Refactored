package incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

    public HeadOfHouseholdTaxpayer(String fullname, int taxRegistrationNumber, float income) {
	super(fullname, taxRegistrationNumber, income);
	incomeUpperLimit[0] = 30390;
	incomeUpperLimit[1] = 90000;
	incomeUpperLimit[2] = 122440;
	incomeUpperLimit[3] = 203390;
	
	correspondingTax[0] = 0;
	correspondingTax[1] = 1625.87;
	correspondingTax[2] = 5828.38;
	correspondingTax[3] = 8092.13;
	correspondingTax[4] = 14472.61;
	
	taxPercentage[0] = 0.0535;
	taxPercentage[1] = 0.0705;
	taxPercentage[2] = 0.0705;
	taxPercentage[3] = 0.0785;
	taxPercentage[4] = 0.0985;
    }
}
