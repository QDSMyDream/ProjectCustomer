package com.mnn.mydream.cosmetology.bmob;

import com.mnn.mydream.cosmetology.bean.fuwuBean.CPDataBean;
import com.mnn.mydream.cosmetology.eventBus.EventBusMsg;
import com.mnn.mydream.cosmetology.eventBus.EventBusSelectMsg;
import com.mnn.mydream.cosmetology.utils.Constons;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.disposables.Disposable;

/**
 * 我所实现的是   所有的bean继承这个类   添加add  删除drop  修改 update 查询select
 */
public class BeanBmob<T> extends BmobObject {

    /**
     * 添加数据库
     */
    public void add(int resultMsg, SaveListener saveListener) {
        if (saveListener == null) {
            save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new EventBusMsg(resultMsg));
                    }
                }
            });
        } else {
            save(saveListener);
        }

    }


    public void dorp(int resultMsg, UpdateListener updateListener) {
        if (updateListener == null) {
            delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new EventBusMsg(resultMsg));
                    }
                }
            });
        } else {
            delete(updateListener);
        }
    }

    public void updateBean(int resultMsg, UpdateListener updateListener) {
        if (updateListener == null) {
            this.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new EventBusMsg(resultMsg));
                    }
                }
            });
        } else {
            this.update(updateListener);
        }
    }

    public void selectBean(int resultMsg) {

        BmobQuery<T> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<T>() {
            @Override
            public void done(List<T> list, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new EventBusSelectMsg(resultMsg));
                }
            }
        });
    }
}
