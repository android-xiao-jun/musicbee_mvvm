package com.musichive.common.bean.home;

/**
 * @Author Jun
 * Date 2021 年 03月 30 日 16:24
 * Description 首页活动bean
 */
public class HomeActivityBean {
    public String id = "";
    public String link = "";
    public Object icon = "";
    public String title = "";

    public HomeActivityBean(String id, String title,String link, Object icon) {
        this.id = id;
        this.link = link;
        this.icon = icon;
        this.title = title;
    }

    public HomeActivityBean() {
    }
}
