package com.bawei.gy.view.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;


import com.bawei.gy.R;
import com.bawei.gy.model.bean.CartBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CartItemAdapter extends BaseQuickAdapter<CartBean.DataBean.ListBean,BaseViewHolder> {

    private   List<CartBean.DataBean.ListBean> bean;

    public CartItemAdapter(int layoutResId, @Nullable List<CartBean.DataBean.ListBean> data) {
        super(layoutResId, data);
        bean= data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CartBean.DataBean.ListBean item) {

        item.setTotalNum(item.getNum());

        /* 获取新的实体类 防止刷新 回到以前*/
        int position = helper.getLayoutPosition();
        final CartBean.DataBean.ListBean listBean = bean.get(position);

        //赋值
        helper.setText(R.id.show_price,"优惠价：¥"+item.getBargainPrice());
        helper.setText(R.id.zu_ed,item.getNum()+"");
        helper.setText(R.id.show2_text,listBean.getTitle());
        helper.setChecked(R.id.up_cb,listBean.isIscheckbox());
        String[] split = item.getImages().split("\\|");
        Glide.with(mContext).load(split[0]).crossFade().into((ImageView) helper.getView(R.id.show2_ima));
        /*获取控件*/
        final CheckBox up_cb = helper.getView(R.id.up_cb);
        /* include引用  点击事件*/
        helper.setText(R.id.zu_btn_del,"-").addOnClickListener(R.id.zu_btn_del);
        helper.setText(R.id.zu_btn_add,"+").addOnClickListener(R.id.zu_btn_add);

        up_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (up_cb.isChecked()){
                    listBean.setIscheckbox(true);
                }else {
                    listBean.setIscheckbox(false);
                }

                //局部刷新
                notifyDataSetChanged();
                /*通知父类刷新*/
               /* if (cartCheckListener!=null){
                    cartCheckListener.notifyParent();}*/
                String ma  ="";
                EventBus.getDefault().post(ma);

            }
        });


    }

}
