package tts.project.qiji.bean;

import java.io.Serializable;

/**
 * Created by shanghang on 2016/7/11.
 */
public class CustomServiceBean implements Serializable {
    /**
     * service_phone_id : 2
     * phone : 15801075998
     * intime : 0
     * status : 2
     */

    private String service_phone_id;
    private String phone;
    private String intime;
    private String status;

    public String getService_phone_id() {
        return service_phone_id;
    }

    public void setService_phone_id(String service_phone_id) {
        this.service_phone_id = service_phone_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
