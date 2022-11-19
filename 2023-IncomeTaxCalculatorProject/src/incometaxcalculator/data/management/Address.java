package incometaxcalculator.data.management;

import java.util.Objects;

public class Address {

    private final String country;
    private final String city;
    private final String street;
    private final int number;

    public Address(String country, String city, String street, int number) {
	this.country = country;
	this.city = city;
	this.street = street;
	this.number = number;
    }

    public String getCountry() {
	return country;
    }

    public String getCity() {
	return city;
    }

    public String getStreet() {
	return street;
    }

    public int getNumber() {
	return number;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Address other = (Address) obj;
	return Objects.equals(city, other.city) && Objects.equals(country, other.country) && number == other.number
		&& Objects.equals(street, other.street);
    }

    public String toString() {
	return (country + " " + city + " " + street + " " + number);
    }
}