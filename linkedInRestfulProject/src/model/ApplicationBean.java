package model;

public class ApplicationBean {
	
	private ApplicationPKBean id;
	private int applicantId;
	private AdvertismentBean advertisment;
	
	public ApplicationPKBean getId() {
		return id;
	}
	public void setId(ApplicationPKBean id) {
		this.id = id;
	}
	public int getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(int applicantId) {
		this.applicantId = applicantId;
	}
	public AdvertismentBean getAdvertisment() {
		return advertisment;
	}
	public void setAdvertisment(AdvertismentBean advertisment) {
		this.advertisment = advertisment;
	}
	
}
