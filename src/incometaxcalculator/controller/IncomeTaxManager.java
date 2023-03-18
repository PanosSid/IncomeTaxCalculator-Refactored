package incometaxcalculator.controller;

import java.io.IOException;
import java.util.List;

import incometaxcalculator.io.exceptions.WrongFileFormatException;
import incometaxcalculator.io.exceptions.WrongReceiptDateException;
import incometaxcalculator.io.exceptions.WrongReceiptKindException;
import incometaxcalculator.model.Taxpayer;
import incometaxcalculator.model.exceptions.ReceiptAlreadyExistsException;

public interface IncomeTaxManager {

	void loadTaxpayer(String fileName) throws Exception;

	void removeTaxpayer(int taxRegNum);

	void addReceiptToTaxpayer(int receiptId, String issueDate, float amount, String kind, String companyName,
			String country, String city, String street, int number, int taxRegNum)
			throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException;

	void deleteReceiptFromTaxpayer(int receiptId, int taxRegNum) throws IOException;

	void saveLogFile(int taxRegNum, String filePath, String fileFormat) throws IOException, WrongFileFormatException;

	Taxpayer getTaxpayer(int trn);

	List<String> getFileFormats();

	String[] getLastLoadedTaxpayerNameAndTrn();

}