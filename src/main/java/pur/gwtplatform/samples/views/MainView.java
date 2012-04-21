package pur.gwtplatform.samples.views;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class MainView extends ViewImpl implements IMainView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, MainView> {
	}

	@Inject
	public MainView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	@UiField HTMLPanel htmlPanel;
	@UiField Image rechercheImage;


	public HTMLPanel getHtmlPanel() {
		return htmlPanel;
	}

	public Image getRechercheImage() {
		return rechercheImage;
	}



}
