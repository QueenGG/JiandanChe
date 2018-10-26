package com.bawei.gy.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;


import com.bawei.gy.R;
import com.bawei.gy.model.bean.CartBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CartAdapter extends BaseQuickAdapter<CartBean.DataBean,BaseViewHolder> {

    private Context mcontext;
    private List<CartBean.DataBean> bean;
    private int a= -1;

    public List<CartBean.DataBean> getBean() {
        return bean;
    }

    public CartAdapter(int layoutResId, @Nullable List<CartBean.DataBean> data) {
        super(layoutResId, data);
        EventBus.getDefault().register(this);
        bean= data;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shua(String ma) {
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CartBean.DataBean item) {

        final int positions = helper.getLayoutPosition();
        final CartBean.DataBean list = this.bean.get(positions);
        /* */
        helper.setChecked(R.id.z_box, list.getIschekbox());
        helper.setText(R.id.z_text, list.getSellerName());
        /* */
        RecyclerView z_recyclerview = helper.getView(R.id.z_recyclerview);
        final CheckBox z_box = helper.getView(R.id.z_box);
        /* 适配器 */
        z_recyclerview.setLayoutManager(new LinearLayoutManager(mcontext));
        final CartItemAdapter cartItemAdapter = new CartItemAdapter(R.layout.cart_item_layout, list.getList());
        z_recyclerview.setAdapter(cartItemAdapter);


        /* 判断 商家的checkbox 应不应该选中*/
        for (int i = 0; i < list.getList().size(); i++) {

            if (!list.getList().get(i).isIscheckbox()) {
                helper.setChecked(R.id.z_box, false);
                break;
            } else {
                helper.setChecked(R.id.z_box, true);
            }
        }

        /*
         * 商家点击全选 反选
         * */
        z_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (z_box.isChecked()) {

                    list.setIschekbox(true);

                    for (int i = 0; i < list.getList().size(); i++) {
                        list.getList().get(i).setIscheckbox(true);
                    }
                } else {
                    list.setIschekbox(false);
                    for (int i = 0; i < list.getList().size(); i++) {
                        list.getList().get(i).setIscheckbox(false);
                    }

                }
                // notifyDataSetChanged();
                notifyItemChanged(positions);
                String mas ="";
                EventBus.getDefault().post(mas);
            }
        });


        //点击加减
        cartItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                /*每次点击都赋值 确保数据准确*/
                int num = list.getList().get(position).getNum();
                switch (view.getId()) {
                    case R.id.zu_btn_del:
                        if (num == 1) {
                            return;
                        } else {
                            num--;
                            list.getList().get(position).setTotalNum(num);
                            /*我们页面展示的num*/
                            list.getList().get(position).setNum(num);
                        }


                        notifyItemChanged(positions);
                        //      notifydata();
                        String mas ="";
                        EventBus.getDefault().post(mas);
                        break;

                    case R.id.zu_btn_add:
                        num++;
                        list.getList().get(position).setTotalNum(num);
                        list.getList().get(position).setNum(num);
                        //    notifydata();

                        notifyItemChanged(positions);
                        String mas1 ="";
                        EventBus.getDefault().post(mas1);
                        break;

                }
            }
        });

    }

}
