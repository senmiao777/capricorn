package com.frank.designpattern;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/3
 * <p>
 * 被观察者
 */
public interface Observable {
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


    enum OperationType{
        /**
         * 吃
         */
        EAT,
        DRINK,
        SLEEP;
    }

    class Notice{
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
