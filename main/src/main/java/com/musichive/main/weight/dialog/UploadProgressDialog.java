package com.musichive.main.weight.dialog;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.musichive.main.R;


public class UploadProgressDialog extends AlertDialog {
    public UploadProgressDialog(Context context) {
        super(context, R.style.Translucent_NoTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_progress_bar);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hideLoad();
    }

    public static UploadProgressDialog uploadProgressDialog;

    public static UploadProgressDialog showLoad(Context context) {
        if (uploadProgressDialog == null) {
            uploadProgressDialog = new UploadProgressDialog(context);
        } else if (uploadProgressDialog.getContext() != context) {
            hideLoad();//如果这次的 显示dialog的 context不等，则移除
            uploadProgressDialog = new UploadProgressDialog(context);
        }
        uploadProgressDialog.show();
        return uploadProgressDialog;
    }

    public static void hideLoad() {
        if (uploadProgressDialog != null) {
            uploadProgressDialog.hide();
        }
    }
}
