package incometaxcalculator.io;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileManager {

	Map<String, List<String>> readTaxpayerFromFile(String fileNamePath) throws Exception;

	void removeTaxpayerFilePath(int taxRegNum);

	void updateTaxpayerFiles(int taxRegNum, List<String> taxpayerInfoData,
			Map<Integer, List<String>> receiptsDataOfTaxpayer) throws IOException;

	void saveLogeFile(String filePath, String fileFormat, List<String> logData, boolean taxIncrease) throws IOException;

	List<String> getFileFormats();

	int getTaxRegNumFromFileNamePath(String fileName);

	void addFilePathToMap(int taxRegNum, String string);

	void setFileMapEntry(int i, String string);

	void setTagsMap(Map<String, Tags> tagsFromFile);

}