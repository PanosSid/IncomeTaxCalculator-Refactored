package incometaxcalculator.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TagLoader {
	private Scanner scanner;
	private String fileNamePath = System.getProperty("user.dir") + "\\resources\\tagsProperties.txt";

	public TagLoader() {
	}

	public TagLoader(String fileNamePath) {
		super();
		this.fileNamePath = fileNamePath;
	}

	public Map<String, Tags> getTagsFromFile() {
		Map<String, Tags> tagsMap = new HashMap<String, Tags>();
		try {
			scanner = new Scanner(new File(fileNamePath));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("--TAG START")) {
					Tags tag;
					try {
						tag = getTagFromFile();
						tagsMap.put(tag.getFileFormat(), tag);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tagsMap;
	}

	private Tags getTagFromFile() throws Exception {
		List<String> infoHeaders = new ArrayList<String>();
		List<String> infoFooters = new ArrayList<String>();
		List<String> receiptHeaders = new ArrayList<String>();
		List<String> receiptFooters = new ArrayList<String>();
		List<String> logHeaders = new ArrayList<String>();
		List<String> logFooters = new ArrayList<String>();

		String fileFormat = getFormatOfTag();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.startsWith("--TAG END")) {
				break;
			}
			if (line.startsWith("#TAXPAYER INFO")) {
				laodHeadersAndFooters(getLines(4), infoHeaders, infoFooters);
			} else if (line.startsWith("#RECEIPTS")) {
				laodHeadersAndFooters(getLines(10), receiptHeaders, receiptFooters);
			} else if (line.startsWith("#LOG")) {
				laodHeadersAndFooters(getLines(12), logHeaders, logFooters);
			}

		}
		return new Tags(fileFormat, infoHeaders, infoFooters, receiptHeaders, receiptFooters, logHeaders, logFooters);
	}

	private List<String> getLines(int numOfFields) {
		List<String> lines = new ArrayList<String>();
		for (int i = 0; i < numOfFields; i++) {
			if (scanner.hasNextLine()) {
				lines.add((scanner.nextLine().split("="))[1].trim());
			}
		}
		return lines;
	}

	private void laodHeadersAndFooters(List<String> lines, List<String> headers, List<String> footers)
			throws Exception {
		for (String line : lines) {
			String partsOfLine[] = line.split("_");
			if (partsOfLine.length == 2) {
				headers.add(partsOfLine[0].trim());
				footers.add(partsOfLine[1].trim());
			} else if (partsOfLine.length == 1) {
				headers.add(partsOfLine[0].trim());
				footers.add("");
			} else {
				throw new Exception("too many tags on a line ");
			}
		}
	}

	private String getFormatOfTag() {
		String line;
		if (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.startsWith("*File Format"))
				return line.split("=")[1].trim();
		}
		return null;
	}

}
