
package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Category extends DomainEntity {

	private String	name;
	private String	espName;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getEspName() {
		return this.espName;
	}

	public void setEspName(final String espName) {
		this.espName = espName;
	}


	// Relationships ----------------------------------------------------------

	private Category	parentCategory;


	@ManyToOne(optional = true)
	public Category getParentCategory() {
		return this.parentCategory;
	}

	public void setParentCategory(final Category parentCategory) {
		this.parentCategory = parentCategory;
	}

}
