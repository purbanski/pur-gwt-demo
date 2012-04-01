package pur.gwtplatform.samples.views;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.user.cellview.client.DataGrid;

public class RechercheDialogView extends PopupViewImpl implements IRechercheDialogView {

	private final PopupPanel widget;

	public interface Binder extends UiBinder<PopupPanel, RechercheDialogView> {
	}

	@Inject
	public RechercheDialogView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public PopupPanel asWidget() {
		return widget;
	}

	@UiField
	SuggestBox autoCompleteBox;
	@UiField
	Button annulerButton;	
	@UiField DataGrid grilleResultat;
	@UiField Button rechercherBouton;


	public Button getAnnulerButton() {
		return annulerButton;
	}

	public SuggestBox getAutoCompleteBox() {
		return autoCompleteBox;
	}

	public DataGrid getGrilleResultat() {
		return grilleResultat;
	}

	public Button getRechercherBouton() {
		return rechercherBouton;
	}
	
	
}
