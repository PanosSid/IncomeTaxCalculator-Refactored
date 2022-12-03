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

public class AppConfig {
//    private static AppConfig instance;
    private String paramFileNamePath;
    private String tagFileNamePath;
    private List<String> receiptKinds;
//    private List<TaxpayerCategory> taxpayerCategories;
    public Map<String ,TaxpayerCategory> taxpayerCategoriesMap;

    public AppConfig() {
	receiptKinds = new ArrayList<String>();
//	taxpayerCategories = new ArrayList<TaxpayerCategory>();
	taxpayerCategoriesMap = new HashMap<String ,TaxpayerCategory>();
	String projectDir = System.getProperty("user.dir");
	tagFileNamePath = projectDir + "\\resources\\tagsProperties.txt";
	paramFileNamePath = projectDir+"\\resources\\appSettings.txt";
	loadSettingsFile(new File(paramFileNamePath));
    }
    
//    public static AppConfig getInstance() {
//	if (instance == null) {
//	    instance = new AppConfig();
//	}
//	return instance;
//    }

    public List<String> getReceiptKinds() {
        return receiptKinds;
    }

//    public List<TaxpayerCategory> getTaxpayerCategories() {
//        return taxpayerCategories;
//    }
    
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
