package incometaxcalculator.newGui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class InfoFileFilter extends FileFilter {
    
    public boolean accept(File pathname) {
	if (pathname.getName().endsWith("INFO.txt"))
	      return true;
	else if (pathname.getName().endsWith("INFO.xml")) 
	      return true;
	else if (pathname.isDirectory())
	    return true;
	return false;
    }

    @Override
    public String getDescription() {
	return "(*INFO.txt) (*INFO.xml)";
    }
}
