
package forms;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class MessageForm {

	public static final String	NEUTRAL	= "NEUTRAL", HIGH = "HIGH", LOW = "LOW";

	private int					id;
	private Collection<Integer>	recipientIds;
	private Date				moment;
	private String				subject;
	private String				body;
	private String				priority;


	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@Pattern(regexp = "^NEUTRAL|HIGH|LOW$")
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<Integer> getRecipientIds() {
		return this.recipientIds;
	}

	public void setRecipientIds(Collection<Integer> ids) {
		this.recipientIds = ids;
	}

}
