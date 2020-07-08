package com.mnn.mydream.cosmetology.fragment.beauty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.AppUpdateActivity;
import com.mnn.mydream.cosmetology.activity.FuWuServerDialogActivity;
import com.mnn.mydream.cosmetology.bean.AppUpdateBean;
import com.mnn.mydream.cosmetology.dialog.APPUpdateDialog;
import com.mnn.mydream.cosmetology.utils.APKVersionCodeUtils;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobDialogButtonListener;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：XTSZFragment 系统设置
 */

public class XTSZFragment extends SupportFragment {
    private String TAG = "XTSZFragment";

    @BindView(R.id.update_btn)
    TextView updateBtn;
    Unbinder unbinder;
    private View view;


    public static XTSZFragment newInstance() {


        Bundle args = new Bundle();

        XTSZFragment fragment = new XTSZFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.xtsz_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();


        return view;

    }


    private void initview() {


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + "");
                Intent Intent = new Intent(getActivity(), AppUpdateActivity.class);
                Intent.putExtra("flagInt", "1");
                startActivity(Intent);

            }
        });



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}