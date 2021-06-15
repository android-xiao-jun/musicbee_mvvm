package com.musichive.main.ui.home.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.musichive.main.bean.music.MusicLibPlayerBean;
import com.musichive.main.ui.home.repository.MusicDataRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 31 日 09:55
 * Description 音乐蜜蜂-mvvm版本
 */
public class MusicLibFragmentChildViewModel extends ViewModel {

    public final ObservableBoolean autoRefresh = new ObservableBoolean();
    public final ObservableBoolean closeRefresh = new ObservableBoolean();
    public final ObservableBoolean closeLoad = new ObservableBoolean();
    public final ObservableBoolean notMoreList = new ObservableBoolean();

    public final ObservableInt typeSwitch = new ObservableInt();

    public ObservableInt typeData = new ObservableInt();
    public ObservableInt styleData = new ObservableInt();
    public ObservableInt ordertypeData = new ObservableInt();
    private MutableLiveData<List<MusicLibPlayerBean>> listLiveData = new MutableLiveData<>();

    public MutableLiveData<List<MusicLibPlayerBean>> getListData() {
        return listLiveData;
    }

    public List<MusicLibPlayerBean> getListList() {
        List<MusicLibPlayerBean> value = getListData().getValue();
        if (value == null) {
            value = new ArrayList<>();
            getListData().setValue(value);
        }
        return value;
    }


    public static final int STATE_DEFAULT = 0;

    public static final int STATE_CONTENT_START = 7;
    public static final int STATE_CONTENT_END = 8;
    public static final int STATE_CONTENT_ERROR = 9;
    public static final int STATE_CONTENT_EMPTY = 10;

    public MusicLibFragmentChildViewModel() {

    }

    //当前页面加载处于的状态 0 默认 1加载标签 2加载标签结束
    private MutableLiveData<Integer> stateLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getStateLiveData() {
        return stateLiveData;
    }

    public Integer getStateValue() {
        if (stateLiveData.getValue() == null) {
            stateLiveData.setValue(STATE_DEFAULT);
        }
        return stateLiveData.getValue();
    }


    public void postByTitle(int page) {
        if (page == 1) {
            getStateLiveData().postValue(STATE_CONTENT_START);
        }
        MusicDataRepository.postByTitle(typeData.get(), styleData.get(), typeSwitch.get(), ordertypeData.get(), page, dataResult -> {
            if (dataResult.getResponseStatus().isSuccess()) {
                List<MusicLibPlayerBean> listList = getListList();
                if (page == 1) {
                    listList.clear();
                }
                List<MusicLibPlayerBean> result = dataResult.getResult().list;
                if (result != null && !result.isEmpty()) {
                    listList.addAll(result);
                    notMoreList.set(false);
                } else {
                    notMoreList.set(true);
                }
                getListData().postValue(listList);
                if (listList.isEmpty()) {
                    getStateLiveData().postValue(STATE_CONTENT_EMPTY);
                } else {
                    getStateLiveData().postValue(STATE_CONTENT_END);
                }
            } else {
                getListData().postValue(getListList());
                if (getListList().isEmpty()) {
                    getStateLiveData().postValue(STATE_CONTENT_ERROR);
                } else {
                    getStateLiveData().postValue(STATE_CONTENT_END);
                }
            }
        });
    }

}
