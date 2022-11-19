package acceptance;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.MarriedFilingJointlyTaxpayer;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.Taxpayer;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class AcceptanceTests {
    private TaxpayerManager taxpayerManager;
    private Taxpayer aTaxpayer;
    private Company company1;
    private int taxRegistrationNumber = 111111111;
    private String[] fileTypes = TestFileContents.fileTypes;
    private Map<String, File> testInfoFilesMap = new HashMap<String, File>();

    public AcceptanceTests() throws WrongReceiptDateException, WrongReceiptKindException, WrongTaxpayerStatusException {
	super();
	this.taxpayerManager = new TaxpayerManager();
	taxpayerManager.createTaxpayer("Robert Martin", 111111111, "Married Filing Jointly", (float) 100000.0);
	taxpayerManager.createReceipt(1, "10/10/2010", (float) 100.0, "Basic", "aCompany1", "aCountry1", "aCity1",
		"aStreet1", 10, 111111111);
	taxpayerManager.createReceipt(2, "20/2/2020", (float) 200.0, "Travel", "aCompany2", "aCountry2", "aCity2",
		"aStreet2", 20, 111111111);

	aTaxpayer = new MarriedFilingJointlyTaxpayer("Robert Martin", 111111111, (float) 100000.0);
	company1 = new Company("aCompany1", "aCountry1", "aCity1", "aStreet1", 10);
	Company company2 = new Company("aCompany2", "aCountry2", "aCity2", "aStreet2", 20);
	Receipt receipt1 = new Receipt(1, "10/10/2010", (float) 100.0, "Basic", company1);
	Receipt receipt2 = new Receipt(2, "20/2/2020", (float) 200.0, "Travel", company2);
	aTaxpayer.addReceipt(receipt1);
	aTaxpayer.addReceipt(receipt2);

	File testedInfoTxt = new File("111111111_INFO.txt");
	File testedInfoXml = new File("111111111_INFO.xml");
	testInfoFilesMap.put("txt", testedInfoTxt);
	testInfoFilesMap.put("xml", testedInfoXml);
    }

    @Before
    public void setUpInfoFileInResources() {
	String initialTxt = TestFileContents.getTestFileContents("txt", "initial");
	String initialXml = TestFileContents.getTestFileContents("xml", "initial");
	File testInfoTxt = new File("111111111_INFO.txt");
	File testInfoXml = new File("111111111_INFO.xml");
	overwriteFileContents(testInfoTxt, initialTxt);
	overwriteFileContents(testInfoXml, initialXml);
    }

    private void overwriteFileContents(File file, String newContent) {
	try {
	    FileWriter fileWriter = new FileWriter(file, false);
	    fileWriter.write(newContent);
	    fileWriter.close();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @AfterClass
    public static void tearDown() {
	try {
	    Files.deleteIfExists(Paths.get("111111111_LOG.txt"));
	    Files.deleteIfExists(Paths.get("111111111_LOG.xml"));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Test
    public void testUC1LoadTaxpayer()
	    throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException,
	    WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {

	for (String fType : fileTypes) {
	    String filename = taxRegistrationNumber + "_INFO." + fType;
	    taxpayerManager.loadTaxpayer(filename);
	    Taxpayer actualTaxpayer = taxpayerManager.getTaxpayer(taxRegistrationNumber);
	    Taxpayer expectedTaxpayer = aTaxpayer;
	    Assert.assertEquals(expectedTaxpayer, actualTaxpayer);

	}
    }

    @Test
    public void testUC2DisplayTaxpayerInfo() {
	fail("not implemented yet");
    }

    @Test
    public void testUC3AddReceiptToTaxpayer() throws WrongReceiptDateException, IOException, WrongReceiptKindException,
	    ReceiptAlreadyExistsException, InterruptedException {
	int receiptId = 3;
	String issueDate = "30/3/2003";
	float amount = (float) 300.0;
	String kind = "Basic";
	Receipt receipt3 = new Receipt(receiptId, issueDate, amount, kind, company1);
	String companyName = company1.getName();
	String country = company1.getCountry();
	String city = company1.getCity();
	String street = company1.getStreet();
	int number = company1.getNumber();

	taxpayerManager.addReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
		taxRegistrationNumber);

	HashMap<Integer, Receipt> receiptHashMap = taxpayerManager.getReceiptHashMap(taxRegistrationNumber);
	Receipt addedReceipt = receiptHashMap.get(receiptId);

	Assert.assertEquals(receipt3, addedReceipt);

	for (int i = 0; i < fileTypes.length; i++) {
	    String currentFType = fileTypes[i];
	    File testedFile = testInfoFilesMap.get(currentFType);
	    String actualInfoContents = FileUtils.readFileToString(testedFile, StandardCharsets.UTF_8);
	    String expectedInfoContents = TestFileContents.getTestFileContents(currentFType, "add");
	    Assert.assertEquals(expectedInfoContents, actualInfoContents);
	}
    }

    @Test
    public void testUC4DeleteReceiptOfTaxpayer() throws IOException, WrongReceiptKindException {
	taxpayerManager.removeReceipt(2);
	for (int i = 0; i < fileTypes.length; i++) {
	    String currentFType = fileTypes[i];
	    File testedFile = testInfoFilesMap.get(currentFType);
	    String actualInfoContents = FileUtils.readFileToString(testedFile, StandardCharsets.UTF_8);
	    String expectedInfoContents = TestFileContents.getTestFileContents(currentFType, "delete");
	    Assert.assertEquals(expectedInfoContents, actualInfoContents);
	}
    }

    @Test
    public void testUC6StoreTaxpayerLog() throws IOException, WrongFileFormatException {
	for (int i = 0; i < fileTypes.length; i++) {
	    String currentFtype = fileTypes[i];
	    taxpayerManager.saveLogFile(taxRegistrationNumber, currentFtype);
	    String logFilename = taxRegistrationNumber + "_LOG." + currentFtype;
	    File logFile = new File(logFilename);
	    boolean logFileExists = logFile.exists();
	    Assert.assertEquals(true, logFileExists);
	    String actualFileContents = FileUtils.readFileToString(logFile, StandardCharsets.UTF_8);
	    String expectedFileContents = TestFileContents.getTestFileContents(currentFtype, "log");
	    Assert.assertEquals(expectedFileContents, actualFileContents);
	}
    }

    @Test
    public void testUC7DeleteTaxpayerFromList() throws WrongTaxpayerStatusException {
	taxpayerManager.createTaxpayer("Will Deleted", 222222222, "Single", 30000);
	taxpayerManager.removeTaxpayer(222222222);
	Taxpayer returnedTaxpayer = taxpayerManager.getTaxpayer(222222222);
	Assert.assertEquals(null, returnedTaxpayer);
    }

    @Test
    public void testUC5CalculateTaxCharts() {
	double expectedBasicTax = 6436.64;
	double expectedTaxIncrease = 514.9312;
	double expectedTotalTax = expectedBasicTax + expectedTaxIncrease;

	double actualBasicTax = taxpayerManager.getTaxpayerBasicTax(taxRegistrationNumber);
	double actualTaxIncrease = taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber);
	double actualTotalTax = taxpayerManager.getTaxpayerTotalTax(taxRegistrationNumber);

	Assert.assertEquals(expectedBasicTax, actualBasicTax, 0.0);
	Assert.assertEquals(expectedTaxIncrease, actualTaxIncrease, 0.0);
	Assert.assertEquals(expectedTotalTax, actualTotalTax, 0.0);

	double expectedReceiptAmount[] = { 0.0, 100.0, 200.0, 0.0, 0.0 };
	for (int receiptKind = 0; receiptKind < 5; receiptKind++) {
	    double actualReceiptAmount = taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber,
		    (short) receiptKind);
	    Assert.assertEquals(expectedReceiptAmount[receiptKind], actualReceiptAmount, 0.0);
	}

    }
}
