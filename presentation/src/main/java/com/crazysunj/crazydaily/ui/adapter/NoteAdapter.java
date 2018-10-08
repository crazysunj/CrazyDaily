package com.crazysunj.crazydaily.ui.adapter;

import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.domain.entity.note.NoteEntity;

/**
 * author: sunjian
 * created on: 2018/10/8 下午4:37
 * description:
 */
public class NoteAdapter extends BaseAdapter<NoteEntity, BaseViewHolder> {

    public NoteAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseViewHolder holder, NoteEntity item) {

    }
}
