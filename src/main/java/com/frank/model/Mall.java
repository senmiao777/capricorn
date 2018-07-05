package com.frank.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by frank on 2018/2/2.
 *
 */
public class Mall {


    /**
     * 试衣间，用来模拟有限的资源
     */
    static class FittingRoom{
        private int num;
        public FittingRoom(int num){
            this.num = num;
        }
        @Override
        public String toString(){
            return new StringBuilder("试衣间").append(num).append("正在被使用").toString();
        }
    }

    private boolean[] used = new boolean[5];
    private List<FittingRoom> rooms = Lists.newArrayList(new FittingRoom(1),
            new FittingRoom(2),new FittingRoom(3),new FittingRoom(4),new FittingRoom(5),new FittingRoom(1));

    /**
     * 5个试衣间，公平策略竞争
     */
    private Semaphore room = new Semaphore(5,true);
    public FittingRoom getFittingRoom(){

        return null;
    }

}
