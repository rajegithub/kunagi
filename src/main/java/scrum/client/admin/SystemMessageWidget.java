package scrum.client.admin;

import ilarkesto.gwt.client.Gwt;
import scrum.client.common.AScrumWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SystemMessageWidget extends AScrumWidget {

	private SimplePanel panel;
	private Label text = new Label();
	private Label expires = new Label();

	@Override
	protected Widget onInitialization() {
		expires.setStyleName("SystemMessageWidget-box-time");

		FlowPanel content = new FlowPanel();
		content.add(Gwt.createDiv("SystemMessageWidget-box-title", "Message from Admin"));
		content.add(text);
		content.add(expires);

		panel = Gwt.createDiv("SystemMessageWidget-box", content);
		panel.setVisible(false);
		return panel;
	}

	@Override
	protected void onUpdate() {
		SystemMessage message = cm.getSystemMessageManager().getSystemMessage();
		if (message.isActive()) {
			text.setText(message.getText());
			expires.setText(message.getExpiresAsString());
			panel.setVisible(true);
		} else {
			panel.setVisible(false);
		}
	}

}