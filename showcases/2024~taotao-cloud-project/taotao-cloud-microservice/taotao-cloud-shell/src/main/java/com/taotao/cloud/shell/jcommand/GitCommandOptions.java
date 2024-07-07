package com.taotao.cloud.shell.jcommand;

import com.beust.jcommander.Parameter;

public class GitCommandOptions {

    @Parameter(names = {"help", "-help", "-h"},
        description = "查看帮助信息",
        order = 1,
        help = true)
    private boolean help;

    @Parameter(names = {"clone"},
        description = "克隆远程仓库数据",
        validateWith = UrlParameterValidator.class,
        order = 3,
        arity = 1)
    private String cloneUrl;

    @Parameter(names = {"version", "-version", "-v"},
        description = "显示当前版本号",
        order = 2)
    private boolean version = false;

    public boolean isHelp() {
        return help;
    }

    public boolean isVersion() {
        return version;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }
}
