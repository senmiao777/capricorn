package com.frank.designpattern.command;

/**
 * @author: somebody
 * @time: 2019-04-29 13:54
 */
public class AirconditionerOnCommand implements Command {

    Airconditioner airconditioner;

    @Override
    public void execute() {
        airconditioner.on();
    }

    public AirconditionerOnCommand(Airconditioner airconditioner) {
        this.airconditioner = airconditioner;
    }
}
