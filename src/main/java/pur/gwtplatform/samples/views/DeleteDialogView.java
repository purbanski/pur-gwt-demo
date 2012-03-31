package pur.gwtplatform.samples.views;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class DeleteDialogView extends PopupViewImpl implements IDeleteDialogView {

	private final PopupPanel widget;

	public interface Binder extends UiBinder<PopupPanel, DeleteDialogView> {
	}

	@Inject
	public DeleteDialogView(final EventBus eventBus, final Binder binder) {
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


	public Button getAnnulerButton() {
		return annulerButton;
	}

	public SuggestBox getAutoCompleteBox() {
		return autoCompleteBox;
	}
}
