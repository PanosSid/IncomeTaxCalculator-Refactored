package incometaxcalculator.tags;

public class XMLTag extends Tag {
    
//    public XMLTag(String nameOfTag, String tagData) {
//	super(nameOfTag, "<"+tagData.replaceAll("\\s+", "") +">");
//	this.footerTag = "</"+tagData.replaceAll("\\s+", "") +">";
//    }
    
    public XMLTag(String tagData) {
	super("<"+tagData.replaceAll("\\s+", "") +">", "</"+tagData.replaceAll("\\s+", "") +">");
    }

    public String getFooterTag() {
	return footerTag;
    }
    
//    public String[] getTagNameAndHeaderAndFooter() {
//	String ret[] = {nameOfTag, headerTag, footerTag};
//	return ret;
//    }

    @Override
    public String addTagsToData(String data) {
	return headerTag +" "+ data+ " " + footerTag;
    }
   
}
