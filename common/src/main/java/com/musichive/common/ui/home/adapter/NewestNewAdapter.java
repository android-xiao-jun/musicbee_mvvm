package com.musichive.common.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.musichive.base.glide.GlideUtils;
import com.musichive.common.aop.SingleClick;
import com.musichive.common.bean.home.MusicLibBean;
import com.musichive.common.bean.music.MusicLibPlayerBean;
import com.musichive.common.databinding.NewestNewItemBinding;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.player.helper.PlayerDataTransformUtils;
import com.musichive.common.utils.BlurUtil;
import com.musichive.common.utils.HandlerUtils;
import com.musichive.common.utils.Utils;


import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 13:39
 * Description 音乐蜜蜂-mvvm版本
 */
public class NewestNewAdapter extends RecyclerView.Adapter<NewestNewAdapter.VH> {

    private Context context;
    private List<MusicLibPlayerBean> mList;

    private int page=pageDefault;
    public static final int pageDefault=1;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public NewestNewAdapter(Context context, List<MusicLibPlayerBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewestNewItemBinding newItemBinding = NewestNewItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new VH(newItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        NewestNewItemBinding itemBinding = holder.itemBinding;
        MusicLibPlayerBean item = mList.get(position);
        GlideUtils.loadPicToImageViewAsBitmap(context, item.getCoverurl(), itemBinding.photoImageView);
        BlurUtil.postsGlassBall(context, item.getCoverurl(), itemBinding.ivBg);

        itemBinding.playNumTv.setText(item.getPlayAmount() + "");
        itemBinding.photoDesView.setText(item.getTitle() + "");
        itemBinding.followAccountName.setText(Utils.getPerformername(item.getSign()));
        itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                HandlerUtils.getInstance().postWork(()->{
                    PlayerDataTransformUtils.addMusicLibAndPlay(item);
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public NewestNewItemBinding itemBinding;

        public VH(NewestNewItemBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }
    }
}
