package pur.gwtplatform.samples.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;

import pur.gwtplatform.samples.model.DicoResult;

import com.google.gwt.event.shared.HasHandlers;

public class DicoCompleteEvent extends GwtEvent<DicoCompleteEvent.DicoCompleteHandler> {

	public static Type<DicoCompleteHandler> TYPE = new Type<DicoCompleteHandler>();
	private DicoResult dicoResult;

	public interface DicoCompleteHandler extends EventHandler {
		void onInsertComplete(DicoCompleteEvent event);
	}

	public DicoCompleteEvent() {
		super();
	}

	public DicoCompleteEvent(DicoResult dicoResult) {
		this.dicoResult = dicoResult;
	}

	public DicoResult getDicoResult() {
		return dicoResult;
	}

	@Override
	protected void dispatch(DicoCompleteHandler handler) {
		handler.onInsertComplete(this);
	}

	@Override
	public Type<DicoCompleteHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<DicoCompleteHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, DicoResult data) {
		source.fireEvent(new DicoCompleteEvent(data));
	}
}
