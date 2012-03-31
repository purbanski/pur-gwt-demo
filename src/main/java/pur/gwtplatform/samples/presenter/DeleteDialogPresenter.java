package pur.gwtplatform.samples.presenter;

import java.util.ArrayList;
import java.util.List;

import pur.gwtplatform.samples.events.CodeChoisiEvent;
import pur.gwtplatform.samples.events.DicoCompleteEvent;
import pur.gwtplatform.samples.events.DicoCompleteEvent.InsertCompleteHandler;
import pur.gwtplatform.samples.events.SearchCompleteEvent;
import pur.gwtplatform.samples.events.SearchCompleteEvent.SearchCompleteHandler;
import pur.gwtplatform.samples.events.UpdateDataGridEvent;
import pur.gwtplatform.samples.model.Data;
import pur.gwtplatform.samples.model.ElementResult;
import pur.gwtplatform.samples.services.DataService;
import pur.gwtplatform.samples.views.IDeleteDialogView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;

public class DeleteDialogPresenter extends PresenterWidget<IDeleteDialogView> {
	private EventBus eventBus;
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	List<Data> array = new ArrayList<Data>();
	private List<ElementResult> liste = new ArrayList<ElementResult>(10);
	private DataGrid dataGrid = null;
	private TextColumn<ElementResult> kColumn = new TextColumn<ElementResult>() {

		@Override
		public String getValue(ElementResult data) {
			return data.getK();
		}
	};

	private TextColumn<ElementResult> nColumn = new TextColumn<ElementResult>() {

		@Override
		public String getValue(ElementResult data) {
			return data.getN();
		}
	};
	private TextColumn<ElementResult> oColumn = new TextColumn<ElementResult>() {

		@Override
		public String getValue(ElementResult data) {
			return data.getO();
		}
	};
	private TextColumn<ElementResult> pColumn = new TextColumn<ElementResult>() {

		@Override
		public String getValue(ElementResult data) {
			return data.getP();
		}
	};

	private TextColumn<ElementResult> lColumn = new TextColumn<ElementResult>() {

		@Override
		public String getValue(ElementResult data) {
			return data.getL();
		}
	};
	private TextColumn<ElementResult> hColumn = new TextColumn<ElementResult>() {

		@Override
		public String getValue(ElementResult data) {
			return data.getHighs();
		}
	};
	
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
		enregistrerBoutonRechercher();
		gererAutoCompleteBox();
		gererEvenements();
		initDataGrid();
	}

	private void enregistrerBoutonAnnuler() {
		registerHandler(getView().getAnnulerButton().addClickHandler(new ClickHandler() {
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
				appelServiceIndex(query);
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
						appelServiceIndex(query);
					}
				}));

		registerHandler(getView().getAutoCompleteBox().addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				String texte = getView().getAutoCompleteBox().getText();
				if (texte.length() > 0) {
					dataService.getDataDico(array, texte);
					//appelServiceIndex(texte);
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
		registerHandler(eventBus.addHandler(SearchCompleteEvent.TYPE, new SearchCompleteHandler() {
			public void onSearchComplete(SearchCompleteEvent event) {
				refreshDataGrid();
			}
		}));
	}

	private void initDataGrid() {

		dataGrid = getView().getGrilleResultat();
		dataGrid.setSize("1190px", "700px");
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
		
		
		 // Add a selection model to handle user selection.
	    final SingleSelectionModel<ElementResult> selectionModel = new SingleSelectionModel<ElementResult>();
	    dataGrid.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	    	  ElementResult selected = selectionModel.getSelectedObject();
	        if (selected != null) {	         
	          eventBus.fireEvent(new CodeChoisiEvent(selected.getN()));
	        }
	      }
	    });
		
	}

	private void appelServiceIndex(String query) {
		liste.clear();
		dataService.getDataIndex(liste, query);
	}

	private void refreshDataGrid() {
		dataGrid.setRowData(liste);
		dataGrid.setRowCount(liste.size());
	}

}
