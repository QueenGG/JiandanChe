package com.bawei.gy.presenter;

import android.annotation.SuppressLint;

import com.bawei.gy.model.IModel;
import com.bawei.gy.model.bean.CartBean;
import com.bawei.gy.view.IView.MainView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * _ヽ陌路离殇ゞ on 2018/10/25
 */
public class MainPresenter extends BasePresenter<MainView> {

    private final IModel iModel;

    public MainPresenter() {
        iModel = new IModel();

    }

    @SuppressLint("CheckResult")
    public void getCartDa() {
        iModel.getCartData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CartBean>() {
                    @Override
                    public void accept(CartBean cartBean) throws Exception {
                        List<CartBean.DataBean> data = cartBean.getData();

                        MainView getview = getview();
                        getview.Success(data);
                    }
                });
    }
}
