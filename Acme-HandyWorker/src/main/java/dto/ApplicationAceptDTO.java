package dto;

import java.io.Serializable;

public class ApplicationAceptDTO implements Serializable {

	private static final long serialVersionUID = -6529091330956090604L;

	String holderName;
	String brandName;
	String number;
	int expirationMonth;
	int expirationYear;
	int cVV;
	
	String coment;
	
	int fixUpTaskId;
	
	int applicationId;

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getFixUpTaskId() {
		return fixUpTaskId;
	}

	public void setFixUpTaskId(int fixUpTaskId) {
		this.fixUpTaskId = fixUpTaskId;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public int getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}

	public int getcVV() {
		return cVV;
	}

	public void setcVV(int cVV) {
		this.cVV = cVV;
	}

	public String getComent() {
		return coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}

}
