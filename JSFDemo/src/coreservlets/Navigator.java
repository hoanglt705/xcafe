package coreservlets;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="navigator")
@SessionScoped
public class Navigator {
	public String choosePage() {
		return "page1";
	}
}
