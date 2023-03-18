package incometaxcalculator.gui;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileFilter;

public class InfoFileFilter extends FileFilter {
	private List<String> fileFormats;

	public InfoFileFilter(List<String> fileFormats) {
		super();
		this.fileFormats = fileFormats;
	}

	public boolean accept(File pathname) {
		for (String format : fileFormats) {
			if (pathname.getName().endsWith("_INFO." + format)) {
				return true;
			}
		}
		if (pathname.isDirectory())
			return true;

		return false;
//	if (pathname.getName().endsWith("INFO.txt"))
//	      return true;
//	else if (pathname.getName().endsWith("INFO.xml")) 
//	      return true;
//	else if (pathname.isDirectory())
//	    return true;
//	return false;
	}

	@Override
	public String getDescription() {
		String s = "";
		for (String format : fileFormats) {
			s += "(*INFO." + format + ") ";
		}
		return s;
//	return "(*INFO.txt) (*INFO.xml)";
	}
}
