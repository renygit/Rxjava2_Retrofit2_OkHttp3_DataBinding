package com.reny.mvpvmlib;

import io.reactivex.disposables.Disposable;

public interface Presenter {

    void onCreate();

    //解除依赖
    void onDestroy();

    void loadData(boolean isRefresh);

    void addDisposable(Disposable disposable);

    void onFailure(Throwable e);
}
