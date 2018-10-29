package com.crazysunj.data.api;

import android.content.Context;

import com.crazysunj.domain.db.DaoMaster;
import com.crazysunj.domain.db.DaoSession;
import com.crazysunj.domain.db.NoteEntityDao;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * author: sunjian
 * created on: 2018/10/3 下午6:14
 * description: https://github.com/crazysunj/CrazyDaily
 */
@Singleton
public class DBHelper {

    private final DaoSession mDaoSession;

    @Inject
    public DBHelper(Context context) {
        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(context, "crazydaily-db", null).getWritableDatabase()).newSession();
    }

    public NoteEntityDao getNoteDao() {
        return mDaoSession.getNoteEntityDao();
    }
}
