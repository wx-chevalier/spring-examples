package com.taotao.cloud.idea.plugin.listener.action;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenFileActionListener implements ActionListener {

    private Project project;
    private EditorTextField logoTextField;

    public OpenFileActionListener(Project project, EditorTextField logoTextField) {
        this.project = project;
        this.logoTextField = logoTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooserDescriptor chooserDescriptor = new FileChooserDescriptor(true, false, false, false, false, false);
        VirtualFile virtualFile = FileChooser.chooseFile(chooserDescriptor, this.project, null);
        if (virtualFile != null) {
            logoTextField.setText(virtualFile.getPath());
        }
    }
}
