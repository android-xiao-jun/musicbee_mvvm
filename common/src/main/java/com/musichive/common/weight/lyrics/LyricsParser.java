package com.musichive.common.weight.lyrics;

import com.blankj.utilcode.util.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述：
 * <p>
 * Created by zhaodecang on 2016-11-22下午7:02:32
 * <p>
 * 邮箱：zhaodecang@gmail.com
 */
public class LyricsParser {

	/** 解析一行歌词 [01:45.51][02:58.62]整理好心情再出发 */
	private static List<LyricBean> parserLine(String line) {
		List<LyricBean> lineList = new ArrayList<LyricBean>();
		String[] arr = line.split("]");
		// [01:45.51 [02:58.62 整理好心情再出发
		String content = arr[arr.length - 1];
		// [01:45.51 [02:58.62
		if (arr.length < 2){
			lineList.add(new LyricBean(0, content));
			return  lineList;
		}
		for (int i = 0; i < arr.length - 1; i++) {
			int startPoint = parserStartPoint(arr[i]);
			if(i==0){
				lineList.add(new LyricBean(startPoint, content));
			}else if(i==1){
				lineList.add(new LyricBean(startPoint, ""));
			}

		}
		return lineList;
	}

	/** 解析出一行歌词的起始时间 [01:45.51 */
	private static int parserStartPoint(String startPoint) {
		int time = 0;
		try {
			String[] arr = startPoint.split(":");
			// [01 45.51
			String minStr = arr[0].substring(1);
			// 45.51
			arr = arr[1].split("\\.");
			// 45 51
			String secStr = arr[0];
			String mSecStr = arr[1];
			time = parseInt(minStr) * 60 * 1000 + parseInt(secStr) * 1000 + parseInt(mSecStr) * 1;
		}catch (Exception e){
			LogUtils.e(e.toString());
		}

		return time;
	}

	private static int parseInt(String str) {
		return Integer.parseInt(str);
	}

	public static ArrayList<LyricBean> parserString(String lyrics){
		ArrayList<LyricBean> lyricsList = new ArrayList<LyricBean>();
		String[] arr = lyrics.split("\n");
		for (int i = 0; i <arr.length ; i++) {
			if(!arr[i].trim().isEmpty()){
				List<LyricBean> lineList = parserLine(arr[i].trim());
				lyricsList.addAll(lineList);
			}
		}
//		lyricsList.add(new LyricBean(0, lyrics));
			return lyricsList;
	}
}
