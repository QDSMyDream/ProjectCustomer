package com.mnn.mydream.cosmetology.fragment.beauty.khfragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.fragmentAdapter.MyViewPagerAdapter;
import com.mnn.mydream.cosmetology.bean.BeautyBeanKh;
import com.mnn.mydream.cosmetology.bean.User;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.pickertime.TimePickerPopWin;
import com.mnn.mydream.cosmetology.utils.CommonUtil;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.CircleImageView;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：DDGLFragment 新建客户
 */

public class XJKHFragment extends SupportFragment implements View.OnClickListener {

    private String TAG = "XJKHFragment";

    Unbinder unbinder;

    private View view;

    /**
     * xjkhView 新建客户
     */
    private AppCompatEditText phoneEd;
    private PercentLinearLayout phoneTipsLayout;
    private AppCompatEditText nameEd;
    private PercentLinearLayout nameTipsLayout;
    private CheckBox male;
    private CheckBox femle;
    private NiceSpinner spinnerHy;
    private PercentRelativeLayout photoTx;

    private CircleImageView imgPhoto;
    private NiceSpinner spinnerMd;
    private PercentLinearLayout hyTipsLayout;
    private TextView birTxt;
    private ImageView birImg;
    private PercentLinearLayout birTipsLayout;
    private AppCompatEditText lyEd;
    private AppCompatEditText jsEd;
    private AppCompatEditText remarksContent;
    private TextView remarksNum;
    private AppCompatButton btnCommit;
    private AppCompatButton btnCancel;
    private List<String> khhyStrings = new ArrayList<>();
    private List<String> khmdStrings = new ArrayList<>();
    //头像地址
    private String picPath = "";

    public static XJKHFragment newInstance() {

        Bundle args = new Bundle();
        XJKHFragment fragment = new XJKHFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xjkh_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;

    }


    private void initView() {
        xjkhViewFindViewInit(view);
        xjkhViewInit();
    }


    //新建客户初始化
    private void xjkhViewFindViewInit(View xjkhView) {

        phoneEd = (AppCompatEditText) xjkhView.findViewById(R.id.phone_ed);
        phoneTipsLayout = (PercentLinearLayout) xjkhView.findViewById(R.id.phone_tips_layout);
        nameEd = (AppCompatEditText) xjkhView.findViewById(R.id.name_ed);
        nameTipsLayout = (PercentLinearLayout) xjkhView.findViewById(R.id.name_tips_layout);
        male = (CheckBox) xjkhView.findViewById(R.id.male);
        femle = (CheckBox) xjkhView.findViewById(R.id.femle);
        spinnerHy = (NiceSpinner) xjkhView.findViewById(R.id.spinner_hy);
        hyTipsLayout = (PercentLinearLayout) xjkhView.findViewById(R.id.hy_tips_layout);
        birTxt = (TextView) xjkhView.findViewById(R.id.bir_txt);
        birImg = (ImageView) xjkhView.findViewById(R.id.bir_img);
        birTipsLayout = (PercentLinearLayout) xjkhView.findViewById(R.id.bir_tips_layout);
        lyEd = (AppCompatEditText) xjkhView.findViewById(R.id.ly_ed);
        jsEd = (AppCompatEditText) xjkhView.findViewById(R.id.js_ed);
        remarksContent = (AppCompatEditText) xjkhView.findViewById(R.id.remarks_content);
        remarksNum = (TextView) xjkhView.findViewById(R.id.remarks_num);
        btnCommit = (AppCompatButton) xjkhView.findViewById(R.id.btn_commit);
        btnCancel = (AppCompatButton) xjkhView.findViewById(R.id.btn_cancel);
        spinnerMd = (NiceSpinner) xjkhView.findViewById(R.id.spinner_md);
        photoTx = (PercentRelativeLayout) xjkhView.findViewById(R.id.layout_photo);

        imgPhoto = xjkhView.findViewById(R.id.img_photo);

        photoTx.setOnClickListener(this);
        male.setOnClickListener(this);
        femle.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        birImg.setOnClickListener(this);
        khhyStrings.clear();
        khhyStrings.add("普通会员");
        khhyStrings.add("白金会员");
        spinnerHy.attachDataSource(khhyStrings);

        khmdStrings.clear();
        khmdStrings.add("苏州曼哈顿店");
        spinnerMd.attachDataSource(khmdStrings);

    }


    //初始化新建顾客
    private void xjkhViewInit() {

        //默认女
        CommonUtil.itemUnCheck(male);
        femle.setChecked(true);

        remarksContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                remarksNum.setText((s.length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        setFocusClick();


    }

    //失去焦点事件
    private void setFocusClick() {

        nameEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    if (!Tools.getIsNull(nameEd.getText().toString())) {
                        nameTipsLayout.setVisibility(View.VISIBLE);
                    } else {
                        nameTipsLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
        phoneEd.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    if (!Tools.getIsNull(phoneEd.getText().toString())) {
                        phoneTipsLayout.setVisibility(View.VISIBLE);
                    } else {
                        phoneTipsLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
        birTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    birTipsLayout.setVisibility(View.VISIBLE);
                } else {
                    birTipsLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }

    }


    private void inputImm(View view) {
        //隐藏输入法
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //时间选择框
    public void setOnclick(View v, final int count) {
        boolean b = false;
        if (count == 1) {
            b = true;
        }
        if (count == 0) {
            b = false;
        }

        TimePickerPopWin timePickerPopWin = new TimePickerPopWin.Builder(getActivity(), b, new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int year, int month, int day, int hour, int minute, int second, String time) {

                if (count == 0) {
                    if (Integer.parseInt(Tools.getSameYear()) > year) {
                        birTxt.setText(time);
                    } else {

                        ToastUtils.showToast(getContext(), "出生时间错误，请重新选择！", false);
                    }

                } else if (count == 1) {

                }

            }
        }).btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(getActivity());
    }


    //保存
    private void saveBeautyCustomer() {

        String name = nameEd.getText().toString();
        String phone = phoneEd.getText().toString();
        String sex = male.isChecked() ? "男" : "女";
        String bir = birTxt.getText().toString();

        String hy = spinnerHy.getSelectedItem().toString();
        String remarks = remarksContent.getText().toString();
        String ly = lyEd.getText().toString();
        String js = jsEd.getText().toString();
        String md = spinnerMd.getSelectedItem().toString();

        if (!Tools.getIsNull(phone)) {
            phoneTipsLayout.setVisibility(View.VISIBLE);
            return;
        }

        if (!Tools.getIsNull(name)) {
            nameTipsLayout.setVisibility(View.VISIBLE);
            return;
        }

        if (!Tools.getIsNull(hy)) {
            hyTipsLayout.setVisibility(View.VISIBLE);
            return;
        }

        if (!Tools.getIsNull(bir)) {
            birTipsLayout.setVisibility(View.VISIBLE);
            return;
        }

        BeautyBeanKh beautyBeanKh = new BeautyBeanKh();
        beautyBeanKh.setTx(picPath);
        beautyBeanKh.setMd(md);
        beautyBeanKh.setBir(bir);
        beautyBeanKh.setHy(hy);
        beautyBeanKh.setPhone(phone);
        beautyBeanKh.setName(name);
        beautyBeanKh.setSex(sex);
        beautyBeanKh.setJs(js);
        beautyBeanKh.setLy(ly);
        beautyBeanKh.setRemarksContent(remarks);
        beautyBeanKh.setBmobUser(BmobUser.getCurrentUser(User.class));
        beautyBeanKh.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    if (s != null && s != null) {
                        ToastUtils.showToast(getContext(), "创建客户数据成功", true);
                        Log.e("bmob", "创建客户数据成功");
                        setdataNull();

                    }
                } else {
                    ToastUtils.showToast(getContext(), "创建数据失败：" + e.getMessage(), false);
                    Log.e("bmob", "创建数据失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }

    //取消
    private void back() {
        setdataNull();
    }


    //输入框填写空
    private void setdataNull() {
        nameEd.setText("");
        phoneEd.setText("");
        male.setChecked(false);
        birTxt.setText("");
        spinnerHy.setSelectedIndex(0);
        remarksContent.setText("");
        lyEd.setText("");
        jsEd.setText("");
        spinnerMd.setSelectedIndex(0);
        imgPhoto.setImageResource(R.mipmap.def_photo);
        picPath = "";

        birTipsLayout.setVisibility(View.GONE);
        hyTipsLayout.setVisibility(View.GONE);
        nameTipsLayout.setVisibility(View.GONE);
        phoneTipsLayout.setVisibility(View.GONE);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.male:
                CommonUtil.itemUnCheck(femle);
                male.setChecked(true);
                break;

            case R.id.femle:
                CommonUtil.itemUnCheck(male);
                femle.setChecked(true);
                break;

            case R.id.bir_img:
                inputImm(v);
                setOnclick(v, 0);
                break;
            case R.id.btn_commit:
                saveBeautyCustomer();
                break;
            case R.id.btn_cancel:
                back();
                break;
            case R.id.layout_photo:
                showPickerDialog();
                break;
        }

    }

    /**
     * 显示对话框
     */
    private void showPickerDialog() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_picker_type4picture, null);

        CommonDialog.Builder builder = new CommonDialog.Builder(getActivity());
        final CommonDialog dialog = builder
                .setStyle(R.style.FadeInPopWin)
                .setDialogMode(CommonDialog.DialogMode.MODE_BOTTOM)
                .setWidthPro(1.0f)
                .cancelTouchout(false)
                .setView(view)
                .build();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        dialog.dismiss();
                        pickPictrue();
                        break;
                    case R.id.tv_camera:
                        dialog.dismiss();
                        takePictrue();
                        break;
                    case R.id.tv_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };

        view.findViewById(R.id.tv_album).setOnClickListener(listener);
        view.findViewById(R.id.tv_camera).setOnClickListener(listener);
        view.findViewById(R.id.tv_cancel).setOnClickListener(listener);

        dialog.show();
    }


    /**
     * 相册
     */
    private void pickPictrue() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.PictureStyle)
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .withAspectRatio(500, 500)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(500, 500)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)

                .forResult(Constons.RESULT_XJKH_TX_SUCCESS);//结果回调onActivityResult code

    }

    /**
     * 拍照
     */
    private void takePictrue() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.PictureStyle)
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .withAspectRatio(500, 500)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(500, 500)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(Constons.RESULT_XJKH_TX_SUCCESS);//结果回调onActivityResult code

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: " + requestCode + resultCode);

        if (Constons.RESULT_XJKH_TX_SUCCESS == requestCode) {
            // 图片、视频、音频选择结果回调
            try {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                picPath = selectList.get(0).getCutPath();

                Log.e(TAG, "onActivityResult: " + picPath);
                if (picPath == null || picPath.equals("")) {
                    ToastUtils.showShort(getActivity(), "图片路径获取失败");
                    return;
                }
                //加载本地图片
                ImageLoader.displayLocalImageViewCircle(getActivity(), imgPhoto, picPath);

                //上传图片到服务器
                uoloadImg(picPath);
            } catch (Exception e) {
                ToastUtils.showShort(getActivity(), "图片路径获取失败");

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uoloadImg(String f) {

        BmobFile bmobFile = new BmobFile(new File(f));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    picPath = bmobFile.getFileUrl();
                    Log.e(TAG, "done: 上传成功" + bmobFile.getFileUrl());
                } else {
                    Log.e(TAG, "上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）


            }
        });

    }


}