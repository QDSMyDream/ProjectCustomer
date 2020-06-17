package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mnn.mydream.cosmetology.utils.Tools;


public class CommonDialog extends Dialog {
    private DisplayMetrics displayMetrics;
    private Context context;
    private float widthPro = 0.725f;
    private int dialogMode = 0;
    private int height, width;
    private boolean cancelTouchout;
    private View view;


    private CommonDialog(Builder builder) {
        super(builder.context);
        context = builder.context;
        height = builder.height;
        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
        widthPro = builder.widthPro;
        dialogMode = builder.dialogMode;
        displayMetrics = builder.displayMetrics;
    }


    private CommonDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        context = builder.context;
        height = builder.height;
        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
        widthPro = builder.widthPro;
        dialogMode = builder.dialogMode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);

        //设置dialog的宽高是全屏,注意：一定要放在show的后面，否则不是全屏显示
        WindowManager.LayoutParams params = getWindow().getAttributes();
        /*
         * 设置对话框的位置
         * DialogMode.MODE_NORMAL：
         *      位置：中间
         *  DialogMode.MODE_BOTTOM：
         *      位置：底部
         */
        switch (dialogMode) {
            case DialogMode.MODE_NORMAL:
                params.gravity = Gravity.CENTER;

                break;
            case DialogMode.MODE_BOTTOM:
                params.gravity = Gravity.BOTTOM;
                break;
        }

        //设置对话框的宽度
        params.width = (int) (Tools.getScreenWidth(context)*widthPro);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    public static final class Builder {

        private Context context;
        private int height, width;
        private float widthPro = 0.725f;
        private int dialogMode = 0;
        private boolean cancelTouchout;
        private View view;
        private int resStyle = -1;
        private DisplayMetrics displayMetrics;


        public Builder(Context context) {
            this.context = context;
            this.displayMetrics = context.getResources().getDisplayMetrics();
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        public Builder setContent(String content) {
//            TextView tv_content = this.view.findViewById(R.id.tv_content);
//            tv_content.setText(content);
            return this;
        }

        public Builder setView(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public Builder setWidthPro(float val) {
            widthPro = val;
            return this;
        }

        public Builder setDialogMode(int val) {
            dialogMode = val;
            return this;
        }

        public Builder heightpx(int val) {
            height = val;
            return this;
        }

        public Builder widthpx(int val) {
            width = val;
            return this;
        }

        public Builder heightdp(int val) {
            height = Tools.dp2px(context, val);
            return this;
        }

        public Builder widthdp(int val) {
            width = Tools.dp2px(context, val);
            return this;
        }

        public Builder heightDimenRes(int dimenRes) {
            height = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder widthDimenRes(int dimenRes) {
            width = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder setStyle(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        public Builder addOnClickListener(int viewRes, View.OnClickListener listener) {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        public CommonDialog build() {
            if (resStyle != -1) {
                return new CommonDialog(this, resStyle);
            } else {
                return new CommonDialog(this);
            }
        }
    }

    public static class DialogMode {
        public static final int MODE_NORMAL = 0;
        public static final int MODE_BOTTOM = 1;
    }
}
