package tts.project.qiji.bean;

import java.util.List;

/**
 * Created by shanghang on 2016/7/14.
 */
public class UserManagerDataBean {
    /**
     * company_id : 1
     * logo :
     * name : 吴
     * mobile : 15801075888
     * count : 2
     * sign : [{"sign_id":"1","qy_user_id":"1","gys_user_id":"2","fuwu_id":"59","start_time":"1970年01月01日","end_time":"1970年01月01日","intime":"0","uptime":null,"gys_name":"上海整案2","fuwu_name":"POS机"}]
     */

    private String company_id;
    private String logo;
    private String name;
    private String mobile;
    private String count;
    /**
     * sign_id : 1
     * qy_user_id : 1
     * gys_user_id : 2
     * fuwu_id : 59
     * start_time : 1970年01月01日
     * end_time : 1970年01月01日
     * intime : 0
     * uptime : null
     * gys_name : 上海整案2
     * fuwu_name : POS机
     */

    private List<SignBean> sign;
    /**
     * title : 上海整案
     * province : 上海市
     * city : 上海市
     * area : 浦东新区
     * address : 长青路
     */

    private String title;
    private String province;
    private String city;
    private String area;
    private String address;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<SignBean> getSign() {
        return sign;
    }

    public void setSign(List<SignBean> sign) {
        this.sign = sign;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static class SignBean {
        private String sign_id;
        private String qy_user_id;
        private String gys_user_id;
        private String fuwu_id;
        private String start_time;
        private String end_time;
        private String intime;
        private Object uptime;
        private String gys_name;
        private String fuwu_name;
        private String qy_name;

        public String getQy_name() {
            return qy_name;
        }

        public void setQy_name(String qy_name) {
            this.qy_name = qy_name;
        }

        public String getSign_id() {
            return sign_id;
        }

        public void setSign_id(String sign_id) {
            this.sign_id = sign_id;
        }

        public String getQy_user_id() {
            return qy_user_id;
        }

        public void setQy_user_id(String qy_user_id) {
            this.qy_user_id = qy_user_id;
        }

        public String getGys_user_id() {
            return gys_user_id;
        }

        public void setGys_user_id(String gys_user_id) {
            this.gys_user_id = gys_user_id;
        }

        public String getFuwu_id() {
            return fuwu_id;
        }

        public void setFuwu_id(String fuwu_id) {
            this.fuwu_id = fuwu_id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
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

        public String getGys_name() {
            return gys_name;
        }

        public void setGys_name(String gys_name) {
            this.gys_name = gys_name;
        }

        public String getFuwu_name() {
            return fuwu_name;
        }

        public void setFuwu_name(String fuwu_name) {
            this.fuwu_name = fuwu_name;
        }
    }
}
