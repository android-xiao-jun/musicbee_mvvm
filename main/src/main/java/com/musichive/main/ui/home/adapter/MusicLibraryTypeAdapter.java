package com.musichive.main.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.musichive.main.R;
import com.musichive.aop.SingleClick;
import com.musichive.main.bean.home.UserLabe;
import com.musichive.main.databinding.ItemGroupsPostTypeOrStyleBinding;

import java.util.List;


public class MusicLibraryTypeAdapter extends RecyclerView.Adapter<MusicLibraryTypeAdapter.GroupsPostTypeHolder> {

    private Context context;
    private List<UserLabe> userLabels;

    private int savePosition = 0;

    public int getSavePosition() {
        return savePosition;
    }

    public MusicLibraryTypeAdapter(Context context, List<UserLabe> userLabels) {
        this.context = context;
        this.userLabels = userLabels;
    }

    @NonNull
    @Override
    public GroupsPostTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGroupsPostTypeOrStyleBinding styleBinding = ItemGroupsPostTypeOrStyleBinding.inflate(LayoutInflater.from(context), parent, false);
        return new GroupsPostTypeHolder(styleBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsPostTypeHolder holder, int position) {
        holder.itemView.itemGroupsPostTypeOrStyleTv.setText(userLabels.get(position).getTitle());
        if (savePosition == position) {
            holder.itemView.itemGroupsPostTypeOrStyleTv.setBackgroundResource(R.drawable.type_or_style_bg);
            holder.itemView.itemGroupsPostTypeOrStyleTv.setTextColor(ContextCompat.getColor(context,R.color.colormusicbee));
        } else {
            holder.itemView.itemGroupsPostTypeOrStyleTv.setBackground(null);
            holder.itemView.itemGroupsPostTypeOrStyleTv.setTextColor(ContextCompat.getColor(context,R.color.color_999999));
        }
        holder.itemView.itemGroupsPostTypeOrStyleTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder, position, userLabels.get(position));
                }
                savePosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userLabels == null ? 0 : userLabels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class GroupsPostTypeHolder extends RecyclerView.ViewHolder {
        ItemGroupsPostTypeOrStyleBinding itemView;

        public GroupsPostTypeHolder(ItemGroupsPostTypeOrStyleBinding itemView) {
            super(itemView.getRoot());
            this.itemView=itemView;
        }
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder, int position, UserLabe data);
    }

}
