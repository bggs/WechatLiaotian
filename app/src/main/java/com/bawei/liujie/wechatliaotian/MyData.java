package com.bawei.liujie.wechatliaotian;

/**
 * 类的作用:
 * author: 刘婕
 * date:2017/9/9
 */

public class MyData {
    private String date;//消息日期
    private String message;//消息内容
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private boolean isComMeg = true;// 是否为收到的消息

    public MyData() {
    }

    public MyData( String date, String message, boolean isComMeg) {
        this.date = date;
        this.message = message;
        this.isComMeg = isComMeg;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
        isComMeg = isComMsg;
    }
}
