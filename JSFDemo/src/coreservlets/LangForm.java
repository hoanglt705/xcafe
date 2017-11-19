package coreservlets;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="langForm")
@SessionScoped
public class LangForm {
	private String language;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language.trim();
	}

	public String showChoice() {
		if (isMissing(language)) {
			return ("missing-language");
		} else if (language.equalsIgnoreCase("Java")
				|| language.equalsIgnoreCase("Groovy")) {
			return ("good-language");
		} else {
			return ("bad-language");
		}
	}

	private boolean isMissing(String value) {
		return ((value == null) || (value.trim().isEmpty()));
	}
}
