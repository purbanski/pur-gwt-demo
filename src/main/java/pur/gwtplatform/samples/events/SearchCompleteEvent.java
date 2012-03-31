package pur.gwtplatform.samples.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class SearchCompleteEvent extends GwtEvent<SearchCompleteEvent.SearchCompleteHandler> {

	public static Type<SearchCompleteHandler> TYPE = new Type<SearchCompleteHandler>();

	public interface SearchCompleteHandler extends EventHandler {
		void onSearchComplete(SearchCompleteEvent event);
	}

	public SearchCompleteEvent() {
	}

	@Override
	protected void dispatch(SearchCompleteHandler handler) {
		handler.onSearchComplete(this);
	}

	@Override
	public Type<SearchCompleteHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<SearchCompleteHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new SearchCompleteEvent());
	}
}
