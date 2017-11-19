package coreservlets;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class TrainingForm {
	private String emailAddress;
	private String favouriteLanguage;;
	private String secondLanguage;;
	private Boolean expert = true;
	private Boolean liar = false;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFavouriteLanguage() {
		return favouriteLanguage;
	}

	public void setFavouriteLanguage(String favouriteLanguage) {
		this.favouriteLanguage = favouriteLanguage;
	}

	public String getSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public Boolean getExpert() {
		return expert;
	}

	public void setExpert(Boolean expert) {
		this.expert = expert;
	}

	public Boolean getLiar() {
		return liar;
	}

	public void setLiar(Boolean liar) {
		this.liar = liar;
	}

}
