package incometaxcalculator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class TaxpayerCategoryLoader {

    public void loadSettingsFile(Map<String,TaxpayerCategory> taxpayerCategoriesMap) throws FileNotFoundException {
//	List<String> receiptKinds = null;
	String projectDir = System.getProperty("user.dir");
	String fileNamePath = projectDir + "\\resources\\appSettings.txt";
	
	Scanner scanner = new Scanner(new File(fileNamePath));

	while (scanner.hasNextLine()) {
	String line = scanner.nextLine();
	if (line.startsWith("@")) {
	    TaxpayerCategory tc = getTaxpayerCategory(line, scanner);
	    taxpayerCategoriesMap.put(tc.getCategoryName(), tc);
	}
//		} else if (line.startsWith("*")) {
//		    receiptKinds.add(getReceiptKindFromLine(line));
//		}
	}

	scanner.close();
    }

//    private String getReceiptKindFromLine(String line) {
//	return line.replace("*", "").trim();
//    }

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
