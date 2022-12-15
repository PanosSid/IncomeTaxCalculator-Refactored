package incometaxcalculator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import incometaxcalculator.io.exceptions.WrongReceiptDateException;

public class Receipt {

    private final int id;
    private final Date issueDate;
    private final float amount;
    private final String kind;
    private final Company company;   


    public Receipt(int id, String issueDate, float amount, String kind, Company company)
	    throws WrongReceiptDateException {
	this.id = id;
	this.issueDate = createDate(issueDate);
	this.amount = amount;
	this.kind = kind;
	this.company = company;
    }

    private Date createDate(String issueDate) throws WrongReceiptDateException {
	String token[] = issueDate.split("/");
	if (token.length != 3) {
	    throw new WrongReceiptDateException();
	}
	int day = Integer.parseInt(token[0]);
	int month = Integer.parseInt(token[1]);
	int year = Integer.parseInt(token[2]);
	return new Date(day, month, year);
    }
    
    public List<String> getDataOfReceipt(){
	List<String> data = new ArrayList<String>();
	data.add(""+id);
	data.add(issueDate.toString());
	data.add(kind);
	data.add(""+amount);
	data.add(company.getName());
	data.add(company.getCountry());
	data.add(company.getCity());
	data.add(company.getStreet());
	data.add(""+company.getNumber());
	return data;
    }
    
    public int getId() {
	return id;
    }

    public String getIssueDate() {
	return issueDate.toString();
    }

    public float getAmount() {
	return amount;
    }

    public String getKind() {
	return kind;
    }

    public Company getCompany() {
	return company;
    }

    public String getCompanyName() {
	return company.getName();
    }

    public String getCompanyCountry() {
	return company.getCountry();
    }

    public String getCompanyCity() {
	return company.getCity();
    }

    public String getCompanyStreet() {
	return company.getStreet();
    }

    public int getCompanyNumber() {
	return company.getNumber();
    }  


    @Override
    public int hashCode() {
	return Objects.hash(amount, company, id, issueDate, kind);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Receipt other = (Receipt) obj;
	boolean ret = Float.floatToIntBits(amount) == Float.floatToIntBits(other.amount)
		&& Objects.equals(company, other.company) && id == other.id
		&& Objects.equals(issueDate, other.issueDate) && Objects.equals(kind, other.kind);

	return ret;
    }

    @Override
    public String toString() {
	return "Receipt [id=" + id + ", issueDate=" + issueDate + ", amount=" + amount + ", kind=" + kind + ", company="
		+ company + "]";
    }

}