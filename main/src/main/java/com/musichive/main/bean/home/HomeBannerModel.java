package com.musichive.main.bean.home;

import com.musichive.main.R;
import com.musichive.main.config.AppConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author Jun
 * Date 2021 年 03月 30 日 16:23
 * Description android_client
 */
public class HomeBannerModel {
    private HomeMusicDataBean homeMusicDataBean;

    private List<ListBean> listBannerBean;

    private List<HomeActivityBean> listActivityBean;

    public HomeBannerModel() {

    }

    public void initActivityBean() {
        listActivityBean = new ArrayList<>();
        String lg = Locale.getDefault().getLanguage();
        if (!lg.contains("zh") && !lg.contains("en")) {
            lg = "zh";
        }
        listActivityBean.add(new HomeActivityBean("1", "大运会"
                , AppConfig.NetWork.BASE_WEB_PAGE_URL2 + "universiade?lang=" + lg, R.drawable.home_banner_l));
    }

    public HomeMusicDataBean getHomeMusicDataBean() {
        return homeMusicDataBean;
    }

    public void setHomeMusicDataBean(HomeMusicDataBean homeMusicDataBean) {
        this.homeMusicDataBean = homeMusicDataBean;
    }

    public List<ListBean> getListBannerBean() {
        return listBannerBean;
    }

    public void setListBannerBean(List<ListBean> listBannerBean) {
        this.listBannerBean = listBannerBean;
    }

    public List<HomeActivityBean> getListActivityBean() {
        return listActivityBean;
    }

    public void setListActivityBean(List<HomeActivityBean> listActivityBean) {
        this.listActivityBean = listActivityBean;
    }


    public String getMusician() {
        return homeMusicDataBean == null ? "0" : homeMusicDataBean.getMusician();
    }
    public String getWorksCount() {
        return homeMusicDataBean == null ? "0" : homeMusicDataBean.works_count;
    }
}
