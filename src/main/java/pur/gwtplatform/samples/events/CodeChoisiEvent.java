package pur.gwtplatform.samples.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import java.lang.String;
import com.google.gwt.event.shared.HasHandlers;

public class CodeChoisiEvent extends GwtEvent<CodeChoisiEvent.CodeChoisiHandler> {

	public static Type<CodeChoisiHandler> TYPE = new Type<CodeChoisiHandler>();
	private String code;

	public interface CodeChoisiHandler extends EventHandler {
		void onCodeChoisi(CodeChoisiEvent event);
	}

	public CodeChoisiEvent(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	@Override
	protected void dispatch(CodeChoisiHandler handler) {
		handler.onCodeChoisi(this);
	}

	@Override
	public Type<CodeChoisiHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<CodeChoisiHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, String code) {
		source.fireEvent(new CodeChoisiEvent(code));
	}
}
