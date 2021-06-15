package com.musichive.main.multi_adapter;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;



/**
 * 数据绑定ViewHolderManager
 * Created by free46000 on 2017/4/6.
 */
public class DataBindViewHolderManager<T,D extends ViewDataBinding> extends BindViewHolderManager<T,D> {
    @LayoutRes
    private int itemLayoutId;
    private ItemBindView<T,D> itemBindView;

    /**
     * @param itemLayoutId   item 布局文件资源id
     * @param bindVariableId 需要绑定一个VariableId时使用本构造函数
     */
    public DataBindViewHolderManager(@LayoutRes int itemLayoutId, int bindVariableId) {
        this(itemLayoutId, (dataBinding, data,position) -> dataBinding.setVariable(bindVariableId, data));
    }

    /**
     * @param itemLayoutId item 布局文件资源id
     * @param itemBindView item数据绑定回调 可以自定义绑定逻辑
     */
    public DataBindViewHolderManager(@LayoutRes int itemLayoutId, @NonNull ItemBindView<T,D> itemBindView) {
        this.itemLayoutId = itemLayoutId;
        this.itemBindView = itemBindView;
    }

    /**
     * 绑定数据到视图 {@link ItemBindView}
     *
     * @param dataBinding item视图对应dataBinding类
     * @param data        数据源
     */
    @Override
    protected void onBindViewHolder(D dataBinding, T data,int position) {
        itemBindView.onBindViewHolder(dataBinding, data,position);
    }

    @Override
    protected int getItemLayoutId() {
        return itemLayoutId;
    }


    /**
     * item数据绑定回调接口，在回调方法中自定义绑定逻辑
     */
    public interface ItemBindView<T,D extends ViewDataBinding> {
        void onBindViewHolder(D dataBinding, T data,int position);
    }
}
