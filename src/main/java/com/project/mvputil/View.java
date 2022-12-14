package com.project.mvputil;

public interface View<P extends  Presenter> {
    P getPresenter();
}
