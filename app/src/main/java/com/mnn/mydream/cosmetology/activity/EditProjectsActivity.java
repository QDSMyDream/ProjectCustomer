package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.ProjectsShowListAdapter;
import com.mnn.mydream.cosmetology.bean.CustomerProjectBean;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.bean.ProjectsListBean;
import com.mnn.mydream.cosmetology.dialog.AddProjectsDialog;
import com.mnn.mydream.cosmetology.dialog.DeleteDialog;
import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
import com.mnn.mydream.cosmetology.utils.ConvertPinyin;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.SideBar;
import com.mnn.mydream.cosmetology.view.model.CharacterParser;
import com.mnn.mydream.cosmetology.view.model.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class EditProjectsActivity extends AppCompatActivity implements OnItemRecyclerViewClickListener {

    @BindView(R.id.projects_list)
    ListView projectsList;

    @BindView(R.id.dialog)
    TextView dialog;

    @BindView(R.id.sidrbar)
    SideBar sidrbar;

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.title_text)
    TextView titleText;

    @BindView(R.id.complete)
    TextView complete;

    private String TAG = "EditProjectsActivity";

    private ProjectsShowListAdapter projectsShowListAdapter;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    //总项目list
    private List<CustomerProjectBean> customerProjects = new ArrayList<>();

    //列表显示bean
    private List<ProjectsListBean> projectsListBeanList = new ArrayList<>();


    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private DeleteDialog deleteDialog;

    private AddProjectsDialog addProjectsDialog;

    private int selectPos;

    private ProjectsListBean selectProjectsListBean;

    private CustomerProjectBean addCustomerProjectBean;


    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_projects);
        ButterKnife.bind(this);

        initView();

        getSelectPeojects();
    }


    //初始化
    private void initView() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        sidrbar.setTextView(dialog);
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = projectsShowListAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    projectsList.setSelection(position);
                }

            }
        });


    }

    /**
     * 某个时间及以前 获取全部项目
     */
    private void getSelectPeojects() {

        BmobDate bmobCreatedAtDate = new BmobDate(Tools.getCurrentDayDate());
        BmobQuery<CustomerProjectBean> categoryBmobQuery = new BmobQuery<CustomerProjectBean>();
        categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
        categoryBmobQuery.findObjects(new FindListener<CustomerProjectBean>() {
            @Override
            public void done(List<CustomerProjectBean> object, BmobException e) {
                if (e == null) {
                    customerProjects = object;
                    ToastUtils.showToast(getBaseContext(), "查询成功：" + object.size(), true);
                    refreshHandler.sendEmptyMessage(0);
                } else {
                    ToastUtils.showToast(getBaseContext(), "查询失败" + e.toString(), false);
                }
            }
        });
    }


    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://查询
                    projectsListBeanList.clear();

                    for (CustomerProjectBean customerProject : customerProjects) {

                        ProjectsListBean projectsListBean = new ProjectsListBean();
                        projectsListBean.setCustomerProject(customerProject);

                        String sortString = ConvertPinyin.getPinYinHeadChar(customerProject.getcProjects().substring(0, 1));

                        if (sortString.matches("[A-Z]")) {
                            Log.e(TAG, "handleMessage: " + sortString.toUpperCase());
                            projectsListBean.setpField(sortString.toUpperCase());
                        } else {
                            projectsListBean.setpField("#");
                        }
                        projectsListBeanList.add(projectsListBean);

                    }

                    for (ProjectsListBean projectsListBean : projectsListBeanList) {
                        getProjectCount(projectsListBean);
                    }

                    break;

                //添加
                case 1:

                    ProjectsListBean projectsListBean = new ProjectsListBean();
                    projectsListBean.setCustomerProject(addCustomerProjectBean);

                    String sortString = ConvertPinyin.getPinYinHeadChar(addCustomerProjectBean.getcProjects().substring(0, 1));
                    if (sortString.matches("[A-Z]")) {
                        Log.e(TAG, "handleMessage: " + sortString.toUpperCase());
                        projectsListBean.setpField(sortString.toUpperCase());
                    } else {
                        projectsListBean.setpField("#");
                    }

                    projectsListBeanList.add(projectsListBean);

                    Collections.sort(projectsListBeanList, pinyinComparator);
                    projectsShowListAdapter = new ProjectsShowListAdapter(getBaseContext(), projectsListBeanList);
                    projectsShowListAdapter.setOnItemRecyclerViewClickListener(EditProjectsActivity.this);
                    projectsList.setAdapter(projectsShowListAdapter);

                    break;


                //删除
                case 2:
//                    projectsListBeanList.remove(selectProjectsListBean);
                    projectsShowListAdapter.deleteView(selectPos, projectsList);

                    break;

                //修改
                case 3:


                    String pinyin2 = characterParser.getSelling(selectProjectsListBean.getCustomerProject().getcProjects());
                    String sortString2 = pinyin2.substring(0, 1).toUpperCase();

                    if (sortString2.matches("[A-Z]")) {
                        Log.e(TAG, "handleMessage: " + sortString2.toUpperCase());
                        selectProjectsListBean.setpField(sortString2.toUpperCase());
                    } else {
                        selectProjectsListBean.setpField("#");
                    }

                    projectsShowListAdapter.updataView(selectPos, projectsList, selectProjectsListBean);


                    break;

                //查询数量
                case 4:
                    if (count == projectsListBeanList.size()) {
                        // 根据a-z进行排序源数据
                        Collections.sort(projectsListBeanList, pinyinComparator);
                        projectsShowListAdapter = new ProjectsShowListAdapter(getBaseContext(), projectsListBeanList);
                        projectsShowListAdapter.setOnItemRecyclerViewClickListener(EditProjectsActivity.this);
                        projectsList.setAdapter(projectsShowListAdapter);
                        count = 0;
                    }

                    break;


                default:
                    break;
            }
        }
    };

    @OnClick({R.id.back, R.id.complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.complete:

                AddProjectsDialog.Builder addProjectsDialogBuild = new AddProjectsDialog.Builder(EditProjectsActivity.this, new CustomerProjectBean());
                addProjectsDialogBuild.setString(getString(R.string.projects_dialog_add_title), getString(R.string.projects_add_name), getString(R.string.projects_name_txt), getString(R.string.add));
                addProjectsDialogBuild.setVisble(false);
                addProjectsDialogBuild.setYesOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = addProjectsDialog.findViewById(R.id.ed_add_projects);

                        if (editText.getText() == null || editText.getText().toString().equals("")) {
                            ToastUtils.showToast(getBaseContext(), "请输入添加的项目名称!", false);
                            return;
                        }


                        for (CustomerProjectBean customerProjectBean : customerProjects) {
                            if (customerProjectBean.getcProjects().equals(editText.getText().toString())) {
                                ToastUtils.showToast(getBaseContext(), "请误重复添加", false);
                                return;
                            }
                        }


                        CustomerProjectBean customerProject = new CustomerProjectBean();
                        customerProject.setcProjects(editText.getText().toString());
                        customerProject.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    addCustomerProjectBean = customerProject;
                                    ToastUtils.showToast(getBaseContext(), "添加成功", true);
                                    refreshHandler.sendEmptyMessage(1);
                                } else {
                                    ToastUtils.showToast(getBaseContext(), "添加失败" + e.toString(), false);
                                }
                            }
                        });
                        addProjectsDialog.dismiss();

                    }
                });

                addProjectsDialog = addProjectsDialogBuild.createDialog();
                addProjectsDialog.show();


                break;
        }
    }

    @Override
    public void onItemOnClickListener(View view, int pos) {

        selectProjectsListBean = (ProjectsListBean) projectsShowListAdapter.getItem(pos);
        selectPos = pos;

        AddProjectsDialog.Builder addProjectsDialogBuild = new AddProjectsDialog.Builder(EditProjectsActivity.this, selectProjectsListBean.getCustomerProject());
        addProjectsDialogBuild.setString(getString(R.string.projects_dialog_update_title), getString(R.string.projects_update_name), getString(R.string.projects_update_txt), getString(R.string.update));
        addProjectsDialogBuild.setVisble(true);
        addProjectsDialogBuild.setYesOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = addProjectsDialog.findViewById(R.id.ed_add_projects);

                if (editText.getText() == null || editText.getText().toString().equals("")) {
                    ToastUtils.showToast(getBaseContext(), "请输入修改的名称!", false);
                    return;
                }

                selectProjectsListBean.getCustomerProject().setcProjects(editText.getText().toString());

                selectProjectsListBean.getCustomerProject().update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {

                            ToastUtils.showToast(getBaseContext(), "修改成功", true);
                            refreshHandler.sendEmptyMessage(3);
                        } else {
                            ToastUtils.showToast(getBaseContext(), "修改失败" + e.toString(), false);
                        }
                    }
                });
                addProjectsDialog.dismiss();
            }
        });

        addProjectsDialog = addProjectsDialogBuild.createDialog();
        addProjectsDialog.show();
    }


    @Override
    public void onItemLongOnClickListener(View view, int pos) {

        selectProjectsListBean = (ProjectsListBean) projectsShowListAdapter.getItem(pos);
        selectPos = pos;

        if (selectProjectsListBean.getPeojectsCount() > 0) {
            ToastUtils.showToast(getBaseContext(), "有正在使用此项目的顾客,禁止删除", false);

        } else {

            DeleteDialog.Builder builder = new DeleteDialog.Builder(EditProjectsActivity.this);
            //设置对话框的标题
            builder.setTitleMsg("删除服务项目");
            //设置确定按钮
            builder.setYesOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProjectBean(selectProjectsListBean);
                    deleteDialog.dismiss();
                }
            });


            //设置取消按钮
            builder.setCancelOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();

                }
            });
            //使用创建器生成一个对话框对象
            deleteDialog = builder.createDialog();
            deleteDialog.show();


        }

    }


    //查询数据库存在多少个这样的项目
    private void getProjectCount(ProjectsListBean customerProjectsItem) {

        count++;
        BmobQuery<CustomerProjectsItem> query = new BmobQuery<CustomerProjectsItem>();
        query.addWhereEqualTo("customerProjectBean", customerProjectsItem.getCustomerProject());
        query.count(CustomerProjectsItem.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if (e == null) {

                    customerProjectsItem.setPeojectsCount(count);
                    refreshHandler.sendEmptyMessage(4);

                } else {
                    ToastUtils.showToast(getBaseContext(), "查询使用次数失败" + e.getMessage(), false);
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


    //删除
    private void deleteProjectBean(ProjectsListBean selectProjectsListBean) {


        selectProjectsListBean.getCustomerProject().delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "删除成功", true);
                    refreshHandler.sendEmptyMessage(2);
                } else {
                    ToastUtils.showToast(getBaseContext(), "删除失败" + e.toString(), false);
                }

            }
        });


    }
}
