//package com.mnn.mydream.cosmetology.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.makeramen.roundedimageview.RoundedImageView;
//import com.mnn.mydream.cosmetology.R;
//import com.mnn.mydream.cosmetology.bean.Customer;
//import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
//import com.mnn.mydream.cosmetology.utils.StringUtils;
//import com.mnn.mydream.cosmetology.utils.ToastUtils;
//import com.mnn.mydream.cosmetology.view.SwipeListLayout;
//import com.zhy.android.percent.support.PercentLinearLayout;
//import com.zhy.android.percent.support.PercentRelativeLayout;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * 创建人 :MyDream
// * 创建时间：2020/5/3 18:18
// * 类描述：总记录客户记录列表适配器
// */
//public class GeneralRecordRecycleAdapter extends RecyclerView.Adapter {
//
//    private String TAG = "GeneralRecordRecycleAdapter";
//    private final List<Customer> comingslist;
//    private final Context mContext;
//    private final LayoutInflater mLayoutInflater;
//    private Set<SwipeListLayout> sets = new HashSet();
//    View.OnClickListener onClickListenerLayout;
//    View.OnLongClickListener onLongClickListenerLayout;
//
//    public GeneralRecordRecycleAdapter(Context mContext, List<Customer> comingslist, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListenerLayout) {
//        this.onClickListenerLayout = onClickListener;
//        this.onLongClickListenerLayout = onLongClickListenerLayout;
//        this.mContext = mContext;
//        this.comingslist = comingslist;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new SameDayRecycleViewHolder(mLayoutInflater.inflate(R.layout.general_record_recycle_item_layout, null));
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        SameDayRecycleViewHolder myholder = (SameDayRecycleViewHolder) holder;
//        myholder.setData(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return comingslist.size();
//    }
//
//
//    class SameDayRecycleViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.tx_roundedImageView)
//        RoundedImageView txRoundedImageView;
//        @BindView(R.id.name)
//        TextView names;
//        @BindView(R.id.sex)
//        TextView sexs;
//        @BindView(R.id.age)
//        TextView ages;
//        @BindView(R.id.projects_title)
//        TextView projectsTitle;
//        @BindView(R.id.projects_img)
//        ImageView projectsImg;
//        @BindView(R.id.projects_text)
//        TextView projectsText;
//        @BindView(R.id.time)
//        TextView time;
//        @BindView(R.id.tv_sign_count)
//        TextView tvSignCount;
//        @BindView(R.id.tv_sign_text)
//        TextView tvSignText;
//        @BindView(R.id.sign_layout)
//        PercentRelativeLayout signLayout;
//        @BindView(R.id.item_layout)
//        PercentLinearLayout itemLayout;
//
//        private String s = "";
//        private String countNoSign = "";
//        private String TAG = "";
//
//        SameDayRecycleViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//
//        public void setData(int position) {
//
//            Customer costorBean = comingslist.get(position);
//
//            names.setText(costorBean.getName());
//
//            projectsTitle.setText(costorBean.getPhone());
//
//            sexs.setText(costorBean.getSex());
////            holder.txRoundedImageView.setImageResource("");
////            holder.age.setText(costorBean.getBirthday());
//            ages.setText(setAgeSize(costorBean.getAge() + ""));
////            holder.item_money.setText(costorBean.getMoney() + "");
////            holder.item_puse.setText(costorBean.isIspurchase() + "");
////            holder.item_puse_projects.setText(costorBean.getPurchase_project());
////            holder.item_peojects.setText(costorBean.getProjects());
//            time.setText(costorBean.getCome_time());
////            holder.count.setText(costorBean.getTotal_count() + "");
////            holder.item_txt1.setText(costorBean.getmTxt1());
////            holder.item_txt2.setText(costorBean.getmTxt2());
//            itemLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemRecyclerViewClickListener.onItemOnClickListener(v, position);
//                }
//            });
//            itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    onItemRecyclerViewClickListener.onItemLongOnClickListener(v, position);
//                    return false;
//                }
//            });
//
//
//            projectsImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtils.showToast(mContext, "这是电话图标点击", true);
//                }
//
//            });
//            if (costorBean.getProjectsTexts() != null) {
//                s = "";
//                countNoSign = "";
//                signLayout.setVisibility(View.VISIBLE);
//
//                if (costorBean.getProjectsTexts().size() == 0) {
//                    projectsText.setText("暂无服务记录");
//                    signLayout.setVisibility(View.INVISIBLE);
//
//
//                } else if (costorBean.getProjectsTexts().size() > 0) {
//
//                    Log.e(TAG, "getView: " + costorBean.getProjectsTexts().size());
//
//                    for (int i = 0; i < costorBean.getProjectsTexts().size(); i++) {
//
//                        s = s + costorBean.getProjectsTexts().get(i).getcProjectManagers() + "、";
//
//                        if (!costorBean.getProjectsTexts().get(i).iscSignFlag()) {
//                            countNoSign = (i + 1) + "、";
//                        }
//                    }
//                    Log.e(TAG, "getView: " + countNoSign);
//                    if (!countNoSign.equals("")) {
//
//                        tvSignText.setTextColor(mContext.getResources().getColor(R.color.primary));
//                        tvSignText.setText("项目" + countNoSign.substring(0, countNoSign.length() - 1) + "未签字");
//
//                    } else {
//                        tvSignText.setTextColor(mContext.getResources().getColor(R.color.text_board));
//                        tvSignText.setText("已签字");
//                        tvSignCount.setText(countNoSign);
//
//                    }
//                    projectsText.setText(s.substring(0, s.length() - 1));
//                }
//
//            } else {
//                projectsText.setText("暂无服务记录");
//                signLayout.setVisibility(View.INVISIBLE);
//            }
//
//        }
//
//        public String setAgeSize(String age) {
//            String ages = "";
//            if (StringUtils.isEmpty(age)) {
//                return ages;
//            }
//            if (age.length() == 1) {
//                ages = "0" + age;
//                return ages;
//            } else {
//                return age;
//            }
//        }
//
//
//    }
//
//    public OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;
//
//    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
//        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
//    }
//
//
//}
