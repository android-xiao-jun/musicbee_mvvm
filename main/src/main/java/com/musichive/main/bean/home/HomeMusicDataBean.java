package com.musichive.main.bean.home;

/**
 * @Author Jun
 * Date 2021 年 03月 30 日 16:20
 * Description android_client
 */
public class HomeMusicDataBean {

    /**
     * block : {}
     * demo_count : 0
     * lyric_count : 0
     * music_company : 0
     * music_count : 0
     * musician : 0
     * works_count : 0
     */

    public String demo_count;
    public String lyric_count;
    public String music_company;//公司
    public String music_count;
    public String musician;//音乐人
    public String works_count;//作品数

    public String getMusician() {
        return (Integer.parseInt(musician)+Integer.parseInt(music_company))+"";
    }

    public void setMusician(String musician) {
        this.musician = musician;
    }
}
