package incometaxcalculator.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import incometaxcalculator.io.exceptions.WrongReceiptDateException;
import incometaxcalculator.io.exceptions.WrongReceiptKindException;
import incometaxcalculator.model.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.model.exceptions.TaxpayerAlreadyLoadedException;

public interface ITaxpayerManager {

    void laodTaxpayerInfo(int trn, Map<String, List<String>> infoFileContents) // TODO renaming
	    throws TaxpayerAlreadyLoadedException, WrongReceiptDateException, WrongReceiptKindException;

    void removeTaxpayer(int taxRegNum);

    void addReceiptToTaxapyer(int receiptId, String issueDate, float amount, String kind, String companyName,
	    String country, String city, String street, int number, int taxRegNum)
	    throws WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException;

    void deleteReceiptFromTaxpayer(int receiptId, int trn) throws IOException;

    Taxpayer getTaxpayer(int taxRegNum);

    Map<Integer, List<String>> getReceiptsDataOfTaxpayer(int taxRegNum);

    List<String> getTaxpayerInfoData(int taxRegNum);

    boolean isTaxIncreasingForTaxpayer(int taxRegNum);

    String[] getLastLoadedTaxpayerNameAndTrn();

    Map<Integer, Taxpayer> getTaxpayerHashMap();

}