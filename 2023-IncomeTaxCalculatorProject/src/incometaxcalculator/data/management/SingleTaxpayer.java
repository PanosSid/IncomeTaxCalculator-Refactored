package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer {

    public SingleTaxpayer(String fullname, int taxRegistrationNumber, float income) {
	super(fullname, taxRegistrationNumber, income);
	incomeUpperLimit[0] = 24680;
	incomeUpperLimit[1] = 81080;
	incomeUpperLimit[2] = 90000;
	incomeUpperLimit[3] = 152540;
	
	correspondingTax[0] = 0;
	correspondingTax[1] = 1320.38;
	correspondingTax[2] = 5296.58;
	correspondingTax[3] = 5996.80;
	correspondingTax[4] = 10906.19;
	
	taxPercentage[0] = 0.0535;
	taxPercentage[1] = 0.0705;
	taxPercentage[2] = 0.0785;
	taxPercentage[3] = 0.0785;
	taxPercentage[4] = 0.0985;
    }

}