package com.crazysunj.data.service;

import com.crazysunj.domain.entity.NeihanEntity;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:36
 * description:
 */

public interface NeihanService {

    String HOST = "http://iu.snssdk.com/";

    @GET("neihan/stream/mix/v1/")
    Flowable<NeihanEntity> getNeihanList(@Query("webp") int webp, @Query("essence") int essence, @Query("content_type") int content_type, @Query("message_cursor") int message_cursor, @Query("am_longitude") String am_longitude,
                                         @Query("am_latitude") String am_latitude, @Query("am_city") String am_city, @Query("am_loc_time") long am_loc_time, @Query("count") int count,
                                         @Query("min_time") long min_time, @Query("screen_width") int screen_width, @Query("double_col_mode") int double_col_mode, @Query("iid") String iid,
                                         @Query("device_id") String device_id, @Query("ac") String ac, @Query("channel") String channel, @Query("aid") int aid,
                                         @Query("app_name") String app_name, @Query("version_code") String version_code, @Query("version_name") String version_name, @Query("device_platform") String device_platform,
                                         @Query("ssmix") String ssmix, @Query("device_type") String device_type, @Query("device_brand") String device_brand, @Query("os_api") int os_api,
                                         @Query("os_version") String os_version, @Query("uuid") String uuid, @Query("openudid") String openudid, @Query("manifest_version_code") String manifest_version_code,
                                         @Query("resolution") String resolution, @Query("dpi") String dpi, @Query("update_version_code") String update_version_code);
}
