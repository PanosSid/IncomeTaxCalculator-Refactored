package incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {

    public MarriedFilingSeparatelyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
	super(fullname, taxRegistrationNumber, income);
	incomeUpperLimit[0] = 18040;
	incomeUpperLimit[1] = 71680;
	incomeUpperLimit[2] = 90000;
	incomeUpperLimit[3] = 127120;
	
	correspondingTax[0] = 0;
	correspondingTax[1] = 965.14;
	correspondingTax[2] = 4746.76;
	correspondingTax[3] = 6184.88;
	correspondingTax[4] = 9098.80;
	
	taxPercentage[0] = 0.0535;
	taxPercentage[1] = 0.0705;
	taxPercentage[2] = 0.0785;
	taxPercentage[3] = 0.0785;
	taxPercentage[4] = 0.0985;
    }

}