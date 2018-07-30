package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Application database table.
 * 
 */
@Entity
@Table(name="`Application`")
@NamedQuery(name="Application.findAll", query="SELECT a FROM Application a")
public class Application implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApplicationPK id;

	private int applicantId;

	//bi-directional many-to-one association to Advertisment
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="Advertisment_idAdvertisment", referencedColumnName="idAdvertisment")
		})
	private Advertisment advertisment;

	public Application() {
	}

	public ApplicationPK getId() {
		return this.id;
	}

	public void setId(ApplicationPK id) {
		this.id = id;
	}

	public int getApplicantId() {
		return this.applicantId;
	}

	public void setApplicantId(int applicantId) {
		this.applicantId = applicantId;
	}

	public Advertisment getAdvertisment() {
		return this.advertisment;
	}

	public void setAdvertisment(Advertisment advertisment) {
		this.advertisment = advertisment;
	}

}