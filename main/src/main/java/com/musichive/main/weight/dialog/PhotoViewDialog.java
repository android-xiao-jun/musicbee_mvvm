package com.musichive.main.weight.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.kunminx.architecture.utils.ScreenUtils;
import com.musichive.base.glide.GlideUtils;
import com.musichive.main.R;
import com.musichive.main.databinding.DialogPhotoViewBinding;

/**
 * @Author Jun
 * Date 2021 年 06月 16 日 10:12
 * Description 音乐蜜蜂-mvvm版本 -显示图片的 对话框
 */
public class PhotoViewDialog extends Dialog {
    private String picPath;

    public PhotoViewDialog(@NonNull Context context, String picPath) {
        super(context, R.style.Translucent_NoTitle);
        this.picPath = picPath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        DialogPhotoViewBinding photoViewBinding = DialogPhotoViewBinding.inflate(getLayoutInflater(), null, false);
        setContentView(photoViewBinding.getRoot());
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (ScreenUtils.getScreenWidth() * 0.9);
            lp.height = (int) (ScreenUtils.getScreenHeight() * 0.8);
            window.setAttributes(lp);
        }
        GlideUtils.loadPicToImageViewAsBitmap(getContext(),picPath,photoViewBinding.previewImageView);
        photoViewBinding.dialogCha.setOnClickListener(v -> cancel());
    }

}
