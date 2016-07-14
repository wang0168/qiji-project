package tts.project.qiji.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shanghang on 2016/6/7.
 */
public class HomeSortBean implements Serializable {

    private String name;
    private String imgPath;
    private int imgId;
    /**
     * fuwu_id : 1
     * img : /public/upload/imgs/32dfe515498580650130233e43bf78e4.jpg
     * price : 25
     * state : 1
     * fid : 0
     * px : 1
     * xiadan : 2
     * intime : 1466490783
     * uptime : 0
     * sort_types : []
     */

    private String fuwu_id;
    private String img;
    private String price;
    private String state;
    private String fid;
    private String px;
    private String xiadan;
    private String intime;
    private String uptime;
    private List<HomeSortBean> sort_types;
    private boolean isChecked;

    public HomeSortBean(String name, String imgPath, int imgId) {
        this.name = name;
        this.imgPath = imgPath;
        this.imgId = imgId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getFuwu_id() {
        return fuwu_id;
    }

    public void setFuwu_id(String fuwu_id) {
        this.fuwu_id = fuwu_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getPx() {
        return px;
    }

    public void setPx(String px) {
        this.px = px;
    }

    public String getXiadan() {
        return xiadan;
    }

    public void setXiadan(String xiadan) {
        this.xiadan = xiadan;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public List<HomeSortBean> getSort_types() {
        return sort_types;
    }

    public void setSort_types(List<HomeSortBean> sort_types) {
        this.sort_types = sort_types;
    }
}
