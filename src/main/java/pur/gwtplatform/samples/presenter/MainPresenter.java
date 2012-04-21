package pur.gwtplatform.samples.presenter;

import pur.gwtplatform.samples.events.CodeChoisiEvent;
import pur.gwtplatform.samples.events.CodeChoisiEvent.CodeChoisiHandler;
import pur.gwtplatform.samples.events.ErreurEvent;
import pur.gwtplatform.samples.modules.NameTokens;
import pur.gwtplatform.samples.views.IMainView;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

public class MainPresenter extends Presenter<IMainView, MainPresenter.MyProxy> {
	private EventBus eventBus;
	private final PlaceManager placeManager;
	private RechercheDialogPresenter rechercheDialogPresenter;

	@ProxyCodeSplit
	@NameToken(NameTokens.main)
	public interface MyProxy extends ProxyPlace<MainPresenter> {

	}

	@Inject
	public MainPresenter(EventBus eventBus, IMainView view, MyProxy proxy, PlaceManager placeManager, DispatchAsync dispatcher,
			RechercheDialogPresenter rechercheDialogPresenter) {
		super(eventBus, view, proxy);
		this.eventBus = eventBus;
		this.placeManager = placeManager;
		this.rechercheDialogPresenter = rechercheDialogPresenter;
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
		gererEvenements();
		enregistrerBoutonOuvPopup();
		RootPanel root = RootPanel.get("gwt");
		root.add(getView().getRechercheImage());
	}

	private void enregistrerBoutonOuvPopup() {
		registerHandler(getView().getRechercheImage().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				openPopupRecherche();
			}

		}));
	}

	private void gererEvenements() {
		registerHandler(eventBus.addHandler(CodeChoisiEvent.TYPE, new CodeChoisiHandler() {
			@Override
			public void onCodeChoisi(CodeChoisiEvent event) {
				//on met met à jour le code de la page HTML avec le code choisi
				Document.get().getElementById("codePck").setAttribute("value", event.getCode());
			}
		}));

		registerHandler(eventBus.addHandler(ErreurEvent.TYPE, new ErreurEvent.ErreurHandler() {
			@Override
			public void onErreur(ErreurEvent event) {
				// si une erreur survient on affiche une popup d'erreur
				placeManager.revealPlace(new PlaceRequest(NameTokens.error));
				Window.alert("Une erreur grave est survenue: " + event.getMessage());
			}
		}));

	}

	private void openPopupRecherche() {
		RevealRootPopupContentEvent.fire(this, rechercheDialogPresenter);
	}
}
