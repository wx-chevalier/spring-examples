package com.taotao.cloud.shell.commond;

import jakarta.validation.constraints.Size;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

/**
 * 对参数进行校验
 */
@ShellComponent
public class AvailableCommand {

    /**
     * 添加验证
     * @param password
     * 输入：change-password pass
     * 输出：--password string : 个数必须在8和40之间 (You passed 'pass')
     *
     * 输入：change-password thispassword
     * 输出：Password successfully set to thispassword
     * @return
     */
    @ShellMethod("只能输入长度为8至40的内容")
    public String changePassword(@Size(min = 8, max = 40) String password) {
        return "Password successfully set to " + password;
    }


    private boolean connected;

    @ShellMethod("设置链接状态为true")
    public void connect() {
        connected = true;
    }

    /**
     * 输入：download
     * 输出：
     * Command 'download' exists but is not currently available because 没有进行链接
     * Details of the error have been omitted. You can use the stacktrace command to print the full stacktrace.
     *
     * 第二次输入
     * 输入:>connect
     * 输出:>download
     */
    @ShellMethod("必须链接后才能执行的方法")
    public void download() {
    }

    public Availability downloadAvailability() {
        return connected
                ? Availability.available()
                : Availability.unavailable("没有进行链接");
    }

    private boolean connected2;

    @ShellMethod("设置链接状态2为true")
    public void connect2() {
        connected2 = true;
    }

    /**
     * 为命令指定校验方法
     */
    @ShellMethod("必须链接2链接后才能执行的方法")
    @ShellMethodAvailability("availabilityCheck")
    public void disconnect2() {
    }

    /**
     * 为校验方法指定需要校验的命令
     * @return
     */
    @ShellMethodAvailability({"download2", "disconnect2"})
    public Availability availabilityCheck() {
        return connected2
                ? Availability.available()
                : Availability.unavailable("没有进行链接");
    }


}
