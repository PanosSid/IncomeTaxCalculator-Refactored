package incometaxcalculator.data.management;

import java.util.Objects;

public class Company {

    private final String name;
    private final Address address;

    public Company() {	// isos auto prepei na afairethei 
	this.name = "";
	this.address = null;
    }

    public Company(String name, String country, String city, String street, int number) {
	this.name = name;
	this.address = new Address(country, city, street, number);
    }

    public String getName() {
	return name;
    }

    public String getCountry() {
	return address.getCountry();
    }

    public String getCity() {
	return address.getCity();
    }

    public String getStreet() {
	return address.getStreet();
    }

    public int getNumber() {
	return address.getNumber();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Company other = (Company) obj;
	return Objects.equals(address, other.address) && Objects.equals(name, other.name);
    }

}