package com.mnn.mydream.cosmetology.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.DetailsActivity;
import com.mnn.mydream.cosmetology.activity.UserInfoActivity;
import com.mnn.mydream.cosmetology.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;


/**
 * Created by Administrator on 2018/6/2.
 */

public class SettingFragment extends android.support.v4.app.Fragment {

    private String TAG = "SetingFragment";

    @BindView(R.id.img_photo)
    CircleImageView imgPhoto;

    @BindView(R.id.tv_nickName)
    TextView tvNickName;

    @BindView(R.id.tv_authSate)
    TextView tvAuthSate;

    @BindView(R.id.tv_postCount)
    TextView tvPostCount;

    @BindView(R.id.tv_dongtai)
    TextView tvDongtai;

    @BindView(R.id.dot_post)
    TextView dotPost;

    @BindView(R.id.layout_post)
    RelativeLayout layoutPost;

    @BindView(R.id.tv_followCount)
    TextView tvFollowCount;

    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;

    @BindView(R.id.dot_follow)
    TextView dotFollow;

    @BindView(R.id.layout_follow)
    RelativeLayout layoutFollow;

    @BindView(R.id.tv_collectCount)
    TextView tvCollectCount;

    @BindView(R.id.tv_shoucang)
    TextView tvShoucang;

    @BindView(R.id.dot_collect)
    TextView dotCollect;

    @BindView(R.id.layout_collect)
    RelativeLayout layoutCollect;

    @BindView(R.id.layout_setting)
    LinearLayout layoutSetting;

    @BindView(R.id.dot_update)
    TextView dotUpdate;

    @BindView(R.id.layout_update)
    LinearLayout layoutUpdate;

    Unbinder unbinder;

    private View settingView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //判断view是否存在
        if (settingView == null) {
            settingView = inflater.inflate(R.layout.fragment_personal, container, false);
        }
        // 缓存的viewiew需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) settingView.getParent();
        if (parent != null) {
            parent.removeView(settingView);
        }
        unbinder = ButterKnife.bind(this, settingView);
        initView();
        return settingView;

    }

    private void initView() {


    }

    @Override
    public void onResume() {
        super.onResume();
        if (BmobUser.isLogin()) {
            BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
            tvNickName.setText(user.getUsername());
        } else {
            tvNickName.setText("请先登录!");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_photo, R.id.layout_setting, R.id.layout_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_photo:
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserInfoActivity.class);
                //传值给下一个Activity
                intent.putExtra("username", tvNickName.getText().toString().trim() + "");
                startActivity(intent);
//                getActivity().startActivity(new Intent(getActivity(), UserInfoActivity.class));
                getActivity().overridePendingTransition(R.anim.bottom_dialog_in, R.anim.bottom_dialog_out);
                break;
            case R.id.layout_setting:

                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), DetailsActivity.class);
                startActivity(intent2);
                break;
            case R.id.layout_update:


                break;
        }
    }

}
