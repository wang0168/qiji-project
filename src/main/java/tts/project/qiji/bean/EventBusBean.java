package tts.project.qiji.bean;

/**
 * Created by shanghang on 2016/7/5.
 */
public class EventBusBean {
    private boolean isRefresh;//用户订单详情中修改，订单列表刷新
    private String homePage;//首页切换tab
    private String engineerOrderPage;//工程师端待接单切换tab; 1,企业订单 ；2个人订单
    private String userOrderPage;//用户端订单切换tab; 1,待指派 ；2,待服务；3,待确认；4,已完成;

    public String getUserOrderPage() {
        return userOrderPage;
    }

    public EventBusBean setUserOrderPage(String userOrderPage) {
        this.userOrderPage = userOrderPage;
        return this;
    }

    public String getEngineerOrderPage() {
        return engineerOrderPage;
    }

    public EventBusBean setEngineerOrderPage(String engineerOrderPage) {
        this.engineerOrderPage = engineerOrderPage;
        return this;
    }

    public String getHomePage() {
        return homePage;
    }

    public EventBusBean setHomePage(String homePage) {
        this.homePage = homePage;
        return this;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public EventBusBean setRefresh(boolean refresh) {
        isRefresh = refresh;
        return this;
    }
}
