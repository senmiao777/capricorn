package com.frank.designpattern.command;

/**
 * @author: somebody
 * @time: 2019-04-30 10:21
 * 宏命令，包含多个命令的命令
 */
public class ArriveHomeCommand implements Command {

    private Light light;
    private Airconditioner airconditioner;

    public ArriveHomeCommand(Light light, Airconditioner airconditioner) {
        this.light = light;
        this.airconditioner = airconditioner;
    }

    @Override
    public void execute() {
        light.on();
        airconditioner.on();
    }
}
