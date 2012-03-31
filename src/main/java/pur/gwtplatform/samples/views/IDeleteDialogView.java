package pur.gwtplatform.samples.views;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.gwtplatform.mvp.client.PopupView;

public interface IDeleteDialogView extends PopupView {
	public Button getAnnulerButton();

	public SuggestBox getAutoCompleteBox();
	
	PopupPanel asWidget();
}
