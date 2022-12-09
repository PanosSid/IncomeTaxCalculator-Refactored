package incometaxcalculator.data.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import incometaxcalculator.tags.DataTagger;
import incometaxcalculator.tags.GenericTags;

public class TaxFileManager {
    private Map<Integer, String> filePathsMap;
    private Map<String, GenericTags> genTagsMap;

    public TaxFileManager() {
	super();
	filePathsMap = new HashMap<Integer, String>();	
	genTagsMap = new HashMap<String, GenericTags>();
	genTagsMap.put("txt", new GenericTags("txt"));
	genTagsMap.put("xml", new GenericTags("xml"));
    }
    
    public void addFilePathToMap(int trn, String filePath) {
	filePathsMap.put(trn, filePath);
    }

    public Map<String, List<String>> readTaxpayerFromFile(String fileNamePath) throws Exception {
	String ending[] = fileNamePath.split("\\.");
	String fileFormat = ending[1];
	String filePathWithoutFileFormat = ending[0];
	int trn = getTaxRegNumFromFileNamePath(fileNamePath);
	filePathsMap.put(trn, filePathWithoutFileFormat);
	GenericTags genTags = genTagsMap.get(fileFormat);
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
	String fileFormats[] = getFileFormats();  // TODO get from somewhere the file formats
	for (int i = 0; i < fileFormats.length; i++) {
	    String fileNamePath = filePathsMap.get(taxRegNum) + "." + fileFormats[i];
	    File infoFile = new File(fileNamePath);
	    if (infoFile.exists()) {
		GenericTags genTags = genTagsMap.get(fileFormats[i]);
		List<String> allInfoData = DataTagger.getTaggedTaxpayerAsList(taxpayerInfoData, receiptsDataOfTaxpayer,
			genTags.getTaxpayerAllInfoTags());
		TaxFileWriter.writeTaggedData(fileNamePath, allInfoData);
	    }
	}
    } 

    public void saveLogeFile(String filePath, String fileFormat, List<String> logData, boolean taxIncrease) throws IOException {
	String fileNamePath = filePath + fileFormat;
	GenericTags genTags = genTagsMap.get(fileFormat);
	List<String> logTaggedData = DataTagger.tagData(logData, genTags.getLogHeaders(), genTags.getLogFooters());
	TaxFileWriter.writeTaggedData(fileNamePath, logTaggedData);
    }
    
    private String[] getFileFormats() {
	return String.join(",", genTagsMap.keySet()).split(",");
    }

    public void setFileMapEntry(int i, String string) {
	filePathsMap.put(i, string);
    }
}
