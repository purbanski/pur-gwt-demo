package pur.gwtplatform.samples.views;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.gwtplatform.mvp.client.PopupView;

public interface IRechercheDialogView extends PopupView {
	Button getAnnulerButton();

	SuggestBox getAutoCompleteBox();

	PopupPanel asWidget();

	DataGrid getGrilleResultat();

	Button getRechercherBouton();
}
