package tts.project.qiji.bean;

/**
 * Created by shanghang on 2016/6/7.
 */
public class HomeSortBean {
    private String name;
    private String imgPath;
    private int imgId;

    public HomeSortBean(String name, String imgPath, int imgId) {
        this.name = name;
        this.imgPath = imgPath;
        this.imgId = imgId;
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
}
