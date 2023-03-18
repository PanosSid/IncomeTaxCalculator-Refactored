package incometaxcalculator.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/* Utility writer class*/
public class TaxFileWriter {

    public static void writeTaggedData(String fileNamePath, List<String> infoLines) throws IOException {
	FileWriter infoFile = new FileWriter(fileNamePath);
	PrintWriter outputStream = new PrintWriter(infoFile);
	int size = infoLines.size();
	for (int i = 0; i < size - 1; i++) {
	    outputStream.println(infoLines.get(i));
	}
	outputStream.print(infoLines.get(size - 1));
	outputStream.close();
    }

}