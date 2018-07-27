package model;

import entities.Advertisment;
import entities.ApplicationPK;

public class ApplicationBean {
	
	private ApplicationPK id;
	private int applicantId;
	private Advertisment advertisment;
	
	public ApplicationBean( ) {
	}
	
	public ApplicationPK getId() {
		return id;
	}
	public void setId(ApplicationPK id) {
		this.id = id;
	}
	public int getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(int applicantId) {
		this.applicantId = applicantId;
	}
	public Advertisment getAdvertisment() {
		return advertisment;
	}
	public void setAdvertisment(Advertisment advertisment) {
		this.advertisment = advertisment;
	}

}
