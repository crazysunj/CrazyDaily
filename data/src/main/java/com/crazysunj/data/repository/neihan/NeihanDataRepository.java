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
package com.crazysunj.data.repository.neihan;

import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.service.NeihanService;
import com.crazysunj.data.util.RxTransformerUtil;
import com.crazysunj.domain.entity.neihan.NeihanEntity;
import com.crazysunj.domain.repository.neihan.NeihanRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午6:58
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NeihanDataRepository implements NeihanRepository {

    private NeihanService mNeihanService;

    @Inject
    public NeihanDataRepository(HttpHelper httpHelper) {
        mNeihanService = httpHelper.getNeihanService();
    }

    @Override
    public Flowable<NeihanEntity> getNeihanList(int webp, int essence, int content_type, int message_cursor, String am_longitude, String am_latitude, String am_city, long am_loc_time, int count, long min_time, int screen_width, int double_col_mode, String iid, String device_id, String ac, String channel, int aid, String app_name, String version_code, String version_name, String device_platform, String ssmix, String device_type, String device_brand, int os_api, String os_version, String uuid, String openudid, String manifest_version_code, String resolution, String dpi, String update_version_code) {
        return mNeihanService.getNeihanList(webp, essence, content_type, message_cursor, am_longitude, am_latitude, am_city, am_loc_time, count, min_time, screen_width, double_col_mode, iid, device_id, ac, channel, aid, app_name, version_code, version_name, device_platform, ssmix, device_type, device_brand, os_api, os_version, uuid, openudid, manifest_version_code, resolution, dpi, update_version_code)
                .compose(RxTransformerUtil.normalTransformer());
    }
}
