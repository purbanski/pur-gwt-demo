package pur.gwtplatform.samples.presenter;

import java.util.ArrayList;
import java.util.List;

import pur.gwtplatform.samples.events.DicoCompleteEvent;
import pur.gwtplatform.samples.events.DicoCompleteEvent.InsertCompleteHandler;
import pur.gwtplatform.samples.events.UpdateDataGridEvent;
import pur.gwtplatform.samples.model.Data;
import pur.gwtplatform.samples.services.DataService;
import pur.gwtplatform.samples.views.IDeleteDialogView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;

public class DeleteDialogPresenter extends PresenterWidget<IDeleteDialogView> {
	private EventBus eventBus;
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	List<Data> array = new ArrayList<Data>();
	@Inject
	private DataService dataService;

	@Inject
	public DeleteDialogPresenter(final EventBus eventBus, final IDeleteDialogView view) {
		super(eventBus, view);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
		enregistrerBoutonAnnuler();
		gererAutoCompleteBox();
		gererEvenements();
	}

	private void enregistrerBoutonAnnuler() {
		registerHandler(getView().getAnnulerButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getView().asWidget().hide();
			}
		}));
	}

	private void gererAutoCompleteBox() {
		refreshAutoCompBox();
		registerHandler(getView().getAutoCompleteBox().addSelectionHandler(
				new SelectionHandler<SuggestOracle.Suggestion>() {
					@Override
					public void onSelection(SelectionEvent<Suggestion> event) {
						String query = event.getSelectedItem().getReplacementString();
						eventBus.fireEvent(new UpdateDataGridEvent(query));
					}
				}));

		registerHandler(getView().getAutoCompleteBox().addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				String texte = getView().getAutoCompleteBox().getText();
				if (texte.length() > 0) {
					dataService.getDataDico(array, texte);
				}
			}
		}));

	}

	private void refreshAutoCompBox() {
		oracle = (MultiWordSuggestOracle) getView().getAutoCompleteBox().getSuggestOracle();
		oracle.clear();
		for (Data data : array) {
			oracle.add(data.getKey());
		}
	}

	private void gererEvenements() {
		registerHandler(eventBus.addHandler(DicoCompleteEvent.TYPE, new InsertCompleteHandler() {
			public void onInsertComplete(DicoCompleteEvent event) {
				refreshAutoCompBox();
			}
		}));
	}
}
