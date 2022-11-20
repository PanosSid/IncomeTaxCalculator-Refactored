package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {

    public MarriedFilingJointlyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
	super(fullname, taxRegistrationNumber, income);
	incomeUpperLimit[0] = 36080;
	incomeUpperLimit[1] = 90000;
	incomeUpperLimit[2] = 143350;
	incomeUpperLimit[3] = 254240;
	
	correspondingTax[0] = 0;
	correspondingTax[1] = 1930.28;
	correspondingTax[2] = 5731.64;
	correspondingTax[3] = 9492.82;
	correspondingTax[4] = 18197.69;
	
	taxPercentage[0] = 0.0535;
	taxPercentage[1] = 0.0705;
	taxPercentage[2] = 0.0705;
	taxPercentage[3] = 0.0785;
	taxPercentage[4] = 0.0985;	
    }

}