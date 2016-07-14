package tts.project.qiji.bean;

import java.io.Serializable;

/**
 * Created by shanghang on 2016/7/4.
 */
public class EngineerBean implements Serializable{
    /**
     * engineer_id : null
     * img : null
     * phone : null
     * xing : 5
     */

    private Object engineer_id;
    private Object img;
    private Object phone;
    private int xing;
    /**
     * engineer_order_id : 1
     * order_id : 23
     * user_id : 10
     * status : 4
     * type : 1
     * intime : 0
     * ordertime : 1467798088
     * reason : null
     */

    private String engineer_order_id;
    private String order_id;
    private String user_id;
    private String status;
    private String type;
    private String intime;
    private String ordertime;
    private Object reason;
    /**
     * username : 布局图
     * log : 121.431589
     * lag : 31.186743
     */

    private String username;
    private String log;
    private String lag;

    public Object getEngineer_id() {
        return engineer_id;
    }

    public void setEngineer_id(Object engineer_id) {
        this.engineer_id = engineer_id;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public int getXing() {
        return xing;
    }

    public void setXing(int xing) {
        this.xing = xing;
    }

    public String getEngineer_order_id() {
        return engineer_order_id;
    }

    public void setEngineer_order_id(String engineer_order_id) {
        this.engineer_order_id = engineer_order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLag() {
        return lag;
    }

    public void setLag(String lag) {
        this.lag = lag;
    }
}
