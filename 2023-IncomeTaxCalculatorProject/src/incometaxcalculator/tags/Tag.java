package incometaxcalculator.tags;

import incometaxcalculator.exceptions.FileHasWrongTagsException;

public abstract class Tag {
//    protected String nameOfTag;
    protected String headerTag;
    protected String footerTag;
    
//    public Tag(String nameOfTag, String headerTag) {
//	super();
//	this.nameOfTag = nameOfTag;
//	this.headerTag = headerTag;
//    }
    
    public Tag(String headerTag, String footerTag) {
	super();
	this.headerTag = headerTag;
	this.footerTag = footerTag;
    }
        
    public String getHeaderTag() {
	return headerTag;
    }
    
    public String getFooterTag() {
	return footerTag;
    }

    public abstract String addTagsToData(String data);
    
    public String removeTagsFromLine(String line) throws FileHasWrongTagsException {
	if (!doesLineContainTags(line)) {
	    throw new FileHasWrongTagsException();
	}
	return line.replaceAll(headerTag, "").replaceAll(footerTag, "").trim();
    }
    
    public boolean doesLineContainTags(String line) {
	return line.startsWith(headerTag) && line.endsWith(footerTag);
    }
    
    public boolean doesLineContainHeaderTag(String line) {
	return line.startsWith(headerTag);
    }
    
    public boolean doesLineContainFooterTag(String line) {
	return line.endsWith(footerTag);
    }
    
    
//    public boolean areTagsPartOfLine(String line) {
//  	return line.startsWith(headerTag) && line.endsWith(footerTag);
//      }
      
    
    
//    public String[] getTagNameAndHeader() {
//	String ret[] = {nameOfTag, headerTag};
//	return ret;
//    }
    

    
    
    
}
