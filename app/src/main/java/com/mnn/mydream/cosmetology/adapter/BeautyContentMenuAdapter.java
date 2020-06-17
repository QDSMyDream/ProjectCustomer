package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.BeautyContentMenuBean;
import com.mnn.mydream.cosmetology.interfaces.BeautyContentListOnClickListener;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：BeautyActivity 适配中间布局
 */


public class BeautyContentMenuAdapter extends BaseAdapter {
    private String TAG = "BeautyContentMenuAdapter";
    private Context context;
    private List<BeautyContentMenuBean> beautyContentMenuBeans;

    private BeautyContentListOnClickListener mSListener = null;

    public BeautyContentMenuAdapter(Context context, List<BeautyContentMenuBean> beautyContentMenuBeans) {
        this.beautyContentMenuBeans = beautyContentMenuBeans;
        this.context = context;

    }

    @Override
    public int getCount() {
        return beautyContentMenuBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return beautyContentMenuBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.beauty_listview_content_listview_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BeautyContentMenuBean beautyContentMenuBean = beautyContentMenuBeans.get(position);
        holder.title.setText(beautyContentMenuBean.getMenuString());
        Log.e(TAG, "getView: " + beautyContentMenuBean.getBeautyContentMenuItems().size());

        if (beautyContentMenuBean.getBeautyContentMenuItems().size() > 0) {
            switch (beautyContentMenuBean.getBeautyContentMenuItems().size()) {

                case 1:
                    holder.layout1.setVisibility(View.VISIBLE);
                    holder.layout2.setVisibility(View.GONE);
                    holder.layout3.setVisibility(View.GONE);
                    holder.layout4.setVisibility(View.GONE);

                    holder.layout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 1);
                        }
                    });

                    holder.layout1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 1);
                            return true;
                        }
                    });
                    holder.img1.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
                    holder.text1.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuString());

                    break;

                case 2:
                    holder.layout1.setVisibility(View.VISIBLE);
                    holder.layout2.setVisibility(View.VISIBLE);
                    holder.layout3.setVisibility(View.GONE);
                    holder.layout4.setVisibility(View.GONE);

                    holder.layout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 1);
                        }
                    });

                    holder.layout1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 1);
                            return true;
                        }
                    });

                    holder.layout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 2);
                        }
                    });

                    holder.layout2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 2);
                            return true;
                        }
                    });
                    holder.img1.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
                    holder.text1.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuString());
                    holder.img2.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
                    holder.text2.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuString());
                    break;

                case 3:
                    holder.layout1.setVisibility(View.VISIBLE);
                    holder.layout2.setVisibility(View.VISIBLE);
                    holder.layout3.setVisibility(View.VISIBLE);
                    holder.layout4.setVisibility(View.GONE);

                    holder.layout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 1);
                        }
                    });

                    holder.layout1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 1);
                            return true;
                        }
                    });

                    holder.layout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean ,2);
                        }
                    });

                    holder.layout2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 2);
                            return true;
                        }
                    });
                    holder.layout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 3);
                        }
                    });

                    holder.layout3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 3);
                            return true;
                        }
                    });
                    holder.img1.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
                    holder.text1.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuString());
                    holder.img2.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
                    holder.text2.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuString());
                    holder.img3.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(2).getTitleMenuImg());
                    holder.text3.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(2).getTitleMenuString());
                    break;

                case 4:
                    holder.layout1.setVisibility(View.VISIBLE);
                    holder.layout2.setVisibility(View.VISIBLE);
                    holder.layout3.setVisibility(View.VISIBLE);
                    holder.layout4.setVisibility(View.VISIBLE);

                    holder.layout4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 4);
                        }
                    });

                    holder.layout4.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 4);
                            return true;
                        }
                    });

                    holder.layout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 1);
                        }
                    });

                    holder.layout1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 1);
                            return true;
                        }
                    });

                    holder.layout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 2);
                        }
                    });

                    holder.layout2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 2);
                            return true;
                        }
                    });
                    holder.layout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSListener.onClick(v, position, beautyContentMenuBean, 3);
                        }
                    });

                    holder.layout3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            mSListener.onLongClick(v, position, beautyContentMenuBean, 3);
                            return true;
                        }
                    });

                    holder.img1.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
                    holder.text1.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuString());
                    holder.img2.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
                    holder.text2.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuString());
                    holder.img3.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(2).getTitleMenuImg());
                    holder.text3.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(2).getTitleMenuString());
                    holder.img4.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(3).getTitleMenuImg());
                    holder.text4.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(3).getTitleMenuString());

                    break;

                default:
                    break;

            }

        }

//        switch (beautyContentMenuBean.getBeautyContentMenuItems().size()) {
//            case 1:
//                holder.img1.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
//                holder.text1.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
//                break;
//            case 2:
//                holder.img1.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
//                holder.text1.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
//                holder.img2.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
//                holder.text2.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
//                break;
//            case 3:
//                holder.img1.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
//                holder.text1.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
//                holder.img2.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
//                holder.text2.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
//                holder.img3.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(2).getTitleMenuImg());
//                holder.text3.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(2).getTitleMenuImg());
//
//
//                break;
//            case 4:
//                holder.img1.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
//                holder.text1.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(0).getTitleMenuImg());
//                holder.img2.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
//                holder.text2.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(1).getTitleMenuImg());
//                holder.img3.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(2).getTitleMenuImg());
//                holder.text3.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(2).getTitleMenuImg());
//                holder.img4.setImageResource(beautyContentMenuBean.getBeautyContentMenuItems().get(3).getTitleMenuImg());
//                holder.text4.setText(beautyContentMenuBean.getBeautyContentMenuItems().get(4).getTitleMenuImg());
//                break;
//            default:
//                break;
//        }


        return convertView;
    }


    public void setBeautyContentListOnClickListener(BeautyContentListOnClickListener listener) {
        mSListener = listener;
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.layout1)
        PercentRelativeLayout layout1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.text2)
        TextView text2;
        @BindView(R.id.layout2)
        PercentRelativeLayout layout2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.text3)
        TextView text3;
        @BindView(R.id.layout3)
        PercentRelativeLayout layout3;
        @BindView(R.id.img4)
        ImageView img4;
        @BindView(R.id.text4)
        TextView text4;
        @BindView(R.id.layout4)
        PercentRelativeLayout layout4;
        @BindView(R.id.agency_layout)
        PercentLinearLayout agencyLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}