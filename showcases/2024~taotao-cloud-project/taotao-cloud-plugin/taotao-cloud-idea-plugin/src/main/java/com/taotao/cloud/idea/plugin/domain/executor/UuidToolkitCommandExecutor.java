package com.taotao.cloud.idea.plugin.domain.executor;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.util.ui.JBDimension;

import com.taotao.cloud.idea.plugin.domain.ToolkitCommand;
import com.taotao.cloud.idea.plugin.ui.UuidUI;
import javax.swing.*;

public class UuidToolkitCommandExecutor extends AbstractToolkitCommandExecutor {
    @Override
    public boolean support(ToolkitCommand command) {
        return ToolkitCommand.UUID.equals(command);
    }

    @Override
    public void execute(ToolkitCommand command, DataContext dataContext) {
        Project project = getProject(dataContext);

        JPanel panel = new UuidUI(project).getPanel();

        JBDimension dimension = new JBDimension(550, 250);
        JBPopup popup = createPopup(command.getDescription(), dimension, AllIcons.FileTypes.Config, panel);
        popup.show(getRelativePoint(dataContext, dimension));
    }
}
