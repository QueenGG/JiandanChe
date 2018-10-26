package com.bawei.gy.model;

import com.bawei.gy.model.bean.CartBean;
import com.bawei.gy.model.http.Api;
import com.bawei.gy.utils.RetroHttpUtils;

import io.reactivex.Observable;

/**
 * _ヽ陌路离殇ゞ on 2018/10/25
 */
public class IModel {
    public Observable<CartBean> getCartData() {
        return RetroHttpUtils.getInstan().CreatApi(Api.class).getCart();
    }
}
