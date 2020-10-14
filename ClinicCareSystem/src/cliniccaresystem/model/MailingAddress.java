package cliniccaresystem.model;

public class MailingAddress {

	private String street;
	private String city;
	private USState state;
	private String zipcode;
	
	public MailingAddress(String street, String city, USState state, String zipcode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	
	
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public USState getState() {
		return state;
	}

	public void setState(USState state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
