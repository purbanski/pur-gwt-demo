package pur.gwtplatform.samples.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import java.lang.String;
import com.google.gwt.event.shared.HasHandlers;

public class ErreurEvent extends GwtEvent<ErreurEvent.ErreurHandler> {

	public static Type<ErreurHandler> TYPE = new Type<ErreurHandler>();
	private String message;

	public interface ErreurHandler extends EventHandler {
		void onErreur(ErreurEvent event);
	}

	public ErreurEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	protected void dispatch(ErreurHandler handler) {
		handler.onErreur(this);
	}

	@Override
	public Type<ErreurHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ErreurHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, String message) {
		source.fireEvent(new ErreurEvent(message));
	}
}
