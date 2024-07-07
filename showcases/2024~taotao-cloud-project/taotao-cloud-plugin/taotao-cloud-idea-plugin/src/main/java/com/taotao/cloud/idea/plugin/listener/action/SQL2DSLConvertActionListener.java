package com.taotao.cloud.idea.plugin.listener.action;

import com.intellij.ui.EditorTextField;
import com.taotao.cloud.idea.plugin.notification.ToolkitNotifier;
import com.taotao.cloud.idea.plugin.utils.JsonFormatter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.http.HttpUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SQL2DSLConvertActionListener implements ActionListener {
    private static final String URL = "http://www.ischoolbar.com/EsParser/convert.php";
    private EditorTextField sqlTextField;
    private EditorTextField dslTextField;
    private JCheckBox formatCheckBox;

    public SQL2DSLConvertActionListener(EditorTextField sqlTextField, EditorTextField dslTextField, JCheckBox formatCheckBox) {
        this.sqlTextField = sqlTextField;
        this.dslTextField = dslTextField;
        this.formatCheckBox = formatCheckBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = sqlTextField.getText();
        if (StringUtils.isBlank(text)) {
            return;
        }
        String dslText;
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("mysqlStr", text);
            dslText = HttpUtil.post(URL, paramMap);
            dslText = StringEscapeUtils.unescapeJava(dslText.substring(1, dslText.length() - 1));
        } catch (Exception ex) {
            ToolkitNotifier.error("SQL convert to ElasticSearch DSL fail, please check SQL.");
            return;
        }

        if (formatCheckBox.isSelected()) {
            try {
                dslText = JsonFormatter.format(dslText);
            } catch (Exception ex) {
                ToolkitNotifier.error("Format DSL fail");
            }
        }
        this.dslTextField.setText(dslText);
    }
}
