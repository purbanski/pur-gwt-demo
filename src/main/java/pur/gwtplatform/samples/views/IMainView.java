package pur.gwtplatform.samples.views;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.gwtplatform.mvp.client.View;

public interface IMainView extends View {
	HTMLPanel getHtmlPanel();
	Image getRechercheImage();
	
}