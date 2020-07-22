package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.TipsAdapter;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.bean.CustomerProjectBean;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：客户信息添加activity
 */

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "CustomerActivity";

    @BindView(R.id.back)
    TextView mBack;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.complete)
    TextView mComplete;
    @BindView(R.id.record_toolbar)
    PercentRelativeLayout mRecordToolbar;
    @BindView(R.id.img_photo)
    CircleImageView mImgPhoto;
    @BindView(R.id.layout_photo)
    PercentRelativeLayout mLayoutPhoto;
    @BindView(R.id.name_ed)
    AutoCompleteTextView mNameEd;
    @BindView(R.id.phone_ed)
    EditText mPhoneEd;

    @BindView(R.id.name_layout)
    PercentLinearLayout mNameLayout;
    @BindView(R.id.male)
    CheckBox mMale;
    @BindView(R.id.femle)
    CheckBox mFemle;
    @BindView(R.id.bir_txt)
    TextView mBirTxt;
    @BindView(R.id.bir_img)
    ImageView mBirImg;
    @BindView(R.id.age)
    EditText mAge;
    @BindView(R.id.createtime_text)
    TextView mCreatetimeText;
    @BindView(R.id.updatetime_text)
    TextView mUpdatetimeText;
    @BindView(R.id.add_content)
    EditText mAddContent;
    @BindView(R.id.num)
    TextView mNum;
    @BindView(R.id.myScrollView)
    ScrollView mMyScrollView;
    @BindView(R.id.img_tx)
    ImageView imgTx;


    private List<CustomerProjectBean> customerProjectsList = new ArrayList<>();//总项目bean


    private List<CustomerProjectsItem> customerProjectsItems = new ArrayList<>();//客户做的项目bean

    private Customer costorBean;

    private TipsAdapter tipsAdapter;

    private NiceSpinner spinner;
    //头像地址
    String picPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
        initView();
        costorBean = new Customer();
    }

    private void initView() {


        //输入姓名提示
        tipsAdapter = new TipsAdapter(this, Constons.costorBeans);

        mNameEd.setAdapter(tipsAdapter);
        mNameEd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemClick: " + tipsAdapter.getItem(position));

            }
        });

        mAddContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNum.setText((s.length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBack.setOnClickListener(this);

        mComplete.setOnClickListener(this);

        mBirImg.setOnClickListener(this);


        //默认女
        CommonUtil.itemUnCheck(mMale);
        mFemle.setChecked(true);


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

        TimePickerPopWin timePickerPopWin = new TimePickerPopWin.Builder(CustomerActivity.this, b, new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int year, int month, int day, int hour, int minute, int second, String time) {

                if (count == 0) {
                    if (Integer.parseInt(Tools.getSameYear()) > year) {
                        mAge.setText((Integer.parseInt(Tools.getSameYear()) - year) + "");
                        mBirTxt.setText(time);
                    } else {
                        ToastUtils.showToast(getBaseContext(), "出生时间错误，请重新输入！", false);
                        mAge.setText("");
                        mBirTxt.setText("");
                    }
                } else if (count == 1) {

                }


            }
        }).btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(CustomerActivity.this);
    }

    //完成
    private void setSubmission() {

        String name = mNameEd.getText().toString();
        String phone = mPhoneEd.getText().toString();
        String sex = mMale.isChecked() ? "男" : "女";
        String bir = mBirTxt.getText().toString();

        String remarks = mAddContent.getText().toString();
        Log.e(TAG, "setSubmission: " + remarks);


        if (!Tools.getIsNull(name)) {
            ToastUtils.showToast(getBaseContext(), "请输入姓名！", false);
            return;
        }
        if (!Tools.getIsNull(phone)) {
            ToastUtils.showToast(getBaseContext(), "请输入手机号！", false);
            return;
        }
        if (!Tools.getIsNull(bir)) {
            ToastUtils.showToast(getBaseContext(), "请输入生日！", false);
            return;
        }

        int age = Integer.parseInt(mAge.getText().toString());
        Log.e(TAG, "setSubmission: "+picPath );

        costorBean.setCustomer_tx(picPath);
        costorBean.setName(name);
        costorBean.setPhone(phone);
        costorBean.setBirthday(bir);
        costorBean.setSex(sex);
        costorBean.setAge(age);
        costorBean.setBmobUser(BmobUser.getCurrentUser(BmobUser.class));
        costorBean.setRemarks(remarks);

        costorBean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    if (objectId != null && objectId != null) {
                        ToastUtils.showToast(getBaseContext(), "创建数据成功", true);
                        Log.e("bmob", "成功");
                        refreshHandler.sendEmptyMessage(1);
                    }
                } else {
                    ToastUtils.showToast(getBaseContext(), "创建数据失败：" + e.getMessage(), false);
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    @Override
    @OnClick({R.id.complete, R.id.bir_img, R.id.male, R.id.femle, R.id.img_photo})
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;

            //头像
            case R.id.img_photo:
                showPickerDialog();
                break;

            //返回
            case R.id.back:
                finish();
                break;

            //完成
            case R.id.complete:
                setSubmission();
                break;

            //生日
            case R.id.bir_img:
                inputImm(v);
                setOnclick(v, 0);
                break;

            case R.id.male:
                CommonUtil.itemUnCheck(mFemle);
                mMale.setChecked(true);
                break;

            case R.id.femle:
                CommonUtil.itemUnCheck(mMale);
                mFemle.setChecked(true);
                break;
        }
    }

    //查询项目beans
    private void defaultSelectProjects() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(Tools.getSameDay());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobQuery<CustomerProjectBean> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        categoryBmobQuery.setLimit(50);
        categoryBmobQuery.findObjects(new FindListener<CustomerProjectBean>() {
            @Override
            public void done(List<CustomerProjectBean> object, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: " + object.size());
                    customerProjectsList.clear();
                    if (object.size() > 0) {
                        customerProjectsList = object;
//                        refreshHandler.sendEmptyMessage(0);
                    }
                } else {
                    ToastUtils.showToast(getBaseContext(), "拉取服务项目列表失败，请检查网络！", false);
//                    Toast.makeText(getBaseContext(), "拉取服务项目列表失败，请检查网络！" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "done: " + e.getMessage().toString());
                }
            }
        });

        //设置默认选中女
        CommonUtil.itemUnCheck(mMale);
        mFemle.setChecked(true);

    }

    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        customerProjectsItems = savedInstanceState.getParcelableArrayList("cs");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("cs", (ArrayList<? extends Parcelable>) customerProjectsItems);

        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }


    private void inputImm(View view) {
        //隐藏输入法
        InputMethodManager imm = (InputMethodManager) CustomerActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }

    /**
     * 显示对话框
     */
    private void showPickerDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_picker_type4picture, null);

        CommonDialog.Builder builder = new CommonDialog.Builder(this);
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
        PictureSelector.create(CustomerActivity.this)
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
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    /**
     * 拍照
     */
    private void takePictrue() {
        PictureSelector.create(CustomerActivity.this)
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
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
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
                            ToastUtils.showShort(CustomerActivity.this, "图片路径获取失败");
                            return;
                        }
                        //加载本地图片
                        ImageLoader.displayLocalImageViewCircle(CustomerActivity.this, mImgPhoto, picPath);

                        //上传图片到服务器
                        uoloadImg(picPath);
                    } catch (Exception e) {
                        ToastUtils.showShort(CustomerActivity.this, "图片路径获取失败");

                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void uoloadImg(String f){

        BmobFile bmobFile = new BmobFile(new File(f));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    picPath=bmobFile.getFileUrl();
                    Log.e(TAG, "done: 上传成功"+bmobFile.getFileUrl() );
                }else{
                    Log.e(TAG,"上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });

    }
}
