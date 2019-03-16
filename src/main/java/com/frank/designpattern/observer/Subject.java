package com.frank.designpattern.observer;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/3
 * <p>
 * 被订阅的主题，即被观察者
 */
public interface Subject {
    /**
     * 添加观察者
     *
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * 删除观察者
     *
     * @param observer
     */
    void deleteObserver(Observer observer);

    /**
     * 通知观察者
     *
     * @param msg
     */
    void notifyObservers(Notice msg);

    /**
     * 吃方法
     * @param food
     */
    void eat(String food);

    /**
     * 睡方法
     * @param time
     */
    void sleep(String time);


    enum OperationType {
        /**
         * 吃
         */
        EAT,
        DRINK,
        SLEEP;
    }

    class Notice {
        private OperationType operationType;
        private String observable;
        private Object obj;


        public Notice(OperationType operationType, String observable, Object obj) {
            this.operationType = operationType;
            this.observable = observable;
            this.obj = obj;
        }

        public OperationType getOperationType() {
            return operationType;
        }

        public String getObservable() {
            return observable;
        }

        public Object getObj() {
            return obj;
        }
    }
}
