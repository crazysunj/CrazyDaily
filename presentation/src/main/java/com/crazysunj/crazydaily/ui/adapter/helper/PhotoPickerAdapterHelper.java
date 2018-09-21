package com.crazysunj.crazydaily.ui.adapter.helper;

import com.crazysunj.crazydaily.R;
import com.crazysunj.domain.entity.photo.MediaEntity;
import com.crazysunj.multitypeadapter.helper.AsynAdapterHelper;

/**
 * author: sunjian
 * created on: 2018/9/21 下午1:56
 * description:
 */
public class PhotoPickerAdapterHelper extends AsynAdapterHelper<MediaEntity> {

    public static final int LEVEL_PHOTO_PICKER = 0;

    public PhotoPickerAdapterHelper() {
        super(null);
    }

    @Override
    protected void registerModule() {
        registerModule(LEVEL_PHOTO_PICKER)
                .type(MediaEntity.TYPE_PHOTO_PICKER)
                .layoutResId(R.layout.item_photo_picker)
                .register();
    }
}
