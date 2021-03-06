package scrum.client.admin;

import ilarkesto.gwt.client.AFieldValueWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.IntegerEditorWidget;
import ilarkesto.gwt.client.editor.TextEditorWidget;
import scrum.client.ScrumGwt;
import scrum.client.common.AScrumWidget;
import scrum.client.common.UserGuideWidget;
import scrum.client.project.Project;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PunishmentsWidget extends AScrumWidget {

	@Override
	protected Widget onInitialization() {

		PagePanel page = new PagePanel();
		Project project = getCurrentProject();

		TableBuilder main = ScrumGwt.createFieldTable();

		main.add(Gwt.createDiv("PunishmentsWidget-tableHeader", "User"));
		main.add(Gwt.createDiv("PunishmentsWidget-tableHeader", "Misconducts"));
		main.add(Gwt.createDiv("PunishmentsWidget-tableHeader", "Punishment"));
		main.nextRow();

		for (User u : project.getParticipants()) {
			Label nameLabel = new Label(u.getName());
			nameLabel.getElement().getStyle().setProperty("color", project.getUserConfig(u).getColor());
			main.add(nameLabel);
			main.add(new IntegerEditorWidget(u.getProjectConfig().getMisconductsModel()));
			main.add(new PunishmentViewer(u.getProjectConfig(), project));
			main.nextRow();
		}

		page.addHeader("Courtroom");
		page.addSection(main.createTable());

		if (project.isScrumMaster(getCurrentUser())) {
			TableBuilder settings = ScrumGwt.createFieldTable();
			settings.addFieldRow("Factor", new IntegerEditorWidget(project.getPunishmentFactorModel()));
			settings.addFieldRow("Unit", new TextEditorWidget(project.getPunishmentUnitModel()));
			page.addHeader("Punishment Configuration");
			page.addSection(settings.createTable());
		}

		page.addSection(new UserGuideWidget(getLocalizer().views().courtRoom(), true, getCurrentUser()
				.getHideUserGuideCourtroomModel()));

		return page;
	}

	private class PunishmentViewer extends AFieldValueWidget {

		private ProjectUserConfig userConfig;
		private Project project;

		public PunishmentViewer(ProjectUserConfig userConfig, Project project) {
			super();
			this.userConfig = userConfig;
			this.project = project;
		}

		@Override
		protected void onUpdate() {
			setText(userConfig.getMisconducts() * project.getPunishmentFactor() + " " + project.getPunishmentUnit());
		}
	}

}
