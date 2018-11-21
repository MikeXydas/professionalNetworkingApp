package model;

public class ShowConvBean {

	private int idConversation;
	private String firstName;
	private String lastName;
	private PhotoBean photoBean;

	public int getIdConversation() {
		return idConversation;
	}
	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public PhotoBean getPhotoBean() {
		return photoBean;
	}
	public void setPhotoBean(PhotoBean photoBean) {
		this.photoBean = photoBean;
	}
	
}
