package com.musichive.common.test;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RecyclerView的adapter<p>
 * adapter与view holder创建绑定解耦{@link #register(Class, ViewHolderManager)}
 *
 * @author free46000  2017/03/15
 * @version v1.0
 */
public class BaseItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<Object> dataItems = new ArrayList<>();
    private List<Object> headItems = new ArrayList<>();
    private List<Object> footItems = new ArrayList<>();
    private ItemTypeManager itemTypeManager;

    public BaseItemAdapter() {
        itemTypeManager = new ItemTypeManager();
    }


    /**
     * 为数据源注册ViewHolder的管理类{@link ViewHolderManager}<br>
     * ViewHolder的管理类使adapter与view holder的创建绑定解耦<br>
     * 通过{@link ItemTypeManager}管理不同数据源和ViewHolder的关系<br>
     *
     * @param cls     数据源class
     * @param manager 数据源管理类
     * @param <T>     数据源
     * @param <V>     ViewHolder
     * @see #register 为相同数据源注册多个ViewHolder的管理类
     */
    public <T, V extends BaseViewHolder> void register(@NonNull Class<T> cls, @NonNull ViewHolderManager<T, V> manager) {
        itemTypeManager.register(cls, manager);
    }


    /**
     * 设置Item list
     */
    public void setDataItems(@NonNull List<? extends Object> dataItems) {
        setItem(dataItems);
    }

    /**
     * 添加Item
     */
    public void addDataItem(@NonNull Object item) {
        addDataItem(dataItems.size(), item);
    }

    /**
     * 在指定位置添加Item
     */
    public void addDataItem(int position, @NonNull Object item) {
        addDataItems(position, Collections.singletonList(item));
    }

    /**
     * 添加ItemList
     */
    public void addDataItems(@NonNull List<? extends Object> items) {
        addDataItems(dataItems.size(), items);
    }

    /**
     * 在指定位置添加ItemList
     */
    public void addDataItems(int position, @NonNull List<? extends Object> items) {
        addItem(position, items);
    }

    /**
     * add item的最后调用处
     */
    protected void addItem(int position, @NonNull List<? extends Object> items) {
        dataItems.addAll(position, items);
        notifyItemRangeInserted(position + getHeadCount(), items.size());
    }

    /**
     * 设置item的最后调用处
     */
    protected void setItem(@NonNull List<? extends Object> dataItems) {
        this.dataItems = (List<Object>) dataItems;
        notifyDataSetChanged();
    }

    /**
     * 移动Item的位置 包括数据源和界面的移动
     *
     * @param fromPosition Item之前所在位置
     * @param toPosition   Item新的位置
     */
    public void moveDataItem(int fromPosition, int toPosition) {
        //考虑到跨position(如0->2)移动的时候处理不能Collections.swap
        // if(from<to) to = to + 1 - 1;//+1是因为add的时候应该是to位置后1位，-1是因为先remove了from所以位置往前挪了1位
        dataItems.add(toPosition, dataItems.remove(fromPosition));
        notifyItemMoved(fromPosition + getHeadCount(), toPosition + getHeadCount());
    }


    /**
     * 移除Item 包括数据源和界面的移除
     *
     * @param position 需要被移除Item的position
     */
    public void removeDataItem(int position) {
        removeDataItem(position, 1);
    }

    /**
     * 改变Item 包括数据源和界面的移除
     *
     * @param position 需要被移除第一个Item的position
     * @param position 需要被移除Item的个数
     */
    public void removeDataItem(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            dataItems.remove(position);
        }
        notifyItemRangeRemoved(position + getHeadCount(), itemCount);
    }

    /**
     * @return 获取当前数据源List，不包含head和foot
     */
    public List<Object> getDataList() {
        return dataItems;
    }


    /**
     * @param position int
     * @return 返回指定位置Item
     */
    public Object getItem(int position) {
        if (position < headItems.size()) {
            return headItems.get(position);
        }

        position -= headItems.size();
        if (position < dataItems.size()) {
            return dataItems.get(position);
        }

        position -= dataItems.size();
        if (position < footItems.size()) {
            return footItems.get(position);
        }

        return null;
    }

    /**
     * 清空数据
     */
    public void clearAllData() {
        clearData();
        headItems.clear();
        footItems.clear();
    }

    /**
     * 清空Item数据不含head 和 foot
     */
    public void clearData() {
        dataItems.clear();
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderManager provider = itemTypeManager.getViewHolderManager(viewType);
        BaseViewHolder viewHolder = provider.onCreateViewHolder(parent);
        viewHolder.viewHolderManager = provider;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Object item = getItem(position);
        ViewHolderManager manager = holder.viewHolderManager;
        manager.onBindViewHolder(holder, item);
        //赋值 方便以后使用
        holder.itemData = item;
    }

    @Override
    public int getItemViewType(int position) {
        int type = itemTypeManager.getItemType(getItem(position));
        if (type < 0) {
            throw new RuntimeException("没有为" + getItem(position).getClass() + "找到对应的item itemView manager，是否注册了？");
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return dataItems.size() + getHeadCount() + getFootCount();
    }


    /**
     * @return head view个数
     */
    public int getHeadCount() {
        return headItems.size();
    }

    /**
     * @return foot view个数
     */
    public int getFootCount() {
        return footItems.size();
    }

}
