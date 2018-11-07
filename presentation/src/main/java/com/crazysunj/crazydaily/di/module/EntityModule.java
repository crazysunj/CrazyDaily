/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.crazysunj.crazydaily.di.module;

import com.crazysunj.crazydaily.entity.ExpandCollapseFooterEntity;
import com.crazysunj.crazydaily.ui.adapter.helper.HomeAdapterHelper;
import com.crazysunj.domain.entity.common.CommonFooterEntity;
import com.crazysunj.domain.entity.common.CommonHeaderEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.entity.neihan.NeihanItemEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:11
 * description: https://github.com/crazysunj/CrazyDaily
 */
@Module
public class EntityModule {

    public static final String NAME_ZHIHU = "zhihu";
    public static final String NAME_GANK_IO = "gankio";
    public static final String NAME_NEIHAN = "neihan";
    public static final String NAME_GAOXIAO = "gaoxiao";

    @Named(NAME_ZHIHU)
    @Provides
    CommonHeaderEntity providerZhihuHeader() {
        return new CommonHeaderEntity(ZhihuNewsEntity.StoriesEntity.HEADER_TITLE, HomeAdapterHelper.LEVEL_ZHIHU, ZhihuNewsEntity.StoriesEntity.HEADER_TITLE, ZhihuNewsEntity.StoriesEntity.HEADER_OPTIONS);
    }

    @Named(NAME_NEIHAN)
    @Provides
    CommonHeaderEntity providerNeihanHeader() {
        return new CommonHeaderEntity(NeihanItemEntity.HEADER_TITLE, HomeAdapterHelper.LEVEL_NEIHAN, NeihanItemEntity.HEADER_TITLE, NeihanItemEntity.HEADER_OPTIONS);
    }

    @Named(NAME_GAOXIAO)
    @Provides
    CommonHeaderEntity providerGaoxiaoHeader() {
        return new CommonHeaderEntity(GaoxiaoItemEntity.HEADER_TITLE, HomeAdapterHelper.LEVEL_GAOXIAO, GaoxiaoItemEntity.HEADER_TITLE, GaoxiaoItemEntity.HEADER_OPTIONS);
    }

    @Named(NAME_ZHIHU)
    @Provides
    ExpandCollapseFooterEntity providerZhihuFooter() {
        return new ExpandCollapseFooterEntity(HomeAdapterHelper.LEVEL_ZHIHU);
    }

    @Named(NAME_GANK_IO)
    @Provides
    ExpandCollapseFooterEntity providerGankioFooter() {
        return new ExpandCollapseFooterEntity(HomeAdapterHelper.LEVEL_GANK_IO);
    }

    @Named(NAME_GAOXIAO)
    @Provides
    CommonFooterEntity providerGaoxiaoFooter() {
        return new CommonFooterEntity(GaoxiaoItemEntity.HEADER_TITLE, HomeAdapterHelper.LEVEL_GAOXIAO, "我是有底线的");
    }
}
