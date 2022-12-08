package incometaxcalculator.data.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import incometaxcalculator.data.management.TaxpayerCategory;
import incometaxcalculator.tags.FileTags;
import incometaxcalculator.tags.TXTTag;
import incometaxcalculator.tags.Tag;

public class AppConfig {
//    private static AppConfig instance;
    private String paramFileNamePath;
    private static List<String> receiptKinds;
    public Map<String ,TaxpayerCategory> taxpayerCategoriesMap;
    public Map<String, FileTags> fileTagsMap;


    public AppConfig() {
	receiptKinds = new ArrayList<String>();
//	taxpayerCategories = new ArrayList<TaxpayerCategory>();
	taxpayerCategoriesMap = new HashMap<String ,TaxpayerCategory>();
	String projectDir = System.getProperty("user.dir");
	paramFileNamePath = projectDir+"\\resources\\appSettings.txt";
//	tagFileNamePath = projectDir + "\\resources\\tagsProperties.txt";
	loadSettingsFile(new File(paramFileNamePath));
    }

    public static List<String> getReceiptKinds() {
//	if(receiptKinds == null || receiptKinds.isEmpty()){
//	    loadSettingsFile(new File(paramFileNamePath));
//	}
	return receiptKinds;
    }
    
    public void initFileTags() {
//	fileTagsMap = new HashMap<String, FileTags>();
//	List<Tag> infoTags = new ArrayList<Tag>();
//	infoTags.add(new TXTTag("Name", "Name"));
//	infoTags.add(new TXTTag("TRN", "AFM"));
//	infoTags.add(new TXTTag("Status", "Status"));
//	infoTags.add(new TXTTag("Income", "Income"));
//	
//	List<Tag> receiptTags = new ArrayList<Tag>();
//	receiptTags.add(new TXTTag("ReceiptsDecleration","Receipts"));
//	receiptTags.add(new TXTTag("Receipt ID","Receipt ID"));
//	receiptTags.add(new TXTTag("Date","Date"));
//	receiptTags.add(new TXTTag("Kind","Kind"));
//	receiptTags.add(new TXTTag("Amount","Amount"));
//	receiptTags.add(new TXTTag("Company","Company"));
//	receiptTags.add(new TXTTag("Country","Country"));
//	receiptTags.add(new TXTTag("City","City"));
//	receiptTags.add(new TXTTag("Street","Street"));
//	receiptTags.add(new TXTTag("Number","Number"));
//	
//	List<Tag> logTags = new ArrayList<Tag>();
//	logTags.add(new TXTTag("","Basic Tax"));
//	logTags.add(new TXTTag("","Tax Increase"));
//	logTags.add(new TXTTag("","Total Tax"));
//	logTags.add(new TXTTag("","TotalReceiptsGathered"));
//	logTags.add(new TXTTag("","Entertainment"));
//	logTags.add(new TXTTag("","Basic"));
//	logTags.add(new TXTTag("","Travel"));
//	logTags.add(new TXTTag("","Health"));
//	logTags.add(new TXTTag("","Other"));
    }
    

   
    public TaxpayerCategory getTaxpayerCategoryByName(String name) {
	return taxpayerCategoriesMap.get(name);
    }

    private void loadSettingsFile(File file){
	try {
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
		    String line = scanner.nextLine();
		    if (line.startsWith("@")) {
			TaxpayerCategory tc = getTaxpayerCategory(line, scanner);
//			taxpayerCategories.add(getTaxpayerCategory(line, scanner));
			taxpayerCategoriesMap.put(tc.getCategoryName(), tc);
		    } else if (line.startsWith("*")) {
			receiptKinds.add(getReceiptKindFromLine(line));
		    }
		}

		scanner.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
    }
    


    private String getReceiptKindFromLine(String line) {
	return line.replace("*", "").trim();
    }

    private TaxpayerCategory getTaxpayerCategory(String line, Scanner scanner) {
	String type = line.replace("@", "").trim();
	double incomeUpperLimit[] = null;	
	double correspondingTax[] = null;
	double taxPercentage[] = null;
	int loadedTables = 0;
	while(scanner.hasNextLine() && loadedTables < 3) {
	    String newline = scanner.nextLine().trim();
	    if (newline.length() > 0) {
		String splittedLine[] =  newline.split("=");
		String constantType = splittedLine[0].trim();
		String values = splittedLine[1];
		if (constantType.equals("income limits")) {
		    incomeUpperLimit = getNumbersFromLine(values);
		    loadedTables++;
		} else if (constantType.equals("base tax")) {
		    correspondingTax = getNumbersFromLine(values);
		    loadedTables++;
		} else if (constantType.equals("percentage")) {
		    taxPercentage = getNumbersFromLine(values);
		    loadedTables++;
		}
	    }
	}
	return new TaxpayerCategory(type, incomeUpperLimit, correspondingTax, taxPercentage );
    }

    private double[] getNumbersFromLine(String values) {
	String valuesArray[] = values.split(";");
	double retValues[] = new double[valuesArray.length];
	for (int i = 0; i < valuesArray.length; i++) {
	    retValues[i] = Double.parseDouble(valuesArray[i].trim());
	}
	return retValues;
    }
    
    

    public static void main(String args[]) {
	AppConfig app = new AppConfig();
//	System.out.println(app.getTaxpayerCategories());
	System.out.println(app.taxpayerCategoriesMap);
	System.out.println(app.getReceiptKinds());
	
    }
    

}
