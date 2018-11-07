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
package com.crazysunj.domain.interactor.neihan;

import com.crazysunj.domain.constant.CodeConstant;
import com.crazysunj.domain.entity.neihan.NeihanEntity;
import com.crazysunj.domain.entity.neihan.NeihanItemEntity;
import com.crazysunj.domain.exception.ApiException;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.neihan.NeihanRepository;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: sunjian
 * created on: 2017/9/5 下午5:34
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NeihanUseCase extends UseCase<List<NeihanItemEntity>, NeihanUseCase.Params> {

    private final NeihanRepository mNeihanRepository;

    @Inject
    public NeihanUseCase(NeihanRepository neihanRepository) {
        mNeihanRepository = neihanRepository;
    }

    @Override
    protected Flowable<List<NeihanItemEntity>> buildUseCaseObservable(Params params) {
        return mNeihanRepository.getNeihanList(params.webp, params.essence, params.content_type, params.message_cursor, params.am_longitude, params.am_latitude, params.am_city, params.am_loc_time, params.count,
                params.min_time, params.screen_width, params.double_col_mode, params.iid, params.device_id, params.ac, params.channel, params.aid, params.app_name, params.version_code, params.version_name,
                params.device_platform, params.ssmix, params.device_type, params.device_brand, params.os_api, params.os_version, params.uuid, params.openudid, params.manifest_version_code, params.resolution, params.dpi,
                params.update_version_code)
                .observeOn(Schedulers.io())
                .flatMap(this::handleException)
                //Objects nonNull api24
                .filter(entity -> entity != null && entity.getGroup() != null && entity.getGroup().getUser() != null)
                .map(NeihanItemEntity::get)
                .toList()
                .toFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Publisher<NeihanEntity.DataEntityX.DataEntity> handleException(NeihanEntity neihanEntity) {
        if (neihanEntity == null) {
            return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
        }
        NeihanEntity.DataEntityX data = neihanEntity.getData();
        if (data == null) {
            return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
        }
        List<NeihanEntity.DataEntityX.DataEntity> entities = data.getData();
        if (entities == null || entities.isEmpty()) {
            return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
        }
        return Flowable.fromIterable(entities);
    }


    public static final class Params {

        private final int webp;
        private final int essence;
        private final int content_type;
        private final int message_cursor;
        private final String am_longitude;
        private final String am_latitude;
        private final String am_city;
        private final long am_loc_time;
        private final long min_time;
        private final int count;
        private final int screen_width;
        private final int double_col_mode;
        private final int aid;
        private final String iid;
        private final String device_id;
        private final String ac;
        private final String channel;
        private final String app_name;
        private final String version_code;
        private final String version_name;
        private final String device_platform;
        private final String ssmix;
        private final String device_type;
        private final String device_brand;
        private final int os_api;
        private final String os_version;
        private final String uuid;
        private final String openudid;
        private final String manifest_version_code;
        private final String resolution;
        private final String dpi;
        private final String update_version_code;

        private Params(long am_loc_time, long min_time, int screen_width, String iid, String device_id, String ac, String version_code, String version_name,
                       String device_type, String device_brand, int os_api, String os_version, String uuid, String openudid, String manifest_version_code, String resolution,
                       String dpi, String update_version_code) {
            this.webp = 1;
            this.essence = 1;
            this.content_type = -104;
            this.message_cursor = -1;
            this.am_longitude = "";
            this.am_latitude = "";
            this.am_city = "";
            this.am_loc_time = am_loc_time;
            this.min_time = min_time;
            this.count = 30;
            this.screen_width = screen_width;
            this.double_col_mode = 0;
            this.iid = iid;
            this.device_id = device_id;
            this.ac = ac;
            this.channel = "360";
            this.aid = 7;
            this.app_name = "joke_essay";
            this.version_code = version_code;
            this.version_name = version_name;
            this.device_platform = "android";
            this.ssmix = "a";
            this.device_type = device_type;
            this.device_brand = device_brand;
            this.os_api = os_api;
            this.os_version = os_version;
            this.uuid = uuid;
            this.openudid = openudid;
            this.manifest_version_code = manifest_version_code;
            this.resolution = resolution;
            this.dpi = dpi;
            this.update_version_code = update_version_code;
        }

        public static Params get(long am_loc_time, long min_time, int screen_width, String iid, String device_id, String ac, String version_code, String version_name,
                                 String device_type, String device_brand, int os_api, String os_version, String uuid, String openudid, String manifest_version_code, String resolution,
                                 String dpi, String update_version_code) {
            return new Params(am_loc_time, min_time, screen_width, iid, device_id, ac, version_code, version_name, device_type, device_brand, os_api, os_version, uuid, openudid, manifest_version_code, resolution, dpi, update_version_code);
        }
    }
}
