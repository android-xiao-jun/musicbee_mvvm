package com.musichive.common.test;


import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


/**
 * 数据绑定ViewHolderManager
 * Created by free46000 on 2017/4/6.
 */
public abstract class BindViewHolderManager<T,D extends ViewDataBinding> extends BaseViewHolderManager<T> {
    {
        enableDataBind();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, T data) {
        D dataBinding = DataBindingUtil.getBinding(holder.itemView);
        onBindViewHolder(dataBinding, data);
//        dataBinding.executePendingBindings();
    }

    /**
     * 绑定数据到视图
     *
     * @param dataBinding item视图对应dataBinding类
     * @param data        数据源
     */
    protected abstract void onBindViewHolder(D dataBinding, T data);

    @Override
    protected abstract int getItemLayoutId();
}
