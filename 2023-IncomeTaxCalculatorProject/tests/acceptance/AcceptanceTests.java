package acceptance;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.MarriedFilingJointlyTaxpayer;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.Taxpayer;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;


public class AcceptanceTests {
    private TaxpayerManager taxpayerManager;
    private Taxpayer aTaxPayer;
    
    public AcceptanceTests() throws WrongReceiptDateException, WrongReceiptKindException {
	super();
	this.taxpayerManager = new TaxpayerManager();
	aTaxPayer = new MarriedFilingJointlyTaxpayer("Panos Sidiropoulos", 111111111, (float) 100000.0);
	Company company1 = new Company("aCompany1", "aCountry1", "aCity1", "aStreet1",  10);
	Company company2 = new Company("aCompany2", "aCountry2", "aCity2", "aStreet2",  20);
	Receipt receipt1 = new Receipt(1, "10/10/2010", (float) 100.0, "Basic", company1);
	Receipt receipt2 = new Receipt(2, "20/2/2020", (float) 200.0, "Travel", company2);
	aTaxPayer.addReceipt(receipt1);
	aTaxPayer.addReceipt(receipt2);
    } 
    
    @Test
    public void testLoadTaxpayer() {
	int trn = 111111111;
	String filename = trn+"_INFO.txt";
	try {
	    taxpayerManager.loadTaxpayer(filename);
	    Taxpayer taxpayer = taxpayerManager.getTaxpayer(trn);
	    System.out.println("expected : "+ aTaxPayer);
	    System.out.println("actual   : "+ taxpayer);
	    Assertions.assertEquals(aTaxPayer, taxpayer);
//	    fail("not impemented yet");
	    
	} catch (NumberFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (WrongFileFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (WrongFileEndingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (WrongTaxpayerStatusException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (WrongReceiptKindException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (WrongReceiptDateException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
  
}         
  