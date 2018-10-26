package com.bawei.gy.model.http;

import com.bawei.gy.model.bean.CartBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * _ヽ陌路离殇ゞ on 2018/10/25
 */
public interface Api {
    @GET("product/getCarts?uid=17224")
    Observable<CartBean> getCart();
}
