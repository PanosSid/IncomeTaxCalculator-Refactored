package incometaxcalculator.io;

import java.util.ArrayList;
import java.util.List;

public class Tags {
	private String fileFormat;
	private List<String> infoHeaders;
	private List<String> infoFooters;
	private List<String> receiptHeaders;
	private List<String> receiptFooters;
	private List<String> logHeaders;
	private List<String> logFooters;

	public Tags(String fileFormat, List<String> infoHeaders, List<String> infoFooters, List<String> receiptHeaders,
			List<String> receiptFooters, List<String> logHeaders, List<String> logFooters) {
		super();
		this.fileFormat = fileFormat;
		this.infoHeaders = infoHeaders;
		this.infoFooters = infoFooters;
		this.receiptHeaders = receiptHeaders;
		this.receiptFooters = receiptFooters;
		this.logHeaders = logHeaders;
		this.logFooters = logFooters;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public List<String> getInfoHeaders() {
		return infoHeaders;
	}

	public List<String> getInfoFooters() {
		return infoFooters;
	}

	public List<String> getReceiptHeaders() {
		return receiptHeaders;
	}

	public List<String> getReceiptFooters() {
		return receiptFooters;
	}

	public List<String> getLogHeaders() {
		return logHeaders;
	}

	public List<String> getLogFooters() {
		return logFooters;
	}

	public List<List<String>> getTaxpayerAllInfoTags() {
		List<List<String>> tags = new ArrayList<>();
		tags.add(infoHeaders);
		tags.add(infoFooters);
		tags.add(receiptHeaders);
		tags.add(receiptFooters);
		return tags;
	}

}
