package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mnn.mydream.cosmetology.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MyDream on 2020/5/16.
 * <p>
 * 删除弹窗
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private String TAG = "LoadingDialog";
        private Context context;
        private View.OnClickListener cancelOnClick = null;
        private View.OnClickListener yesOnClick = null;

        private String loadingString = "BallSpinFadeLoaderIndicator";


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setLoadingMsg(String title) {

            this.loadingString = title;

            return this;

        }

        public LoadingDialog createDialog() {

            LoadingDialog dialog = new LoadingDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.loading_dialog_layout, null);
            dialog.setContentView(contentView);
            ViewHolder viewHolder = new ViewHolder(contentView);
            viewHolder.avloading.setIndicator(loadingString);

            return dialog;
        }


        static class ViewHolder {
            @BindView(R.id.avloading)
            AVLoadingIndicatorView avloading;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
