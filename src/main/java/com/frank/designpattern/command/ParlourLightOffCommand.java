package com.frank.designpattern.command;

/**
 * @author: somebody
 * @time: 2019-04-29 11:38
 * 客厅关灯命令
 * parlour
 * 英 [ˈpɑːlə(r)] 美 [ˈpɑːrlər]
 * <p>
 * n.
 * (私人住房的)起居室，客厅;(专营某种商品或业务的)店铺
 */
public class ParlourLightOffCommand implements Command {

    Light light;

    public ParlourLightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }
}
