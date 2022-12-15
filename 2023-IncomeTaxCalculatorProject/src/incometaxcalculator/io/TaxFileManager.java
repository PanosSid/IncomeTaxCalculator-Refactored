package incometaxcalculator.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxFileManager {
    private List<String> fileFormats;
    private Map<String, Tags> genTagsMap;
    private Map<Integer, String> filePathsMap; // contains the paths of the info files
					       // without their file format

    public TaxFileManager() {
	super();
	filePathsMap = new HashMap<Integer, String>();
	genTagsMap = new HashMap<String, Tags>();
	TagLoader tagLoader = new TagLoader();
	tagLoader.loadTags(genTagsMap);
	fileFormats = new ArrayList<String>();
	for (String format : genTagsMap.keySet()) {
	    fileFormats.add(format);
	}
//	genTagsMap = AppConfig.genTagsMap;
//	genTagsMap = new HashMap<String, GenericTags>();
//	genTagsMap.put("txt", new GenericTags("txt"));	// TODO auta na ta balo mesa sto appconfig !!
//	genTagsMap.put("xml", new GenericTags("xml"));
    }
    
//    private void initTagsMap() {
//	genTagsMap = new HashMap<String, GenericTags>();
//	TagLoader tagLoader = new TagLoader();
//	tagLoader.loadTags(genTagsMap);
//	fileFormats = new ArrayList<String>();
//	for (String format : genTagsMap.keySet()) {
//	    fileFormats.add(format);
//	}
//    }

    public void addFilePathToMap(int trn, String filePath) {
	filePathsMap.put(trn, filePath);
    }

    public Map<String, List<String>> readTaxpayerFromFile(String fileNamePath) throws Exception {
	String ending[] = fileNamePath.split("\\.");
	String fileFormat = ending[1];
	String filePathWithoutFileFormat = ending[0];
	int trn = getTaxRegNumFromFileNamePath(fileNamePath);
	filePathsMap.put(trn, filePathWithoutFileFormat);
	Tags genTags = genTagsMap.get(fileFormat);
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

    public void removeTaxpayerFilePath(int taxRegNum) {
	filePathsMap.remove(taxRegNum);

    }

    public void updateTaxpayerFiles(int taxRegNum, List<String> taxpayerInfoData,
	    Map<Integer, List<String>> receiptsDataOfTaxpayer) throws IOException {
//	String fileFormats[] = getFileFormats();  
//	List<String> fileFormats = AppConfig.fileFormats;
//	for (int i = 0; i < fileFormats.length; i++) {
	for (int i = 0; i < fileFormats.size(); i++) {
//	    String fileNamePath = filePathsMap.get(taxRegNum) + "." + fileFormats[i];
	    String fileNamePath = filePathsMap.get(taxRegNum) + "." + fileFormats.get(i);
	    File infoFile = new File(fileNamePath);
	    if (infoFile.exists()) {
//		GenericTags genTags = genTagsMap.get(fileFormats[i]);
		Tags genTags = genTagsMap.get(fileFormats.get(i));
//		String fileNamePath = filePathsMap.get(taxRegNum) + "." + fileFormats.get(i);
		List<String> allInfoData = DataTagger.getTaggedTaxpayerAsList(taxpayerInfoData, receiptsDataOfTaxpayer,
			genTags.getTaxpayerAllInfoTags());
		TaxFileWriter.writeTaggedData(fileNamePath, allInfoData);
	    }
	}
    }

    public void saveLogeFile(String filePath, String fileFormat, List<String> logData, boolean taxIncrease)
	    throws IOException {
	String fileNamePath = filePath + fileFormat;
	Tags genTags = genTagsMap.get(fileFormat);
	List<String> logTaggedData = DataTagger.tagData(logData, genTags.getLogHeaders(), genTags.getLogFooters());
	TaxFileWriter.writeTaggedData(fileNamePath, logTaggedData);
    }

    public List<String> getFileFormats() {
	return fileFormats;
    }

    public void setFileMapEntry(int i, String string) {
	filePathsMap.put(i, string);
    }



}
