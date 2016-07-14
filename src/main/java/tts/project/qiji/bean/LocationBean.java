package tts.project.qiji.bean;

import java.io.Serializable;

/**
 * Created by shanghang on 2016/7/8.
 */
public class LocationBean implements Serializable {
    /**
     * distance_id : 1
     * order_id : 1
     * user_id : 10
     * distance : 5
     * intime : 0
     * uptime : null
     * name : null
     * log : null
     * lag : null
     */

    private String distance_id;
    private String order_id;
    private String user_id;
    private String distance;
    private String intime;
    private Object uptime;
    private Object name;
    private Object log;
    private Object lag;

    public String getDistance_id() {
        return distance_id;
    }

    public void setDistance_id(String distance_id) {
        this.distance_id = distance_id;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public Object getUptime() {
        return uptime;
    }

    public void setUptime(Object uptime) {
        this.uptime = uptime;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getLog() {
        return log;
    }

    public void setLog(Object log) {
        this.log = log;
    }

    public Object getLag() {
        return lag;
    }

    public void setLag(Object lag) {
        this.lag = lag;
    }
}
