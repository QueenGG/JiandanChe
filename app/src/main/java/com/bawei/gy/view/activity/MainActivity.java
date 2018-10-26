package com.bawei.gy.view.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.gy.R;
import com.bawei.gy.model.bean.CartBean;
import com.bawei.gy.presenter.MainPresenter;
import com.bawei.gy.view.IView.MainView;
import com.bawei.gy.view.adapter.CartAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.f_recyclerView)
    RecyclerView fRecyclerView;
    @BindView(R.id.f_checkbox)
    CheckBox fCheckbox;
    @BindView(R.id.qx_tv)
    TextView qxTv;
    @BindView(R.id.f_price)
    TextView fPrice;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.toShop_btn)
    Button toShopBtn;
    private CartAdapter adapter;
    private List<CartBean.DataBean> data;
    private MainPresenter mainPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        mainPresenter = new MainPresenter();

        mainPresenter.attchView(this);

        mainPresenter.getCartDa();
    }

    @Override
    public void Success(List<CartBean.DataBean> showCartsBean) {

        data=showCartsBean;
        fRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter = new CartAdapter(R.layout.cart_item, showCartsBean);
            fRecyclerView.setAdapter(adapter);

            toShopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity(new Intent(getActivity(), OrderActivity.class));
                }
            });

            fCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fCheckbox.isChecked()) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setIschekbox(true);
                            for (int i1 = 0; i1 < data.get(i).getList().size(); i1++) {
                                data.get(i).getList().get(i1).setIscheckbox(true);
                            }
                        }
                    } else {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setIschekbox(false);
                            for (int i1 = 0; i1 < data.get(i).getList().size(); i1++) {
                                data.get(i).getList().get(i1).setIscheckbox(false);
                            }
                        }
                    }
                    /*  */
                    totalPrice();
                    adapter.notifyDataSetChanged();
                }

            });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shub(String mas) {

        adapter.notifyDataSetChanged();
        totalPrice();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shub2(String mas1) {

        adapter.notifyDataSetChanged();
        totalPrice();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shua(String ma) {
        adapter.notifyDataSetChanged();

        StringBuilder stringBuilder = new StringBuilder();
        if (adapter != null) {
            for (int i = 0; i < adapter.getBean().size(); i++) {

                stringBuilder.append(adapter.getBean().get(i).getIschekbox());

                for (int i1 = 0; i1 < adapter.getBean().get(i).getList().size(); i1++) {

                    stringBuilder.append(adapter.getBean().get(i).getList().get(i1).isIscheckbox());

                }
            }
        }
        if (stringBuilder.toString().contains("false")) {
            fCheckbox.setChecked(false);
        } else {
            fCheckbox.setChecked(true);
        }

        totalPrice();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.destorView();
        EventBus.getDefault().unregister(this);
    }

    private void totalPrice() {
        double totalprice = 0;
        /*循环嵌套*/
        for (int i = 0; i < adapter.getBean().size(); i++) {
            for (int i1 = 0; i1 < adapter.getBean().get(i).getList().size(); i1++) {
                if (adapter.getBean().get(i).getList().get(i1).isIscheckbox()) {
                    CartBean.DataBean.ListBean listBean = adapter.getBean().get(i).getList().get(i1);
                    totalprice += listBean.getTotalNum() * listBean.getBargainPrice();
                }
            }
        }
        fPrice.setText("合计：￥" + totalprice);
    }
}
