package com.taotao.cloud.shell.commond;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * 多参数命令
 */
@ShellComponent
public class ArgCommands {

    /**
     * 多参数 可以使用 --arg value 指定参数名称
     * 输入：echo-int --b 1 --a 2 --c 3
     * 输出：You said a=2, b=1, c=c
     *
     * 输入：echo-int 1 2 3
     * 输出：You said a=1, b=2, c=3
     * @return
     */
    @ShellMethod("通过明明参数名称，来指定输入的数据对应的参数名称")
    public String echoInt(int a, int b, int c) {
        return String.format("You said a=%d, b=%d, c=%d", a, b, c);
    }


    /**
     *
     * 输入：echo-int2 1 2  3
     * 输出：You said a=1, b=2, c=3
     *
     * 输入：echo-int2 -b 2 -a 3 --third 4
     * 输出：You said a=3, b=2, c=4
     * @return
     */
    @ShellMethod(value = "通过明明参数名称，强制的指定输入的数据对应的参数名称", prefix="-")
    public String echoInt2(int a, int b, @ShellOption("--third") int c) {
        return String.format("You said a=%d, b=%d, c=%d", a, b, c);
    }

    /**
     * 设置默认值
     * 输入：echo-string --who ' string is "name"'
     * 输出：input: string is "name"
     * @return
     */
    @ShellMethod("输入字符串")
    public String echoString(@ShellOption(defaultValue="World") String who) {
        return "input:" + who;
    }

    /**
     * 数组类参数
     * 输入：echo-array 2 3 4
     * 输出：input:2.0,3.0,4.0
     * @return
     */
    @ShellMethod("输入数组")
    public String echoArray(@ShellOption(arity=3) float[] numbers) {
        return "input:" + numbers[0] + "," + numbers[1] + "," + numbers[2];
    }

    /**
     * boolean类型参数,boolean 类型参数当你设置了参数会返回true
     * 输入：echo-boolean --force
     * 输出：input:true
     *
     * 输入：echo-boolean
     * 输出：input:false
     * @return
     */
    @ShellMethod("Terminate the system.")
    public String echoBoolean(boolean force) {
        return "input:" + force;
    }

}
