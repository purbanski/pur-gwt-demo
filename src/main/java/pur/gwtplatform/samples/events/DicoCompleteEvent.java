package pur.gwtplatform.samples.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;

import pur.gwtplatform.samples.model.Data;

import com.google.gwt.event.shared.HasHandlers;

public class DicoCompleteEvent extends GwtEvent<DicoCompleteEvent.InsertCompleteHandler> {

	public static Type<InsertCompleteHandler> TYPE = new Type<InsertCompleteHandler>();
	private Data data;

	public interface InsertCompleteHandler extends EventHandler {
		void onInsertComplete(DicoCompleteEvent event);
	}

	public DicoCompleteEvent() {
		super();
	}

	public DicoCompleteEvent(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@Override
	protected void dispatch(InsertCompleteHandler handler) {
		handler.onInsertComplete(this);
	}

	@Override
	public Type<InsertCompleteHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<InsertCompleteHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, Data data) {
		source.fireEvent(new DicoCompleteEvent(data));
	}
}
