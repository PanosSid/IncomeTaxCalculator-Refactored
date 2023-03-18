package incometaxcalculator.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import incometaxcalculator.io.exceptions.WrongReceiptDateException;
import incometaxcalculator.io.exceptions.WrongReceiptKindException;
import incometaxcalculator.model.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.model.exceptions.TaxpayerAlreadyLoadedException;

public class TaxpayerManager implements ITaxpayerManager {
	private Map<Integer, Taxpayer> taxpayerHashMap;
	private Map<String, TaxpayerCategory> taxpayerCategoriesMap;

	public TaxpayerManager() {
		taxpayerHashMap = new LinkedHashMap<Integer, Taxpayer>(0); // must be linked to maintain order for gui
		initTaxpayerCategories();
	}

	public Map<Integer, Taxpayer> getTaxpayerHashMap() {
		return taxpayerHashMap;
	}

	public void setTaxpayerCategoriesMap(Map<String, TaxpayerCategory> map) {
		taxpayerCategoriesMap = map;
	}

	@Override
	public void laodTaxpayerInfo(int trn, Map<String, List<String>> infoFileContents)
			throws TaxpayerAlreadyLoadedException, WrongReceiptDateException, WrongReceiptKindException {
		loadTaxpayerData(infoFileContents.get("taxpayerInfo"));
		infoFileContents.remove("taxpayerInfo");
		loadReceiptsData(trn, infoFileContents);
	}

	private void loadTaxpayerData(List<String> taxpayerInfoData) throws TaxpayerAlreadyLoadedException {
		String fullname = taxpayerInfoData.get(0);
		int taxRegNum = Integer.parseInt(taxpayerInfoData.get(1));
		String status = taxpayerInfoData.get(2);
		float income = Float.parseFloat(taxpayerInfoData.get(3));
		if (taxpayerHashMap.containsKey(taxRegNum)) {
			throw new TaxpayerAlreadyLoadedException();
		} else {
			TaxpayerCategory category = taxpayerCategoriesMap.get(status);
			Taxpayer taxpayer = new Taxpayer(fullname, taxRegNum, income, category);
			taxpayerHashMap.put(taxRegNum, taxpayer);
		}
	}

	private void loadReceiptsData(int taxRegNum, Map<String, List<String>> receiptsMap)
			throws WrongReceiptDateException, WrongReceiptKindException {
		for (String strId : receiptsMap.keySet()) {
			List<String> receiptData = receiptsMap.get(strId);
			int receiptId = Integer.parseInt(receiptData.get(0));
			String issueDate = receiptData.get(1);
			String kind = receiptData.get(2);
			float amount = Float.parseFloat(receiptData.get(3));
			String companyName = receiptData.get(4);
			String country = receiptData.get(5);
			String city = receiptData.get(6);
			String street = receiptData.get(7);
			int number = Integer.parseInt(receiptData.get(8));
			Company company = new Company(companyName, country, city, street, number);
			Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, company);
			Taxpayer taxpayer = taxpayerHashMap.get(taxRegNum);
			taxpayer.addReceipt(receipt);
		}
	}

	@Override
	public void removeTaxpayer(int taxRegNum) {
		taxpayerHashMap.remove(taxRegNum);
	}

	@Override
	public void addReceiptToTaxapyer(int receiptId, String issueDate, float amount, String kind, String companyName,
			String country, String city, String street, int number, int taxRegNum)
			throws WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {
		Receipt receipt = createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number);
		if (containsReceipt(taxRegNum, receipt.getId())) {
			throw new ReceiptAlreadyExistsException();
		}
		taxpayerHashMap.get(taxRegNum).addReceipt(receipt);
	}

	private Receipt createReceipt(int receiptId, String issueDate, float amount, String kind, String companyName,
			String country, String city, String street, int number) throws WrongReceiptDateException {
		Company company = new Company(companyName, country, city, street, number);
		return new Receipt(receiptId, issueDate, amount, kind, company);
	}

	private boolean containsReceipt(int taxRegNum, int receiptid) {
		return taxpayerHashMap.get(taxRegNum).hasReceiptId(receiptid);
	}

	@Override
	public void deleteReceiptFromTaxpayer(int receiptId, int trn) throws IOException {
		taxpayerHashMap.get(trn).removeReceipt(receiptId);
	}

	@Override
	public Taxpayer getTaxpayer(int taxRegNum) {
		return taxpayerHashMap.get(taxRegNum);
	}

	public String[] getLastLoadedTaxpayerNameAndTrn() {
		int numberOfloadedTaxpayers = taxpayerHashMap.size();
		List<Integer> lKeys = new ArrayList<Integer>(taxpayerHashMap.keySet());
		Integer lastTrn = lKeys.get(numberOfloadedTaxpayers - 1);
		String lastName = taxpayerHashMap.get(lastTrn).getFullname();
		String[] nameAndTrn = { lastName, "" + lastTrn };
		return nameAndTrn;
	}

	public List<String> getTaxpayerInfoData(int taxRegNum) {
		return taxpayerHashMap.get(taxRegNum).getTaxpayerInfoData();
	}

	@Override
	public Map<Integer, List<String>> getReceiptsDataOfTaxpayer(int taxRegNum) {
		return taxpayerHashMap.get(taxRegNum).getReceiptsDataOfTaxpayer();
	}

	public boolean isTaxIncreasingForTaxpayer(int taxRegNum) {
		if (getTaxpayer(taxRegNum).getVariationTaxOnReceipts() > 0)
			return true;
		return false;
	}

	private void initTaxpayerCategories() {
		TaxpayerCategoryLoader categoryLoader = new TaxpayerCategoryLoader();
		try {
			taxpayerCategoriesMap = categoryLoader.getTaxapayerCategoreis();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public TaxpayerCategory getTaxpayerCategoryByName(String categoryName) {
		return taxpayerCategoriesMap.get(categoryName);
	}

}