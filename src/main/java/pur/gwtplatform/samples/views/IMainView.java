package pur.gwtplatform.samples.views;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.View;

public interface IMainView extends View {
	Button getPopupButton();

	HTMLPanel getHtmlPanel();
}