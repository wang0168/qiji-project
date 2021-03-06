package tts.project.qiji.bean;

/**
 * Created by shanghang on 2016/6/7.
 */
public class MeItemBean {
    private int left_img;
    private String item_name;
    private String context;
    private boolean isRight;
    private boolean isline;
    private boolean iswidth;

    public MeItemBean(int left_img, String item_name,String context, boolean isRight, boolean isline, boolean iswidth) {
        this.left_img = left_img;
        this.item_name = item_name;
        this.isRight = isRight;
        this.isline = isline;
        this.iswidth = iswidth;
        this.context=context;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getLeft_img() {
        return left_img;
    }

    public void setLeft_img(int left_img) {
        this.left_img = left_img;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean isline() {
        return isline;
    }

    public void setIsline(boolean isline) {
        this.isline = isline;
    }

    public boolean iswidth() {
        return iswidth;
    }

    public void setIswidth(boolean iswidth) {
        this.iswidth = iswidth;
    }
}
