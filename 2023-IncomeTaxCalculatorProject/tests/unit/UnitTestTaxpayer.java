package unit;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import incometaxcalculator.data.management.*;

public class UnitTestTaxpayer {
    
    private static final double ERROR_MARGIN = 0.00000001;
    TaxpayerType taxpayerTypes[] = {
	    	TaxpayerType.MARREID_FILLING_JOINTLY,
	    	TaxpayerType.MARREID_FILLING_SEPERATLY,
	    	TaxpayerType.SINGLE,
	    	TaxpayerType.HEAD_OF_HOUSEHOLD
	    	};
        
    
    @Test
    public void testCalculateBasicTaxMarriedFilingJointly() {
	double income[] = {10000, 50000, 100000, 150000, 300000};
	double expectedBasicTax[] = {535.00, 2911.64, 6436.64, 10014.845, 22705.05};
	for (int i = 0; i < income.length; i++) {
	    double actualBasicTax = getBasicTax(TaxpayerType.MARREID_FILLING_JOINTLY, (float) income[i]);
	    Assert.assertEquals(expectedBasicTax[i], actualBasicTax, ERROR_MARGIN);
	}
    }
    
    @Test
    public void testCalculateBasicTaxMarriedFilingSeperatly() {
	double income[] = {10000, 50000, 80000, 100000, 150000};
	double expectedBasicTax[] = {535.00, 3218.32, 5399.88, 6969.88, 11352.48};
	for (int i = 0; i < income.length; i++) {
	    double actualBasicTax = getBasicTax(TaxpayerType.MARREID_FILLING_SEPERATLY, (float) income[i]);
	    Assert.assertEquals(expectedBasicTax[i], actualBasicTax, ERROR_MARGIN);
	}
    }
    
    @Test
    public void testCalculateBasicTaxSignleTaxpayer() {
	double income[] = {10000, 50000, 85000, 120000, 180000};
	double expectedBasicTax[] = { 535.00, 3105.44, 5604.3, 8351.8, 13611.0};
	for (int i = 0; i < income.length; i++) {
	    double actualBasicTax = getBasicTax(TaxpayerType.SINGLE, (float) income[i]);
	    Assert.assertEquals(expectedBasicTax[i], actualBasicTax, ERROR_MARGIN);
	}
    }
    
    @Test
    public void testCalculateBasicTaxHeadOfHouseHold() {
	double income[] = {10000, 50000, 100000, 150000, 250000};
	double expectedBasicTax[] = {535.00, 3008.375, 6533.38, 10255.59, 19063.695};
	for (int i = 0; i < income.length; i++) {
	    double actualBasicTax = getBasicTax(TaxpayerType.HEAD_OF_HOUSEHOLD, (float) income[i]);
	    Assert.assertEquals(expectedBasicTax[i], actualBasicTax, ERROR_MARGIN);
	}
    }

    private double getBasicTax(TaxpayerType taxpayerType, float income) {
//	Taxpayer taxpayer = null;
//	if (taxpayerType == TaxpayerType.MARREID_FILLING_JOINTLY) {
//	    taxpayer = new MarriedFilingJointlyTaxpayer("Test Name", 987654321, income);
//	} else if (taxpayerType == TaxpayerType.MARREID_FILLING_SEPERATLY) {
//	    taxpayer = new MarriedFilingSeparatelyTaxpayer("Test Name", 987654321, income);
//	} else if (taxpayerType == TaxpayerType.SINGLE) {
//	    taxpayer = new SingleTaxpayer("Test Name", 987654321, income);
//	} else if (taxpayerType == TaxpayerType.HEAD_OF_HOUSEHOLD) {
//	    taxpayer = new HeadOfHouseholdTaxpayer("Test Name", 987654321, income);
//	} else {
//	    // exception ?
//	}
//	return taxpayer.calculateBasicTax();
	return 0;
    }
    
}
