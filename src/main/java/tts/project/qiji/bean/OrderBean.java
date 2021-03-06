package tts.project.qiji.bean;

import java.io.Serializable;

/**
 * Created by shanghang on 2016/6/15.
 */
public class OrderBean implements Serializable {

    /**
     * order_id : 5
     * user_id : 11
     * amount : 150.00
     * true_amount : 150.00
     * order_number : 20160704100411733
     * pay_on : null
     * pay_type : 0
     * status : 1
     * name : 王奇利
     * phone : 15893038271
     * province : 上海市
     * city : 上海市
     * area : 浦东新区
     * address : 三林镇三舒路
     * fuwu : 其他服务
     * fuwu_time :
     * img :
     * dis :
     * coupon : null
     * return : null
     * is_need_server : 1
     * log : 0
     * lag : 0
     * type : 2
     * date : 1467597851
     * end_time : null
     * uptime : null
     * cancel_time : null
     * service_true_time : null
     */

    private String order_id;
    private String user_id;
    private String amount;
    private String true_amount;
    private String order_number;
    private Object pay_on;
    private String pay_type;
    private String status;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String area;
    private String address;
    private String fuwu;
    private String fuwu_time;
    private String img;
    private String dis;
    private Object coupon;
    private String service_type;

    private String is_need_server;
    private String log;
    private String lag;
    private String type;
    private String date;
    private Object end_time;
    private Object uptime;
    private Object cancel_time;
    private Object service_true_time;
    private EngineerBean engineer;
    /**
     * one_fuwu : 外接设备
     * two_fuwu : 网络设备
     * three_fuwu : 路由器
     * result_return : null
     */

    private String one_fuwu;
    private String two_fuwu;
    private String three_fuwu;
    private Object result_return;
    /**
     * engineer_dis : 预付角梳
     * engineer_img : /public/upload/imgs/577f02d170556.jpg,/public/upload/imgs/577f02d175666.jpg,/public/upload/imgs/577f02d176242.jpg,/public/upload/imgs/577f02d176cc8.jpg,/public/upload/imgs/577f02d1772d6.jpg
     * pingjia : null
     */

    private String engineer_dis;
    private String engineer_img;
    private Object pingjia;

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public EngineerBean getEngineer() {
        return engineer;
    }

    public void setEngineer(EngineerBean engineer) {
        this.engineer = engineer;
    }

    /**
     * engineer_state : null
     * engineer : {"engineer_id":null,"img":null,"phone":null,"xing":5}
     */

    private Object engineer_state;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTrue_amount() {
        return true_amount;
    }

    public void setTrue_amount(String true_amount) {
        this.true_amount = true_amount;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public Object getPay_on() {
        return pay_on;
    }

    public void setPay_on(Object pay_on) {
        this.pay_on = pay_on;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getFuwu() {
        return fuwu;
    }

    public void setFuwu(String fuwu) {
        this.fuwu = fuwu;
    }

    public String getFuwu_time() {
        return fuwu_time;
    }

    public void setFuwu_time(String fuwu_time) {
        this.fuwu_time = fuwu_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public Object getCoupon() {
        return coupon;
    }

    public void setCoupon(Object coupon) {
        this.coupon = coupon;
    }


    public String getIs_need_server() {
        return is_need_server;
    }

    public void setIs_need_server(String is_need_server) {
        this.is_need_server = is_need_server;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Object end_time) {
        this.end_time = end_time;
    }

    public Object getUptime() {
        return uptime;
    }

    public void setUptime(Object uptime) {
        this.uptime = uptime;
    }

    public Object getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(Object cancel_time) {
        this.cancel_time = cancel_time;
    }

    public Object getService_true_time() {
        return service_true_time;
    }

    public void setService_true_time(Object service_true_time) {
        this.service_true_time = service_true_time;
    }

    public Object getEngineer_state() {
        return engineer_state;
    }

    public void setEngineer_state(Object engineer_state) {
        this.engineer_state = engineer_state;
    }

    public String getOne_fuwu() {
        return one_fuwu;
    }

    public void setOne_fuwu(String one_fuwu) {
        this.one_fuwu = one_fuwu;
    }

    public String getTwo_fuwu() {
        return two_fuwu;
    }

    public void setTwo_fuwu(String two_fuwu) {
        this.two_fuwu = two_fuwu;
    }

    public String getThree_fuwu() {
        return three_fuwu;
    }

    public void setThree_fuwu(String three_fuwu) {
        this.three_fuwu = three_fuwu;
    }

    public Object getResult_return() {
        return result_return;
    }

    public void setResult_return(Object result_return) {
        this.result_return = result_return;
    }

    public String getEngineer_dis() {
        return engineer_dis;
    }

    public void setEngineer_dis(String engineer_dis) {
        this.engineer_dis = engineer_dis;
    }

    public String getEngineer_img() {
        return engineer_img;
    }

    public void setEngineer_img(String engineer_img) {
        this.engineer_img = engineer_img;
    }

    public Object getPingjia() {
        return pingjia;
    }

    public void setPingjia(Object pingjia) {
        this.pingjia = pingjia;
    }
}
