package com.musichive.common.other.float_player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.musichive.base.glide.GlideUtils;
import com.musichive.common.R;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.weight.CircleImageView;

/**
 * @Author Jun
 * Date 2021年5月31日15:25:14
 * Description 悬浮播放view
 */
public class PlayerToolView extends FrameLayout {

    private CircleImageView icon;

    public PlayerToolView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerToolView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerToolView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_layout_float_imp, this);
        icon = findViewById(R.id.icon);
    }

    public void initFpsLayout() {

    }

    public void loadPic() {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
        if (icon != null && icon.getVisibility() != VISIBLE) {
            icon.setVisibility(VISIBLE);
        }
        GlideUtils.loadPicToImageViewAsBitmap(getContext(), PlayerManager.getInstance().getAlbum().getCoverImg(), icon);
    }
}
