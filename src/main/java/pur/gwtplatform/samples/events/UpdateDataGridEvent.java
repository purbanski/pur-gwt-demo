package pur.gwtplatform.samples.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import java.lang.String;
import com.google.gwt.event.shared.HasHandlers;

public class UpdateDataGridEvent extends GwtEvent<UpdateDataGridEvent.UpdateDataGridHandler> {

	public static Type<UpdateDataGridHandler> TYPE = new Type<UpdateDataGridHandler>();
	private String query;

	public interface UpdateDataGridHandler extends EventHandler {
		void onUpdateDataGrid(UpdateDataGridEvent event);
	}

	public UpdateDataGridEvent(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	@Override
	protected void dispatch(UpdateDataGridHandler handler) {
		handler.onUpdateDataGrid(this);
	}

	@Override
	public Type<UpdateDataGridHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<UpdateDataGridHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, String query) {
		source.fireEvent(new UpdateDataGridEvent(query));
	}
}
