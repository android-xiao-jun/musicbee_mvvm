package com.musichive.common.other;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 14:28
 * Description 音乐蜜蜂-mvvm版本
 */
public class BaseViewHolder<T, BINDING extends ViewDataBinding> extends RecyclerView.ViewHolder {

    protected BINDING binding;

    public BaseViewHolder(View itemView, BINDING binding) {
        super(itemView);
        this.binding = binding;
    }


    public static BaseViewHolder getViewHolder(ViewGroup parent, int layoutID) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutID, parent, false);
        return new BaseViewHolder(binding.getRoot(), binding);

    }
}
