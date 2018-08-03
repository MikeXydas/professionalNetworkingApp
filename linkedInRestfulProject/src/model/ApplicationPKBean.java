package model;

public class ApplicationPKBean {

	private int idApplication;
	private int advertisment_idAdvertisment;
	private int advertisment_User_idUser;

	public int getAdvertisment_User_idUser() {
		return advertisment_User_idUser;
	}
	public void setAdvertisment_User_idUser(int advertisment_User_idUser) {
		this.advertisment_User_idUser = advertisment_User_idUser;
	}
	public int getIdApplication() {
		return idApplication;
	}
	public void setIdApplication(int idApplication) {
		this.idApplication = idApplication;
	}
	public int getAdvertisment_idAdvertisment() {
		return advertisment_idAdvertisment;
	}
	public void setAdvertisment_idAdvertisment(int advertisment_idAdvertisment) {
		this.advertisment_idAdvertisment = advertisment_idAdvertisment;
	}

}
