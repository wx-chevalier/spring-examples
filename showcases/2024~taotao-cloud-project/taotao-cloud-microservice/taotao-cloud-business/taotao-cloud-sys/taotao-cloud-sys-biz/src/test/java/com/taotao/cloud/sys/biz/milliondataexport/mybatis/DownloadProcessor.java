package com.taotao.cloud.sys.biz.milliondataexport.mybatis;

import com.taotao.cloud.common.utils.log.LogUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownloadProcessor {
    private final HttpServletResponse response;

    public DownloadProcessor(HttpServletResponse response) {
        this.response = response;
        String fileName = System.currentTimeMillis() + ".csv";
        this.response.addHeader("Content-Type", "application/csv");
        this.response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        this.response.setCharacterEncoding("UTF-8");
    }

    public <E> void processData(E record) {
        try {
            response.getWriter().write(record.toString()); // 如果是要写入csv,需要重写toString,属性通过","分割
            response.getWriter().write("\n");
        } catch (IOException e) {
            LogUtils.error(e);
        }
    }
}
