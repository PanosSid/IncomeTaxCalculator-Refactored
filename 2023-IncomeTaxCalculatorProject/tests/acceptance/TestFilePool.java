package acceptance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

public class TestFilePool {
    private int taxRegistrationNumber = 111111111;
    private String resourcesPath = "resources/testing resources/";
//    private String fileTypes[] = {"txt", "xml"};
//    private String operations[] = {"load", "add", "delete", "log"}; 
    private List<String> fileTypes = new ArrayList<String>();
    private List<String> operations = new ArrayList<String>();
    private Map<String, HashMap<String, File>> testFiles = new HashMap<>(); // new HashMap<String, HashMap<String,
									    // File>>();

//    public TestFilePool() {
//	super();
//	fileTypes.add("txt");
//	fileTypes.add("xml");
//	operations.add("initial");
//	operations.add("add");
//	operations.add("delete");
//	operations.add("log");
//		
//	for (String fType : fileTypes) {
//	    HashMap<String, String> operationsMap = new HashMap<String, String>();
//	    for (String oper : operations) {
//		if (oper.equals("initial")) {
//		    operationsMap.put(oper, TestFileContents.TXT_INITIAL_INFO);
//		} else if (oper.equals("add")) {
//		    
//		} else if (oper.equals("delete")) {
//		    
//		} else if (oper.equals("log")) {
//		    
//		} else {
//		    
//		}
//	    }
//	    testFiles.put(fType, operationsMap);
//	}
//	
//    }

    public TestFilePool() {
	super();
	fileTypes.add("txt");
	fileTypes.add("xml");
	operations.add("load");
	operations.add("add");
	operations.add("delete");
	operations.add("log");

	for (String fType : fileTypes) {
	    HashMap<String, File> operationsMap = new HashMap<String, File>();
	    for (String oper : operations) {
		String filePath = resourcesPath + oper + "_" + taxRegistrationNumber;
		if (oper.equals("log")) {
		    filePath += "_LOG." + fType;
		} else {
		    filePath += "_INFO." + fType;
		}
		File file = new File(filePath);
		operationsMap.put(oper, file);
	    }
	    testFiles.put(fType, operationsMap);
	}

    }

    public List<String> getFileTypes() {
	return fileTypes;
    }

    public File getTestFile(String fileType, String operation) {
	return testFiles.get(fileType).get(operation);
    }

//    public File getTxtFileForOperation(String operation) {
//	return testFiles.get("txt").get(operation);
//    }
//    
//    public File getXmlFileForOperation(String operation) {
//	return testFiles.get("xml").get(operation);
//    }

    @Override
    public String toString() {
	return "TestFilePool [taxRegistrationNumber=" + taxRegistrationNumber + ", resourcesPath=" + resourcesPath
		+ ", fileTypes=" + fileTypes + ", operations=" + operations + ",\ntestFiles=" + testFiles + "]";
    }

    public static void main(String[] args) {
	TestFilePool testFilePool = new TestFilePool();
	System.out.println(testFilePool);

	System.out.println(testFilePool.getTestFile("txt", "log"));
    }

}
