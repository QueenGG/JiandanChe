package com.bawei.gy.presenter;


import com.bawei.gy.view.IView.BaseView;

/**
 * _ヽ陌路离殇ゞ on 2018/10/22
 */
public class BasePresenter<T extends BaseView>  {
    private T it;

    public void attchView(T t) {
        this.it=t;
    }

    public void destorView() {
        this.it=null;
    }

    public T getview(){
        return it;
    }
}
