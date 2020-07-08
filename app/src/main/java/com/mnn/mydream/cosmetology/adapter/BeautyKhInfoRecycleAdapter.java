//package com.mnn.mydream.cosmetology.adapter;//package com.mnn.mydream.cosmetology.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
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
//import com.mnn.mydream.cosmetology.view.SwipeListLayout;
//import com.zhy.android.percent.support.PercentLinearLayout;
//import com.zhy.android.percent.support.PercentRelativeLayout;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import butterknife.BindView;
//import cn.droidlover.xrecyclerview.XRecyclerView;
//
///**
// * 创建人 :MyDream
// * 创建时间：2020/5/3 18:18
// * 类描述：客户详情信息显示界面
// */
//public class BeautyKhInfoRecycleAdapter extends XRecyclerView.Adapter {
//
//    @BindView(R.id.tx_roundedImageView)
//    RoundedImageView txRoundedImageView;
//    @BindView(R.id.name)
//    TextView name;
//    @BindView(R.id.sex)
//    TextView sex;
//    @BindView(R.id.age)
//    TextView age;
//    @BindView(R.id.projects_title)
//    TextView projectsTitle;
//    @BindView(R.id.projects_img)
//    ImageView projectsImg;
//    @BindView(R.id.phone_txt)
//    TextView phoneTxt;
//    @BindView(R.id.phone_img)
//    ImageView phoneImg;
//    @BindView(R.id.time)
//    TextView time;
//    @BindView(R.id.tv_sign_count)
//    TextView tvSignCount;
//    @BindView(R.id.tv_sign_text)
//    TextView tvSignText;
//    @BindView(R.id.sign_layout)
//    PercentRelativeLayout signLayout;
//    @BindView(R.id.item_layout)
//    PercentLinearLayout itemLayout;
//    @BindView(R.id.swipelistlayout)
//    PercentLinearLayout swipelistlayout;
//    private String TAG = "GeneralRecordRecycleAdapter";
//    private final List<Customer> comingslist;
//    private final Context mContext;
//    private final LayoutInflater mLayoutInflater;
//    private Set<SwipeListLayout> sets = new HashSet();
//    View.OnClickListener onClickListenerLayout;
//    View.OnLongClickListener onLongClickListenerLayout;
//
//    public BeautyKhInfoRecycleAdapter(Context mContext, List<Customer> comingslist, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListenerLayout) {
//        this.onClickListenerLayout = onClickListener;
//        this.onLongClickListenerLayout = onLongClickListenerLayout;
//        this.mContext = mContext;
//        this.comingslist = comingslist;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }
//
//    @Override
//    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new SameDayRecycleViewHolder(mLayoutInflater.inflate(R.layout.beauty_kh_info_recycle_item_layout1, null));
//    }
//
//    @Override
//    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
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
//    public OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;
//
//    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
//        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
//    }
//
//
//}
