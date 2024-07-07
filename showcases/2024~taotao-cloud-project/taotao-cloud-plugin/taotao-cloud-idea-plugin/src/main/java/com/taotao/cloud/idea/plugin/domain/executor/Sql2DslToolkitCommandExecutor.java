package com.taotao.cloud.idea.plugin.domain.executor;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.util.ui.JBDimension;

import com.taotao.cloud.idea.plugin.domain.ToolkitCommand;
import com.taotao.cloud.idea.plugin.ui.Sql2DslUI;
import javax.swing.*;

public class Sql2DslToolkitCommandExecutor extends AbstractToolkitCommandExecutor {

    @Override
    public boolean support(ToolkitCommand command) {
        return ToolkitCommand.Sql2dsl.equals(command);
    }

    @Override
    public void execute(ToolkitCommand command, DataContext dataContext) {
        Project project = getProject(dataContext);

        JPanel panel = new Sql2DslUI(project).getPanel();

        JBDimension dimension = new JBDimension(400, 700);
        JBPopup popup = createPopup(command.getDescription(), dimension, AllIcons.Providers.Mysql, panel);
        popup.show(getRelativePoint(dataContext, dimension));
    }
}
