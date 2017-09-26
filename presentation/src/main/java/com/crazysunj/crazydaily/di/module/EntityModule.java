package com.crazysunj.crazydaily.di.module;

import com.crazysunj.crazydaily.entity.ExpandCollapseFooterEntity;
import com.crazysunj.domain.entity.CommonHeaderEntity;
import com.crazysunj.domain.entity.GankioEntity;
import com.crazysunj.domain.entity.NeihanItemEntity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class EntityModule {

    public static final String NAME_ZHIHU = "zhihu";
    public static final String NAME_GANK_IO = "gankio";
    public static final String NAME_NEIHAN = "neihan";

    @Named(NAME_ZHIHU)
    @Provides
    CommonHeaderEntity providerZhihuHeader() {
        return new CommonHeaderEntity(ZhihuNewsEntity.StoriesEntity.HEADER_TITLE, ZhihuNewsEntity.StoriesEntity.TYPE_ZHIHU_NEWS, ZhihuNewsEntity.StoriesEntity.HEADER_TITLE, ZhihuNewsEntity.StoriesEntity.HEADER_OPTIONS);
    }

    @Named(NAME_NEIHAN)
    @Provides
    CommonHeaderEntity providerNeihanHeader() {
        return new CommonHeaderEntity(NeihanItemEntity.HEADER_TITLE, NeihanItemEntity.TYPE_NEIHAN, NeihanItemEntity.HEADER_TITLE, NeihanItemEntity.HEADER_OPTIONS);
    }

    @Named(NAME_ZHIHU)
    @Provides
    ExpandCollapseFooterEntity providerZhihuFooter() {
        return new ExpandCollapseFooterEntity(ZhihuNewsEntity.StoriesEntity.TYPE_ZHIHU_NEWS);
    }

    @Named(NAME_GANK_IO)
    @Provides
    ExpandCollapseFooterEntity providerGankioFooter() {
        return new ExpandCollapseFooterEntity(GankioEntity.ResultsEntity.TYPE_GANK_IO);
    }
}
