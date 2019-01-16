package dto;

import java.io.Serializable;

public class ApplicationRejectDTO implements Serializable {

	private static final long serialVersionUID = -5863126649989085326L;

	int fixUpTaskId;
	int applicationId;
	String comment;

	public int getFixUpTaskId() {
		return this.fixUpTaskId;
	}

	public void setFixUpTaskId(int fixUpTaskId) {
		this.fixUpTaskId = fixUpTaskId;
	}

	public int getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
