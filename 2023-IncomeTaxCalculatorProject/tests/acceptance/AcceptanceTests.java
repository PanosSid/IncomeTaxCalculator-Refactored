package acceptance;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import incometaxcalculator.controller.MainManager;
import incometaxcalculator.gui.WrongTaxpayerStatusException;
import incometaxcalculator.io.exceptions.WrongFileFormatException;
import incometaxcalculator.io.exceptions.WrongReceiptDateException;
import incometaxcalculator.io.exceptions.WrongReceiptKindException;
import incometaxcalculator.model.Company;
import incometaxcalculator.model.Receipt;
import incometaxcalculator.model.Taxpayer;
import incometaxcalculator.model.TaxpayerManager;
import incometaxcalculator.model.exceptions.TaxpayerAlreadyLoadedException;

public class AcceptanceTests {
    
    private TaxpayerManager taxpayerManager;
    private MainManager mainManager = MainManager.getInstance();
    private int taxRegNum = 111111111;
    private String[] fileFormats = TestFileContents.fileFormats;	// TODO get from gloabl proin from app
    private Map<String, File> testInfoFilesMap = new HashMap<String, File>();
    private final static String TEST_RESOURCES_PATH = System.getProperty("user.dir") + "\\resources\\testing resources\\"; 
    

    public AcceptanceTests() throws WrongReceiptDateException, WrongReceiptKindException, WrongTaxpayerStatusException, TaxpayerAlreadyLoadedException {
	super();
	this.taxpayerManager = new TaxpayerManager(); /*TaxpayerManager.getInstance();*/
	taxpayerManager.getTaxpayerHashMap().put(taxRegNum, getExpectedTaxpayerID1());
//	taxpayerManager.getFilePathsMap().put(taxRegNum, TEST_RESOURCES_PATH+"111111111_INFO");
	mainManager.setTaxpayerManager(taxpayerManager);
	
	File testedInfoTxt = new File(TEST_RESOURCES_PATH+"111111111_INFO.txt");
	File testedInfoXml = new File(TEST_RESOURCES_PATH+"111111111_INFO.xml");
	testInfoFilesMap.put("txt", testedInfoTxt);
	testInfoFilesMap.put("xml", testedInfoXml);
    }

    @Before
    public void setUpInfoFileInResources() {
	String initialTxt = TestFileContents.getTestFileContents("txt", "initial");
	String initialXml = TestFileContents.getTestFileContents("xml", "initial");
	File testInfoFileTxt = new File(TEST_RESOURCES_PATH+"111111111_INFO.txt");
	File testInfoFileXml = new File(TEST_RESOURCES_PATH+"111111111_INFO.xml");
	overwriteFileContents(testInfoFileTxt, initialTxt);
	overwriteFileContents(testInfoFileXml, initialXml);
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

//    @After
//    public void destroyTaxpayerManager() {
//	TaxpayerManager.tearDownTaxpayerManager();
//    }
    
    @AfterClass
    public static void tearDown() {
	
	
//	try {
//	    Files.deleteIfExists(Paths.get(TEST_RESOURCES_PATH + "111111111_LOG.txt"));
//	    Files.deleteIfExists(Paths.get(TEST_RESOURCES_PATH + "111111111_LOG.xml"));
//	} catch (IOException e) {
//	    e.printStackTrace();
//	}
    }
    

    @Test
    public void testUC1LoadTaxpayer()
	    throws Exception {
//	MainManager mainManager = new MainManager();
	for (String fType : fileFormats) {
	    String filename = TEST_RESOURCES_PATH + taxRegNum + "_INFO." + fType;
	    try {
//		taxpayerManager.loadTaxpayer(filename);
		mainManager.loadTaxpayer(filename);
		
	    } catch (TaxpayerAlreadyLoadedException e) {
		// TODO fix this ugly hack
	    }
	    Taxpayer actualTaxpayer = taxpayerManager.getTaxpayer(taxRegNum);
	    Taxpayer expectedTaxpayer = getExpectedTaxpayerID1();
	    Assert.assertEquals(expectedTaxpayer, actualTaxpayer);
	}
    }
    
    private Taxpayer getExpectedTaxpayerID1() throws WrongReceiptDateException, WrongReceiptKindException {
//	Taxpayer aTaxpayer = new Taxpayer("Robert Martin", 111111111, (float) 100000.0, "Married Filing Jointly");
	Taxpayer aTaxpayer = new Taxpayer("Robert Martin", 111111111, (float) 100000.0, taxpayerManager.getTaxpayerCategoryByName("Married Filing Jointly"));
	Company company1 = new Company("aCompany1", "aCountry1", "aCity1", "aStreet1", 10);
	Company company2 = new Company("aCompany2", "aCountry2", "aCity2", "aStreet2", 20);
	Receipt receipt1 = new Receipt(1, "10/10/2010", (float) 100.0, "Basic", company1);
	Receipt receipt2 = new Receipt(2, "20/2/2020", (float) 200.0, "Travel", company2);
	aTaxpayer.addReceipt(receipt1);
	aTaxpayer.addReceipt(receipt2);
	return aTaxpayer;
    }
    
    @Ignore
//    @Test
    public void testUC2DisplayTaxpayerInfo() {
	fail("not implemented yet");
    }

    @Test
    public void testUC3AddReceiptToTaxpayer() throws Exception {
	int receiptId = 3;
	String issueDate = "30/3/2003";
	float amount = (float) 300.0;
	String kind = "Basic";
	Company company1 = new Company("aCompany1", "aCountry1", "aCity1", "aStreet1", 10);
	Receipt receipt3 = new Receipt(receiptId, issueDate, amount, kind, company1);
	String companyName = company1.getName();
	String country = company1.getCountry();
	String city = company1.getCity();
	String street = company1.getStreet();
	int number = company1.getNumber();
	
	mainManager.getTaxFileManager().addFilePathToMap(taxRegNum, TEST_RESOURCES_PATH+"111111111_INFO");
//	mainManager.loadTaxpayer(TEST_RESOURCES_PATH+"111111111_INFO"+".txt");
	
	mainManager.addReceiptToTaxpayer(receiptId, issueDate, amount, kind,
		companyName, country, city, street, number,taxRegNum);
//	taxpayerManager.addReceiptToTaxpayer(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
//		taxRegNum);

//	HashMap<Integer, Receipt> receiptHashMap = taxpayerManager.getReceiptHashMap(taxRegNum);
	Taxpayer taxpayer = taxpayerManager.getTaxpayer(taxRegNum);
	HashMap<Integer, Receipt> receiptHashMap = taxpayer.getReceiptHashMap();
	Receipt addedReceipt = receiptHashMap.get(receiptId);

	Assert.assertEquals(receipt3, addedReceipt);

	for (int i = 0; i < fileFormats.length; i++) {
	    String currentFType = fileFormats[i];
	    File testedFile = testInfoFilesMap.get(currentFType);
	    String actualInfoContents = FileUtils.readFileToString(testedFile, StandardCharsets.UTF_8);
	    String expectedInfoContents = TestFileContents.getTestFileContents(currentFType, "add");
	    Assert.assertEquals(expectedInfoContents, actualInfoContents);
	}
	
	mainManager.removeTaxpayer(taxRegNum);
    }

    @Test
    public void testUC4DeleteReceiptOfTaxpayer() throws Exception {
	mainManager.getTaxFileManager().addFilePathToMap(taxRegNum, TEST_RESOURCES_PATH+"111111111_INFO");
	mainManager.deleteReceiptFromTaxpayer(2, taxRegNum);
	for (int i = 0; i < fileFormats.length; i++) {
	    String currentFType = fileFormats[i];
	    File testedFile = testInfoFilesMap.get(currentFType);
	    String actualInfoContents = FileUtils.readFileToString(testedFile, StandardCharsets.UTF_8);
	    String expectedInfoContents = TestFileContents.getTestFileContents(currentFType, "delete");
	    Assert.assertEquals(expectedInfoContents, actualInfoContents);
	}
    }
    
    @Test
    public void testUC6StoreTaxpayerLog() throws IOException, WrongFileFormatException {
	for (int i = 0; i < fileFormats.length; i++) {
	    String currentFtype = fileFormats[i];
	    mainManager.saveLogFile(taxRegNum, TEST_RESOURCES_PATH,  fileFormats[i]);
//	    taxpayerManager.saveLogFile(taxRegNum,TEST_RESOURCES_PATH, currentFtype);
	    String logFilename = taxRegNum + "_LOG." + currentFtype;
	    File logFile = new File(TEST_RESOURCES_PATH+ logFilename);
	    boolean logFileExists = logFile.exists();
	    Assert.assertEquals(true, logFileExists);
	    String actualFileContents = FileUtils.readFileToString(logFile, StandardCharsets.UTF_8);
	    String expectedFileContents = TestFileContents.getTestFileContents(currentFtype, "log");
	    Assert.assertEquals(expectedFileContents, actualFileContents);
	    
	}
    }

    @Test
    public void testUC7RemoveTaxpayerFromList() throws WrongTaxpayerStatusException, TaxpayerAlreadyLoadedException {
	mainManager.getTaxpayerManger().getTaxpayerHashMap().put(333333333, new Taxpayer("Will Deleted", 333333333, 300000, null));
	mainManager.getTaxFileManager().setFileMapEntry(333333333, TEST_RESOURCES_PATH + "333333333_INFO");

	taxpayerManager.removeTaxpayer(222222222);
	Taxpayer returnedTaxpayer = taxpayerManager.getTaxpayer(222222222);
	Assert.assertEquals(null, returnedTaxpayer);
    }

    @Test
    public void testUC5CalculateTaxCharts() {
//	fail("not implemented yet");
	
	Taxpayer taxpayer = taxpayerManager.getTaxpayer(taxRegNum);
	
	double expectedBasicTax = 6436.64;
	double expectedTaxIncrease = 514.9312;
	double expectedTotalTax = expectedBasicTax + expectedTaxIncrease;

//	double actualBasicTax = taxpayerManager.getTaxpayerBasicTax(taxRegNum);
//	double actualTaxIncrease = taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegNum);
//	double actualTotalTax = taxpayerManager.getTaxpayerTotalTax(taxRegNum);
	
	double actualBasicTax = taxpayer.getBasicTax();
	double actualTaxIncrease = taxpayer.getVariationTaxOnReceipts();
	double actualTotalTax = taxpayer.getTotalTax();

	Assert.assertEquals(expectedBasicTax, actualBasicTax, 0.0);
	Assert.assertEquals(expectedTaxIncrease, actualTaxIncrease, 0.0);
	Assert.assertEquals(expectedTotalTax, actualTotalTax, 0.0);

	mainManager.getTaxpayerManger();
	
//	List<String> receiptKinds = AppConfig.getReceiptKinds();
//	for (String kind : receiptKinds) {
//	    double actualReceiptAmount = taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegNum,
//		    kind);
//	    Assert.assertEquals(expectedReceiptAmount, actualReceiptAmount, 0.0);
//	}
	double actualReceiptAmount = taxpayer.getAmountOfReceiptKind("Entertainment");
	Assert.assertEquals(0.0, actualReceiptAmount, 0.0);
	
	double actualReceiptAmount1 = taxpayer.getAmountOfReceiptKind("Basic");
	Assert.assertEquals(100.0, actualReceiptAmount1, 0.0);
	
	double actualReceiptAmount2 = taxpayer.getAmountOfReceiptKind("Travel");
	Assert.assertEquals(200.0, actualReceiptAmount2, 0.0);
	
	double actualReceiptAmount3 = taxpayer.getAmountOfReceiptKind("Health");
	Assert.assertEquals(0.0, actualReceiptAmount3, 0.0);

	double actualReceiptAmount4 = taxpayer.getAmountOfReceiptKind("Other");
	Assert.assertEquals(0.0, actualReceiptAmount4, 0.0);

	    
	
	
//	double expectedReceiptAmount[] = { 0.0, 100.0, 200.0, 0.0, 0.0 };
//	for (int receiptKind = 0; receiptKind < 5; receiptKind++) {
//	    double actualReceiptAmount = taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegNum,
//		    (short) receiptKind);
//	    Assert.assertEquals(expectedReceiptAmount[receiptKind], actualReceiptAmount, 0.0);
//	}

    }
}
