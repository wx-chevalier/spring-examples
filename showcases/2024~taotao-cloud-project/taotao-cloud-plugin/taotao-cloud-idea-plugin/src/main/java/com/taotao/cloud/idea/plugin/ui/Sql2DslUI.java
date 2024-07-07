package com.taotao.cloud.idea.plugin.ui;

import com.intellij.json.JsonLanguage;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.ui.EditorSettingsProvider;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.LanguageTextField;
import com.taotao.cloud.idea.plugin.listener.action.CopyContentActionListener;
import com.taotao.cloud.idea.plugin.listener.action.SQL2DSLConvertActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class Sql2DslUI {

	private JPanel panel;
	private EditorTextField sqlTextField;
	private EditorTextField dslTextField;
	private JButton convert;
	private JCheckBox jsonFormatCheckBox;
	private JButton copy;

	private Project project;

	public Sql2DslUI(Project project) {
		this.project = project;
		this.copy.addActionListener(new CopyContentActionListener(this.dslTextField));
		this.convert.addActionListener(
			new SQL2DSLConvertActionListener(this.sqlTextField, this.dslTextField,
				this.jsonFormatCheckBox));
	}

	private void createUIComponents() {
		// TODO: place custom component creation code here
		this.sqlTextField = new LanguageTextField(PlainTextLanguage.INSTANCE, project, "", false);
		this.sqlTextField.addSettingsProvider(getSqlEditorSettingsProvider());
		this.dslTextField = new LanguageTextField(JsonLanguage.INSTANCE, project, "", false);
		this.dslTextField.addSettingsProvider(getDslEditorSettingsProvider());
	}

	private EditorSettingsProvider getSqlEditorSettingsProvider() {
		return editor -> {
			EditorSettings settings = editor.getSettings();
			settings.setFoldingOutlineShown(true);
			settings.setLineNumbersShown(true);
			settings.setLineMarkerAreaShown(true);
			settings.setIndentGuidesShown(true);
			settings.setWheelFontChangeEnabled(true);
			editor.setHorizontalScrollbarVisible(true);
			editor.setVerticalScrollbarVisible(true);
		};
	}

	private EditorSettingsProvider getDslEditorSettingsProvider() {
		return editor -> {
			EditorSettings settings = editor.getSettings();
			settings.setFoldingOutlineShown(true);
			settings.setLineNumbersShown(true);
			settings.setLineMarkerAreaShown(true);
			settings.setIndentGuidesShown(true);
			settings.setWheelFontChangeEnabled(true);
			editor.setHorizontalScrollbarVisible(true);
			editor.setVerticalScrollbarVisible(true);
		};
	}

	public JPanel getPanel() {
		return panel;
	}
}
