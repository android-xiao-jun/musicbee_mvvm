package com.musichive.common.bean.home;

import android.text.TextUtils;

import com.musichive.common.config.AppConfig;
import com.musichive.common.weight.AvatarImageTagView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 03月 31 日 14:31
 * Description android_client
 */
public class HomeDynamicInfo {

    public int pageFlag;//当前页码
    public int totalPage;//总页数
    public int totalRecord;//总记录条数
    public List<ListBean> list;

    public static class ListBean {

        public DemoInfoVOBean demoInfoVO;//demo信息
        public String headUrl;//头像
        public String msg;//动态显示语句
        public String name;//名称
        public String account;//账户
        public long time;//动态时间
        public int type;//动态类型:1-新注册;2-新存证;3-新上架出售;4-新成功出售;5乐库视频存证
        public String userLabel;//用户标签
        public String identityVerifyType;//认证类型-1 default 0.实人认证  1.身份证认证  2.企业认证

        public static class DemoInfoVOBean {

            public String cover;//歌名
            public String demoName;//歌名
            public String goodsId;//商品id
            public String musicEncodeUrl;//歌曲obs转码地址


            //type 为 5 的时候使用
            public String account;
            public String permlink;
            public int worksType;

        }

        public String getHeadUrlLink() {
            if (headUrl == null || TextUtils.isEmpty(headUrl)) {
                return "";
            }
            if (headUrl.contains("http://") || headUrl.contains("https://")) {//微信头像
                return headUrl;
            }

            return AppConfig.URLPREFIX + headUrl;
        }

        public String getCoverLink() {
            if (demoInfoVO == null || TextUtils.isEmpty(demoInfoVO.cover)) {
                return "";
            }
            if (demoInfoVO.cover.contains("http://") || demoInfoVO.cover.contains("https://")) {//微信头像
                return headUrl;
            }

            return AppConfig.URLPREFIX + demoInfoVO.cover;
        }

        public String getDemoName(){
            if (demoInfoVO == null || TextUtils.isEmpty(demoInfoVO.demoName)) {
                return "";
            }
            return demoInfoVO.demoName;
        }

        public String timestampToStr() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
            return df.format(new Timestamp(time));
        }

        public int getVerifyType() {
            if ("0".equals(identityVerifyType) || "1".equals(identityVerifyType)) {
                return AvatarImageTagView.TAG_PERSONAL;
            } else if ("2".equals(identityVerifyType)) {
                return AvatarImageTagView.TAG_ENTERPRISE;
            } else {
               return AvatarImageTagView.TAG_DEFAULT;
            }
        }
        public boolean isShowLabel(){
            return !TextUtils.isEmpty(userLabel);
        }
    }

}
