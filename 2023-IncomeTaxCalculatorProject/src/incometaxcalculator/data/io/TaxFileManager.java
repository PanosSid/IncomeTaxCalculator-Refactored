package incometaxcalculator.data.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import incometaxcalculator.tags.FileTags;

public class TaxFileManager {
    private Map<Integer, String> filePathsMap;
    private Map<String, FileTags> fileTagsMap;

    public TaxFileManager() {
	super();
	filePathsMap = new HashMap<Integer, String>();
	fileTagsMap = new HashMap<String, FileTags>();
	fileTagsMap.put("txt", new FileTags("txt"));
	fileTagsMap.put("xml", new FileTags("xml"));
    }
    
    public void addFilePathToMap(int trn, String filePath) {
	filePathsMap.put(trn, filePath);
    }

    public Map<String, List<String>> readTaxpayerFromFile(String fileNamePath) throws Exception {
	String ending[] = fileNamePath.split("\\.");
	String fileType = ending[1];
	String filePathWithoutFileFormat = ending[0];
	int trn = getTaxRegNumFromFileNamePath(fileNamePath);
	filePathsMap.put(trn, filePathWithoutFileFormat);
	TaxFileReader fileReader = new TaxFileReader(fileTagsMap.get(fileType).getInfoTags(),
		fileTagsMap.get(fileType).getReceiptTags());
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
	    String filename = filePathsMap.get(taxRegNum) + "." + fileFormats[i];
	    File infoFile = new File(filename);
	    if (infoFile.exists()) {
		String fileNamePath = filePathsMap.get(taxRegNum);
		TaxFileWriter infoWriter = new InfoWriter(fileNamePath + "." + fileFormats[i],
			fileTagsMap.get(fileFormats[i]).getInfoTags(),
			fileTagsMap.get(fileFormats[i]).getReceiptTags());
		infoWriter.updateInfoFile(taxpayerInfoData, receiptsDataOfTaxpayer);
	    }
	}
    }

    public void saveLogeFile(String filePath, String fileFormat, List<String> logData, boolean taxIncrease) throws IOException {
	String fileNamePath = filePath + fileFormat;
	TaxFileWriter logWriter = new LogWriter(fileNamePath, taxIncrease, fileTagsMap.get(fileFormat).getLogTags());
	logWriter.generateFile(logData);
    }
    
    private String[] getFileFormats() {
	return String.join(",", fileTagsMap.keySet()).split(",");
    }

    public void setFileMapEntry(int i, String string) {
	filePathsMap.put(i, string);
	
    }
}
