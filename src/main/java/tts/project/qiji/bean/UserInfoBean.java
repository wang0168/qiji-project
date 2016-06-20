package tts.project.qiji.bean;

/**
 * Created by shanghang on 2016/6/7.
 */
public class UserInfoBean {
    /**
     * user_id : 2
     * token : 5767b03a5d3ae
     * phone : 15801075991
     * pwd : e10adc3949ba59abbe56e057f20f883e
     * img : http://qj.tstmobile.com/public/admin/touxiang.jpg
     * sex : 0
     * type : 1
     * state : 1
     * username : null
     * is_company : null
     * province : null
     * city : null
     * area : null
     * address : null
     * fuwu_type : null
     * shenhe : 1
     * intime : 2016-06-20 16:58:34
     * uptime : null
     */

    private String user_id;
    private String token;
    private String phone;
    private String pwd;
    private String img;
    private String sex;
    private String type;
    private String state;
    private Object username;
    private Object is_company;
    private Object province;
    private Object city;
    private Object area;
    private Object address;
    private Object fuwu_type;
    private String shenhe;
    private String intime;
    private Object uptime;
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getIs_company() {
        return is_company;
    }

    public void setIs_company(Object is_company) {
        this.is_company = is_company;
    }

    public Object getProvince() {
        return province;
    }

    public void setProvince(Object province) {
        this.province = province;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getFuwu_type() {
        return fuwu_type;
    }

    public void setFuwu_type(Object fuwu_type) {
        this.fuwu_type = fuwu_type;
    }

    public String getShenhe() {
        return shenhe;
    }

    public void setShenhe(String shenhe) {
        this.shenhe = shenhe;
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
}
