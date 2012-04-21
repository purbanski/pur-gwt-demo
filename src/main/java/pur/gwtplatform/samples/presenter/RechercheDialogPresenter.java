package pur.gwtplatform.samples.presenter;

import java.util.ArrayList;
import java.util.List;

import pur.gwtplatform.samples.events.CodeChoisiEvent;
import pur.gwtplatform.samples.events.DicoCompleteEvent;
import pur.gwtplatform.samples.events.DicoCompleteEvent.DicoCompleteHandler;
import pur.gwtplatform.samples.events.SearchCompleteEvent;
import pur.gwtplatform.samples.events.SearchCompleteEvent.SearchCompleteHandler;
import pur.gwtplatform.samples.model.DicoResult;
import pur.gwtplatform.samples.model.ElementResult;
import pur.gwtplatform.samples.services.MPService;
import pur.gwtplatform.samples.views.IRechercheDialogView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;

public class RechercheDialogPresenter extends PresenterWidget<IRechercheDialogView> {
	private EventBus eventBus;
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	List<DicoResult> array = new ArrayList<DicoResult>();
	private List<ElementResult> liste = new ArrayList<ElementResult>(10);
	private DataGrid dataGrid = null;
	private String texteSaisie;

	@Inject
	private MPService mpService;

	@Inject
	public RechercheDialogPresenter(final EventBus eventBus, final IRechercheDialogView view) {
		super(eventBus, view);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
		enregistrerBoutonAnnuler();
		enregistrerBoutonRechercher();
		gererAutoCompleteBox();
		gererEvenements();
		initDataGrid();
	}

	private void enregistrerBoutonAnnuler() {
		registerHandler(getView().getAnnulerBouton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getView().asWidget().hide();
			}
		}));
	}

	private void enregistrerBoutonRechercher() {
		registerHandler(getView().getRechercherBouton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String query = getView().getAutoCompleteBox().getText();
				appelSearchMP(query);
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
						appelSearchMP(query);
					}
				}));

		registerHandler(getView().getAutoCompleteBox().addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				String texte = getView().getAutoCompleteBox().getText();
				if (texte.length() > 0 & !texte.equals(texteSaisie)) {
					texteSaisie = texte;
					mpService.searchDico(array, texte);					
				}
			}			
		}));		
	}

	private void refreshAutoCompBox() {
		oracle = (MultiWordSuggestOracle) getView().getAutoCompleteBox().getSuggestOracle();
		oracle.clear();
		for (DicoResult data : array) {
			oracle.add(data.getKey());
		}
	}

	private void gererEvenements() {
		registerHandler(eventBus.addHandler(DicoCompleteEvent.TYPE, new DicoCompleteHandler() {
			public void onInsertComplete(DicoCompleteEvent event) {
				refreshAutoCompBox();
			}
		}));
		registerHandler(eventBus.addHandler(SearchCompleteEvent.TYPE, new SearchCompleteHandler() {
			public void onSearchComplete(SearchCompleteEvent event) {
				refreshDataGrid();
			}
		}));
	}

	private void initDataGrid() {

		TextColumn<ElementResult> kColumn = new TextColumn<ElementResult>() {

			@Override
			public String getValue(ElementResult data) {
				return data.getK();
			}
		};

		TextColumn<ElementResult> nColumn = new TextColumn<ElementResult>() {

			@Override
			public String getValue(ElementResult data) {
				return data.getN();
			}
		};
		TextColumn<ElementResult> oColumn = new TextColumn<ElementResult>() {

			@Override
			public String getValue(ElementResult data) {
				return data.getO();
			}
		};
		TextColumn<ElementResult> pColumn = new TextColumn<ElementResult>() {

			@Override
			public String getValue(ElementResult data) {
				return data.getP();
			}
		};

		TextColumn<ElementResult> lColumn = new TextColumn<ElementResult>() {

			@Override
			public String getValue(ElementResult data) {
				return data.getL();
			}
		};
		TextColumn<ElementResult> hColumn = new TextColumn<ElementResult>() {

			@Override
			public String getValue(ElementResult data) {
				return new HTML(data.getHighs()).getText();
			}
		};

		dataGrid = getView().getGrilleResultat();
		dataGrid.setSize("1180px", "700px");
		dataGrid.addColumn(kColumn, "SC NAF3142");
		dataGrid.addColumn(lColumn, "Libell\u00E9 NAF3142");
		dataGrid.addColumn(nColumn, "CodePck 1");
		dataGrid.addColumn(oColumn, "CodePck 2");
		dataGrid.addColumn(pColumn, "CodePck 3");
		dataGrid.addColumn(hColumn, "Highs");
		dataGrid.setRowData(liste);
		dataGrid.setColumnWidth(kColumn, "100px");
		dataGrid.setColumnWidth(nColumn, "100px");
		dataGrid.setColumnWidth(oColumn, "100px");
		dataGrid.setColumnWidth(pColumn, "100px");
		dataGrid.setColumnWidth(lColumn, "300px");
		dataGrid.setColumnWidth(hColumn, "480px");
		dataGrid.setRowData(liste);
		dataGrid.setVisible(liste.isEmpty());

		// Add a selection model to handle user selection.
		final SingleSelectionModel<ElementResult> selectionModel = new SingleSelectionModel<ElementResult>();
		dataGrid.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				ElementResult selected = selectionModel.getSelectedObject();
				if (selected != null) {
					eventBus.fireEvent(new CodeChoisiEvent(selected.getN()));
				}
				getView().asWidget().hide();
			}
		});

	}

	private void appelSearchMP(String query) {
		liste.clear();
		mpService.search(liste, query);
	}

	private void refreshDataGrid() {
		dataGrid.setRowData(liste);
		dataGrid.setRowCount(liste.size());
	}

}
