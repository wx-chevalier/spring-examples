package com.taotao.cloud.idea.plugin.listener.action;

import com.intellij.ui.EditorTextField;
import com.taotao.cloud.idea.plugin.notification.ToolkitNotifier;
import com.taotao.cloud.idea.plugin.utils.ParseJsonStrUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.http.HttpUtil;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PhoneAddressSearchActionListener implements ActionListener {
    private static final String URL = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";

    private EditorTextField editorTextField;
    private DefaultTableModel tableModel;

    public PhoneAddressSearchActionListener(EditorTextField editorTextField, DefaultTableModel tableModel) {
        this.editorTextField = editorTextField;
        this.tableModel = tableModel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        String text = editorTextField.getText().trim();
        if (StringUtils.isBlank(text)) {
            return;
        }

        try {
            String body = HttpUtil.get(URL + text);
            Map<String, Object> data = (Map<String, Object>) ParseJsonStrUtils
                    .parseJson(body.replace("__GetZoneResult_ =", "").trim());
            tableModel.setDataVector(
                    new Object[][]{
                            {"手机号码段", data.get("mts")},
                            {"省份", data.get("province")},
                            {"卡号归属地", data.get("carrier")},
                            {"服务商", data.get("catName")}
                    }, new Object[]{"属性", "值"});
        } catch (Exception ex) {
            ToolkitNotifier.error("Search phone address fail");
        }

    }
}
