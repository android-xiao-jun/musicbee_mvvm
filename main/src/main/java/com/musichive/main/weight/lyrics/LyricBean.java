package com.musichive.main.weight.lyrics;

/**
 * 描述：
 * <p>
 * Created by zhaodecang on 2016-11-22下午7:02:13
 * <p>
 * 邮箱：zhaodecang@gmail.com
 */
public class LyricBean implements Comparable<LyricBean> {
	public int time;
	public String lyric;

	public LyricBean(int time, String lyric) {
		this.time = time;
		this.lyric = lyric;
	}

	@Override
	public int compareTo(LyricBean o) {
		// 时间小的放在前面
		return this.time - o.time;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
}
