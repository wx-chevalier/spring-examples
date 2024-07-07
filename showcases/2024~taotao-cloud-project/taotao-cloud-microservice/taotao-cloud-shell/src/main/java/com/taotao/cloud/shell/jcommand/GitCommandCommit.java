package com.taotao.cloud.shell.jcommand;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * git commit -m "desc"
 */
@Parameters(commandDescription = "提交文件", commandNames = "commit")
public class GitCommandCommit {
    public static final String COMMAND = "commit";

    @Parameter(names = {"-comment", "-m"},
        description = "请输入注释",
        arity = 1,
        required = true)
    private String comment;

    public String getComment() {
        return comment;
    }
}
