package com.me.mvx.model;

import com.me.mvx.base.BaseLoadListener;
import com.me.mvx.bean.SimpleNewsBean;

public interface INewsModel {
    /**
     * 获取新闻数据
     *
     * @param page         页数
     * @param loadListener
     */
    void loadNewsData(int page, BaseLoadListener<SimpleNewsBean> loadListener);
}
