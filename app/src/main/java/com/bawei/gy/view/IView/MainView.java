package com.bawei.gy.view.IView;

import com.bawei.gy.model.bean.CartBean;

import java.util.List;

/**
 * _ヽ陌路离殇ゞ on 2018/10/25
 */
public interface MainView extends BaseView {
        void Success(List<CartBean.DataBean> data);
}
