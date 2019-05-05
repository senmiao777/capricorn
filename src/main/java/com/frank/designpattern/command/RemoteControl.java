package com.frank.designpattern.command;

/**
 * @author: somebody
 * @time: 2019-04-29 11:53
 * 遥控器类，负责调用命令
 * 只需要知道要发出什么命令，不需要知道命令具体是怎么被执行的
 * 客户端和接收者（真正的执行者）解耦
 */
public class RemoteControl {
    /**
     * 持有命令对象
     */
    Command command;

    /**
     * 开卧室的灯
     */
    public void openParlourLight() {
        command.execute();
    }

    /**
     * 关卧室的灯
     */
    public void closeParlourLight() {
        command.execute();
    }

    /**
     * 开空调
     */
    public void openAirconditioner() {
        command.execute();
    }


    /**
     * 开空调，热水器，客厅灯。。。
     * 宏命令
     */
    public void arriveHome() {
        command.execute();
    }


    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
