package tts.project.qiji.bean;

import java.util.List;

/**
 * Created by shanghang on 2016/7/13.
 */
public class UserDetailBean {
    /**
     * user_id : 19
     * img : http://qj.tstmobile.com/public/admin/touxiang.jpg
     * username : 王奇利企业用户
     * phone : 15893038273
     * companyname2 : 上海整案（长清路店）
     * province : null
     * city : null
     * area : null
     * address : null
     * bill : {"sum":"0.01","count":"1","order":[{"order_id":"41","one_fuwu":"","two_fuwu":"外接设备","three_fuwu":"POS机","date":"2016-07-12 16:49","dis":"the","img":"","fuwu_time":"","province":"上海市","city":"上海市","area":"徐汇区","address":"斜土路2611号","true_amount":"0.01"}]}
     */

    private String user_id;
    private String img;
    private String username;
    private String phone;
    private String companyname2;
    private Object province;
    private Object city;
    private Object area;
    private Object address;
    /**
     * sum : 0.01
     * count : 1
     * order : [{"order_id":"41","one_fuwu":"","two_fuwu":"外接设备","three_fuwu":"POS机","date":"2016-07-12 16:49","dis":"the","img":"","fuwu_time":"","province":"上海市","city":"上海市","area":"徐汇区","address":"斜土路2611号","true_amount":"0.01"}]
     */

    private BillBean bill;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyname2() {
        return companyname2;
    }

    public void setCompanyname2(String companyname2) {
        this.companyname2 = companyname2;
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

    public BillBean getBill() {
        return bill;
    }

    public void setBill(BillBean bill) {
        this.bill = bill;
    }

    public static class BillBean {
        private String sum;
        private String count;
        private List<OrderBean> order;

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
