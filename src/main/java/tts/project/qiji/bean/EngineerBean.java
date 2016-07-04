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
}
