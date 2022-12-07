package incometaxcalculator.tags;

public class TXTTag extends Tag {   
//    public TXTTag(String nameOfTag, String tagData) {
//	super(nameOfTag, tagData+":");
//    }
    

    public TXTTag(String headerData) {
	super(headerData+":", "");
    }

    @Override
    public String addTagsToData(String data) {
	return headerTag + " " + data;
    }

}
