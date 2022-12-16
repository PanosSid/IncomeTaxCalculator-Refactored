package incometaxcalculator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TaxpayerCategoryLoader {
    
    private String fileNamePath = System.getProperty("user.dir") + "\\resources\\appSettings.txt";
    
    public TaxpayerCategoryLoader() {};
    
    public TaxpayerCategoryLoader(String fileNamePath) {
	super();
	this.fileNamePath = fileNamePath;
    }

    public void setFileNamePath(String path) {
	fileNamePath = path;
    }

    public Map<String,TaxpayerCategory> getTaxapayerCategoreis() throws FileNotFoundException {	
	Scanner scanner = new Scanner(new File(fileNamePath));
	Map<String, TaxpayerCategory> taxpayerCategoriesMap = new HashMap<String, TaxpayerCategory>();
	while (scanner.hasNextLine()) {
	    String line = scanner.nextLine();
	    if (line.startsWith("@")) {
		TaxpayerCategory tc = getTaxpayerCategory(line, scanner);
		taxpayerCategoriesMap.put(tc.getCategoryName(), tc);
	    }
	}
	scanner.close();
	return taxpayerCategoriesMap;
    }

    private TaxpayerCategory getTaxpayerCategory(String line, Scanner scanner) {
	String type = line.replace("@", "").trim();
	double incomeUpperLimit[] = null;
	double correspondingTax[] = null;
	double taxPercentage[] = null;
	int loadedTables = 0;
	while (scanner.hasNextLine() && loadedTables < 3) {
	    String newline = scanner.nextLine().trim();
	    if (newline.length() > 0) {
		String splittedLine[] = newline.split("=");
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
	return new TaxpayerCategory(type, incomeUpperLimit, correspondingTax, taxPercentage);
    }

    private double[] getNumbersFromLine(String values) {
	String valuesArray[] = values.split(";");
	double retValues[] = new double[valuesArray.length];
	for (int i = 0; i < valuesArray.length; i++) {
	    retValues[i] = Double.parseDouble(valuesArray[i].trim());
	}
	return retValues;
    }
}
