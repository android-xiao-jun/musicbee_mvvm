package com.musichive.main.ui.home.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.LogUtils;
import com.kunminx.architecture.data.response.DataResult;
import com.musichive.main.bean.home.MusicLabelResponseBean;
import com.musichive.main.bean.home.UserLabe;
import com.musichive.main.bean.music.MusicLibPlayerBean;
import com.musichive.main.ui.home.repository.MusicDataRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 31 日 09:55
 * Description 音乐蜜蜂-mvvm版本
 */
public class MusicLibFragmentViewModel extends ViewModel {
    public final ObservableField<String> indexStr = new ObservableField<String>();

    public final ObservableBoolean check = new ObservableBoolean();
    public final ObservableInt checkTextColor = new ObservableInt();

    public final ObservableInt typeSwitch = new ObservableInt();

    public final ObservableInt tabLeftMargin = new ObservableInt();
    public final ObservableInt tabWidth = new ObservableInt();

    public final ObservableBoolean autoRefresh = new ObservableBoolean();
    public final ObservableBoolean closeRefresh = new ObservableBoolean();
    public final ObservableBoolean closeLoad = new ObservableBoolean();
    public final ObservableBoolean notMoreList = new ObservableBoolean();

    private MutableLiveData<Integer> typeSwitchLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getTypeSwitchLiveData() {
        return typeSwitchLiveData;
    }

    private MutableLiveData<Integer> typeData = new MutableLiveData<>();
    private MutableLiveData<Integer> styleData = new MutableLiveData<>();
    private MutableLiveData<Integer> ordertypeData = new MutableLiveData<>();
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

    public MutableLiveData<Integer> getTypeData() {
        return typeData;
    }

    public MutableLiveData<Integer> getStyleData() {
        return styleData;
    }

    public MutableLiveData<Integer> getOrdertypeData() {
        return ordertypeData;
    }

    public static final int STATE_DEFAULT = 0;
    public static final int STATE_LOAD_TAG = 1;
    public static final int STATE_LOAD_TAG_END = 2;
    public static final int STATE_ERROR = 6;


    public MusicLibFragmentViewModel() {
        check.set(true);
        typeSwitchLiveData.postValue(1);
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

    private MutableLiveData<List<UserLabe>> typeLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserLabe>> styleLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserLabe>> heatLiveData = new MutableLiveData<>();

    //类型
    public MutableLiveData<List<UserLabe>> getTypeLiveData() {
        return typeLiveData;
    }

    //曲风
    public MutableLiveData<List<UserLabe>> getStyleLiveData() {
        return styleLiveData;
    }

    //热度
    public MutableLiveData<List<UserLabe>> getHeatLiveData() {
        return heatLiveData;
    }


    public List<UserLabe> getTypeList() {
        List<UserLabe> value = getTypeLiveData().getValue();
        if (value == null) {
            value = new ArrayList<>();
            getTypeLiveData().setValue(value);
        }
        return value;
    }

    public List<UserLabe> getStyleList() {
        List<UserLabe> value = getStyleLiveData().getValue();
        if (value == null) {
            value = new ArrayList<>();
            getStyleLiveData().setValue(value);
        }
        return value;
    }

    public List<UserLabe> getHeatList() {
        List<UserLabe> value = getHeatLiveData().getValue();
        if (value == null) {
            value = new ArrayList<>();
        }
        return value;
    }

    public void loadTypeAndStyle() {
        if (STATE_LOAD_TAG == getStateValue()) {
            LogUtils.d("当前正在加载tag");
            return;//当前正在加载tag
        }
        if (getStyleList().isEmpty() || getTypeList().isEmpty() || getHeatList().isEmpty()) {
            //其中有一个获取数据失败，或者空了，重写加载
            LogUtils.d("其中有一个获取数据失败，或者空了，重写加载");
            getStateLiveData().postValue(STATE_LOAD_TAG);
            MusicDataRepository.getMusicLabel(new DataResult.Result<MusicLabelResponseBean>() {
                @Override
                public void onResult(DataResult<MusicLabelResponseBean> dataResult) {
                    if (dataResult.getResponseStatus().isSuccess()) {
                        MusicLabelResponseBean musicLabelResponseBean = dataResult.getResult();
                        List<UserLabe> type = musicLabelResponseBean.getDtype();//类型
                        List<UserLabe> data = musicLabelResponseBean.getData();//曲风
                        List<UserLabe> ordertype = musicLabelResponseBean.getOrderType();//tab标签

                        List<UserLabe> typeList = getTypeList();
                        typeList.clear();
                        typeList.addAll(type);
                        getTypeLiveData().postValue(typeList);

                        List<UserLabe> styleList = getStyleList();
                        styleList.clear();
                        styleList.addAll(data);
                        getStyleLiveData().postValue(styleList);

                        List<UserLabe> heatList = getHeatList();
                        heatList.clear();
                        heatList.addAll(ordertype);
                        getHeatLiveData().postValue(heatList);
                        getStateLiveData().postValue(STATE_LOAD_TAG_END);
                    } else {
                        getStateLiveData().postValue(STATE_ERROR);
                    }
                }
            });

        }
    }

}
