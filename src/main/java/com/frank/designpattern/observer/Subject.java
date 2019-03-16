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




    enum OperationType {
        /**
         * 吃
         */
        EAT,
        DRINK,
        UPDATE_AGE,
        SLEEP;
    }

    class Notice {
        private OperationType operationType;
        private String subject;
        private Object obj;

        public Notice(OperationType operationType, String subject, Object obj) {
            this.operationType = operationType;
            this.subject = subject;
            this.obj = obj;
        }

        public OperationType getOperationType() {
            return operationType;
        }

        public Object getObj() {
            return obj;
        }

        public void setOperationType(OperationType operationType) {
            this.operationType = operationType;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }
    }
}
