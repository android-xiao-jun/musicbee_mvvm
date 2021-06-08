package com.musichive.common.ui.player.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.binding_recyclerview.adapter.BaseDataBindingAdapter;
import com.musichive.common.R;
import com.musichive.common.aop.SingleClick;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.databinding.ItemPlaylistBinding;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.ui.player.activity.PlayerActivity;
import com.musichive.common.ui.player.weight.PlayerListView;
import com.musichive.common.utils.HandlerUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @Author Jun
 * Date 2021 年 06月 07 日 10:54
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerListAdapter extends BaseDataBindingAdapter<TestAlbum.TestMusic, ItemPlaylistBinding> {

    public int playIndex = -1;

    public PlayerListAdapter(Context context) {
        super(context, new DiffUtil.ItemCallback<TestAlbum.TestMusic>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull TestAlbum.TestMusic oldItem, @NonNull @NotNull TestAlbum.TestMusic newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull TestAlbum.TestMusic oldItem, @NonNull @NotNull TestAlbum.TestMusic newItem) {
                return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getMusicId().equals(newItem.getMusicId());
            }
        });
        playIndex = PlayerManager.getInstance().getAlbumIndex();
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_playlist;
    }

    @Override
    protected void onBindItem(ItemPlaylistBinding binding, TestAlbum.TestMusic item, RecyclerView.ViewHolder holder) {
        binding.ibSingleremove.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                HandlerUtils.getInstance().postWork(() -> {
                    PlayerListView.removeListItem(item);
                    HandlerUtils.getInstance().postMain(()->{
                        playIndex = PlayerManager.getInstance().getAlbumIndex();
                        notifyDataSetChanged();
                    });
                });
            }
        });
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                if (holder.getBindingAdapterPosition() == playIndex) {
                    return;
                } else {
                    playIndex = holder.getBindingAdapterPosition();
                    notifyDataSetChanged();
                }
                HandlerUtils.getInstance().removeCallbacks(runnable);
                HandlerUtils.getInstance().getWorkHander().postDelayed(runnable,100);
            }
        });
        binding.tvSongname.setText(item.getTitle());
        binding.tvNikename.setText(item.getArtist().getName());
        if (holder.getBindingAdapterPosition() == playIndex) {
            binding.tvSongname.setTextColor(Color.parseColor("#FF4F48"));
            binding.tvNikename.setTextColor(Color.parseColor("#FF4F48"));
            binding.tvHorline.setTextColor(Color.parseColor("#FF4F48"));
            binding.ivCurrentplaying.setVisibility(View.VISIBLE);
        } else {
            binding.tvSongname.setTextColor(Color.parseColor("#333333"));
            binding.tvNikename.setTextColor(Color.parseColor("#666666"));
            binding.tvHorline.setTextColor(Color.parseColor("#B2B2B2"));
            binding.ivCurrentplaying.setVisibility(View.GONE);
        }
        if (item.getType() == 1) {//0 乐库 1 市集 2 nft播放
            binding.tvHorline.setVisibility(View.GONE);
            binding.tvNikename.setVisibility(View.GONE);
        } else {
            binding.tvHorline.setVisibility(View.VISIBLE);
            binding.tvNikename.setVisibility(View.VISIBLE);
        }
    }
    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
            PlayerManager.getInstance().playAudio(playIndex);
        }
    };
}
