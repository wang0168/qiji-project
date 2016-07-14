package tts.project.qiji.bean;

import java.io.Serializable;

/**
 * Created by shanghang on 2016/6/15.
 */
public class EvaluateBean implements Serializable{

    /**
     * pingjia_id : 5
     * order_id : 33
     * engineer_id : 10
     * xing : 3
     * content : 还不错，下次还来
     * intime : 1468216280
     * img : http://qj.tstmobile.com/public/admin/touxiang.jpg
     * name : 好
     */

    private String pingjia_id;
    private String order_id;
    private String engineer_id;
    private String xing;
    private String content;
    private String intime;
    private String img;
    private String name;

    public String getPingjia_id() {
        return pingjia_id;
    }

    public void setPingjia_id(String pingjia_id) {
        this.pingjia_id = pingjia_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getEngineer_id() {
        return engineer_id;
    }

    public void setEngineer_id(String engineer_id) {
        this.engineer_id = engineer_id;
    }

    public String getXing() {
        return xing;
    }

    public void setXing(String xing) {
        this.xing = xing;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
