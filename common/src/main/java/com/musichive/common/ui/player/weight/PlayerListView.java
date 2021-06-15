package com.musichive.common.ui.player.weight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.kunminx.player.PlayingInfoManager;
import com.musichive.common.R;
import com.musichive.common.app.BaseApplication;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.databinding.FragmentFragmentPlayListBinding;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.room.MusicDatabase;
import com.musichive.common.ui.player.adapter.PlayerListAdapter;
import com.musichive.common.utils.HandlerUtils;

import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 07 日 10:48
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerListView {
    private PopupWindow playlistWindow;
    private FragmentFragmentPlayListBinding playListBinding;
    public PlayerListAdapter playerListAdapter;

    public PlayerListView(Context context) {
        playListBinding = FragmentFragmentPlayListBinding.inflate(LayoutInflater.from(context), null, false);
        playListBinding.ibRecyclemode.setOnClickListener(v -> PlayerManager.getInstance().changeMode());
        playListBinding.ibListremove.setOnClickListener(v -> {
            HandlerUtils.getInstance().postWork(() -> {
                PlayerManager.getInstance().clear();
                MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().deleteMusicAll();
            });
            if (playlistWindow != null) {
                playlistWindow.dismiss();
            }
        });
        playListBinding.bClose.setOnClickListener(v -> {
            if (playlistWindow != null) {
                playlistWindow.dismiss();
            }
        });
        playerListAdapter = new PlayerListAdapter(context);
        playListBinding.rvPlaylist.setLayoutManager(new LinearLayoutManager(context));
        playListBinding.rvPlaylist.setAdapter(playerListAdapter);

        playlistWindow = new PopupWindow(playListBinding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        playlistWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        playlistWindow.setOutsideTouchable(true);
        playlistWindow.setFocusable(true);
        playlistWindow.setAnimationStyle(R.style.style_playlist_animation);

        playlistWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    private String setModeSrc(PlayingInfoManager.RepeatMode mode) {
        int size = PlayerManager.getInstance().getAlbumMusics().size();
        if (mode == PlayingInfoManager.RepeatMode.LIST_CYCLE) {
            return "列表循环（" + size + "首）";
        } else if (mode == PlayingInfoManager.RepeatMode.RANDOM) {
            return "随机播放（" + size + "首）";
        } else if (mode == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) {
            return "单曲循环";
        } else {
            return "列表循环（" + size + "首）";
        }
    }

    public void updateInfoMode() {
        playListBinding.tvRecyclemode.setText(setModeSrc((PlayingInfoManager.RepeatMode) PlayerManager.getInstance().getRepeatMode()));
    }

    public void updateInfoList() {
        playerListAdapter.submitList(PlayerManager.getInstance().getAlbumMusics());
    }

    public void updateInfoIndex() {
        playerListAdapter.playIndex = PlayerManager.getInstance().getAlbumIndex();
        playerListAdapter.notifyDataSetChanged();
    }

    public void showAtLocation() {
        if (playlistWindow == null) {
            return;
        }
        updateInfoMode();
        updateInfoList();
        updateInfoIndex();
        setBackgroundAlpha(0.5f);
        playListBinding.rvPlaylist.smoothScrollToPosition(playerListAdapter.playIndex);
        playlistWindow.showAtLocation(playListBinding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    public void setBackgroundAlpha(float bgAlpha) {
        Activity topActivity = ActivityUtils.getTopActivity();
        WindowManager.LayoutParams lp = topActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        topActivity.getWindow().setAttributes(lp);
    }

    public static void removeListItem(TestAlbum.TestMusic item) {
        TestAlbum album = PlayerManager.getInstance().getAlbum();
        if (album == null) {
            return;
        }
        List<TestAlbum.TestMusic> musics = album.getMusics();
        int temp = -1;
        for (int i = 0; i < musics.size(); i++) {
            if (musics.get(i).getMusicId().equals(item.getMusicId())) {
                temp = i;
                break;
            }
        }
        if (temp != -1) {
            int oldPlayIndex = PlayerManager.getInstance().getAlbumIndex();
            boolean lastIndex = false;
            if (oldPlayIndex == (musics.size() - 1)) {
                lastIndex = true;
            }
            if (temp < oldPlayIndex) {
                oldPlayIndex = oldPlayIndex - 1;
            }
            musics.remove(temp);
            if (musics.size() == 0) {
                PlayerManager.getInstance().clear();
                MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().deleteMusicAll();
            } else {
                PlayerManager.getInstance().loadAlbum(album, lastIndex ? 0 : oldPlayIndex);
                MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().deleteMusic(item.getMusicId());
            }
        }
    }
}
