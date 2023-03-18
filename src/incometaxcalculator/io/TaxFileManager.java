package incometaxcalculator.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxFileManager implements FileManager {
	private List<String> fileFormats;
	private Map<String, Tags> tagsMap;
	private Map<Integer, String> filePathsMap; // contains the paths of the info files
	// without their file format

	public TaxFileManager() {
		super();
		filePathsMap = new HashMap<Integer, String>();
		tagsMap = new HashMap<String, Tags>();
		TagLoader tagLoader = new TagLoader();
		tagsMap = tagLoader.getTagsFromFile();
		updateFileFormats();
	}

	public void setTagsMap(Map<String, Tags> map) {
		tagsMap = map;
		updateFileFormats();
	}

	private void updateFileFormats() {
		fileFormats = new ArrayList<String>();
		for (String format : tagsMap.keySet()) {
			fileFormats.add(format);
		}
	}

	public void addFilePathToMap(int trn, String filePath) {
		filePathsMap.put(trn, filePath);
	}

	@Override
	public Map<String, List<String>> readTaxpayerFromFile(String fileNamePath) throws Exception {
		String ending[] = fileNamePath.split("\\.");
		String fileFormat = ending[1];
		String filePathWithoutFileFormat = ending[0];
		int trn = getTaxRegNumFromFileNamePath(fileNamePath);
		filePathsMap.put(trn, filePathWithoutFileFormat);
		Tags genTags = tagsMap.get(fileFormat);
		TaxFileReader fileReader = new TaxFileReader(genTags.getInfoHeaders(), genTags.getInfoFooters(),
				genTags.getReceiptHeaders(), genTags.getReceiptFooters());
		return fileReader.readTaxpayerAndReceipts(fileNamePath, trn);
	}

	public int getTaxRegNumFromFileNamePath(String fileName) {
		String bySlash[] = fileName.split("\\\\");
		String ending = bySlash[bySlash.length - 1];
		ending.substring(0, 9);
		return Integer.parseInt(ending.substring(0, 9));
	}

	@Override
	public void removeTaxpayerFilePath(int taxRegNum) {
		filePathsMap.remove(taxRegNum);
	}

	@Override
	public void updateTaxpayerFiles(int taxRegNum, List<String> taxpayerInfoData,
			Map<Integer, List<String>> receiptsDataOfTaxpayer) throws IOException {
		for (int i = 0; i < fileFormats.size(); i++) {
			String fileNamePath = filePathsMap.get(taxRegNum) + "." + fileFormats.get(i);
			File infoFile = new File(fileNamePath);
			if (infoFile.exists()) {
				Tags genTags = tagsMap.get(fileFormats.get(i));
				List<String> allInfoData = DataTagger.getTaggedTaxpayerAsList(taxpayerInfoData, receiptsDataOfTaxpayer,
						genTags.getTaxpayerAllInfoTags());
				TaxFileWriter.writeTaggedData(fileNamePath, allInfoData);
			}
		}
	}

	@Override
	public void saveLogeFile(String filePath, String fileFormat, List<String> logData, boolean taxIncrease)
			throws IOException {
		String fileNamePath = filePath + fileFormat;
		Tags genTags = tagsMap.get(fileFormat);
		List<String> logTaggedData = DataTagger.tagData(logData, genTags.getLogHeaders(), genTags.getLogFooters());
		TaxFileWriter.writeTaggedData(fileNamePath, logTaggedData);
	}

	@Override
	public List<String> getFileFormats() {
		return fileFormats;
	}

	public void setFileMapEntry(int i, String string) {
		filePathsMap.put(i, string);
	}

}
