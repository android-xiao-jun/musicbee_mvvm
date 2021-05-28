/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.musichive.common.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KunMinX
 * Create at 2018/6/30
 */
public abstract class BaseMultiDataBindingAdapter<M> extends ListAdapter<M, BaseViewHolder> {

    protected Context mContext;

    protected OnItemClickListener<M> mOnItemClickListener;
    protected OnItemLongClickListener<M> mOnItemLongClickListener;
    protected OnBindDataInterface<M> mOnBindDataInterface = getOnBindDataInterface();

    public abstract void onBindData(M model, BaseViewHolder holder, int position);  //数据绑定

    public abstract OnBindDataInterface<M> getOnBindDataInterface();

    public void setOnItemClickListener(OnItemClickListener<M> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<M> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public BaseMultiDataBindingAdapter(Context context, @NonNull DiffUtil.ItemCallback<M> diffCallback) {
        super(diffCallback);
        this.mContext = context;
    }

    @Override
    public void submitList(@Nullable List<M> list) {
        super.submitList(list, () -> {
            super.submitList(list == null ? new ArrayList<>() : new ArrayList<>(list));
        });
    }

    @Override
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = mOnBindDataInterface.getItemLayoutId(viewType);
        return BaseViewHolder.getViewHolder(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder h, int position) {
        onBindData(getItem(position), h, position);
    }

    /**
     * 绑定数据的接口
     *
     * @param <T> model
     */
    public interface OnBindDataInterface<T> {
        int getItemLayoutId(int viewType);         //设置布局
    }

    /**
     * item多类型支持
     *
     * @param <T>
     */
    public interface OnMultiTypeBindDataInterface<T> extends OnBindDataInterface<T> {
        int setItemViewType(int position);
    }

    public interface OnItemClickListener<M> {
        void onItemClick(int viewId, M item, int position);
    }

    public interface OnItemLongClickListener<M> {
        void onItemLongClick(int viewId, M item, int position);
    }
}