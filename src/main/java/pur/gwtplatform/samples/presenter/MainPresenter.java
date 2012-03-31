package pur.gwtplatform.samples.presenter;

import java.util.ArrayList;
import java.util.List;

import pur.gwtplatform.samples.events.SearchCompleteEvent;
import pur.gwtplatform.samples.events.SearchCompleteEvent.SearchCompleteHandler;
import pur.gwtplatform.samples.events.UpdateDataGridEvent;
import pur.gwtplatform.samples.events.UpdateDataGridEvent.UpdateDataGridHandler;
import pur.gwtplatform.samples.model.ElementResult;
import pur.gwtplatform.samples.modules.NameTokens;
import pur.gwtplatform.samples.services.DataService;
import pur.gwtplatform.samples.views.IMainView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;



public class MainPresenter extends Presenter<IMainView, MainPresenter.MyProxy> {
	private EventBus eventBus;
	private final PlaceManager placeManager;
	private List<ElementResult> liste = new ArrayList<ElementResult>(10);
	private DataGrid dataGrid = null;
	private DeleteDialogPresenter deleteDialogPresenter;
	@Inject
	private DataService dataService;

	private TextColumn<ElementResult> idColumn = new TextColumn<ElementResult>() {

		@Override
		public String getValue(ElementResult data) {
			return data.getK();
		}
	};

	private TextColumn<ElementResult> valueColumn = new TextColumn<ElementResult>() {

		@Override
		public String getValue(ElementResult data) {
			return data.getL();
		}
	};

	@ProxyCodeSplit
	@NameToken(NameTokens.main)
	public interface MyProxy extends ProxyPlace<MainPresenter> {

	}

	@Inject
	public MainPresenter(EventBus eventBus, IMainView view, MyProxy proxy, PlaceManager placeManager,
			DispatchAsync dispatcher, DeleteDialogPresenter deleteDialogPresenter) {
		super(eventBus, view, proxy);
		this.eventBus = eventBus;
		this.placeManager = placeManager;
		this.deleteDialogPresenter = deleteDialogPresenter;
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	/**
	 * Use this in leaf presenters, inside their {@link #revealInParent} method.
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

	@Override
	protected void onBind() {
		super.onBind();
		initDataGrid();
		gererEvenements();
		enregistrerBoutonOuvPopup();

	}

	private void enregistrerBoutonOuvPopup() {
		registerHandler(getView().getPopupButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				openPopupSupp();
			}

		}));
	}

	private void gererEvenements() {
		registerHandler(eventBus.addHandler(UpdateDataGridEvent.TYPE, new UpdateDataGridHandler() {

			@Override
			public void onUpdateDataGrid(UpdateDataGridEvent event) {
				appelServiceIndex(event.getQuery());
			}
		}));
		registerHandler(eventBus.addHandler(SearchCompleteEvent.TYPE, new SearchCompleteHandler() {

			@Override
			public void onSearchComplete(SearchCompleteEvent event) {
				refreshDataGrid();
			}

		}));

	}

	private void initDataGrid() {

		dataGrid = getView().getDataGrid();
		dataGrid.setSize("1050px", "800px");
		dataGrid.addColumn(idColumn, "Sous-cat\u00E9gorie NAF3142");
		dataGrid.addColumn(valueColumn, "Libell\u00E9 NAF575");
		dataGrid.setRowData(liste);
		dataGrid.setColumnWidth(idColumn, "250px");
		dataGrid.setColumnWidth(valueColumn, "800px");
	}

	private void openPopupSupp() {
		RevealRootPopupContentEvent.fire(this, deleteDialogPresenter);
	}

	private void refreshDataGrid() {
		dataGrid.setRowData(liste);
		getView().getPanel1().setVisible(true);
		int hauteur = 35 + (25 * liste.size());
		//dataGrid.setSize("400px", String.valueOf(hauteur) + "px");
	}

	private void appelServiceIndex(String query) {
		liste.clear();
		dataService.getDataIndex(liste, query);
		dataGrid.setRowData(liste);
	}
}
