package pur.gwtplatform.samples.views;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtplatform.mvp.client.View;

public interface IMainView extends View {
	public Label getCode();

	VerticalPanel getPanel1();

	Button getPopupButton();

}