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
package com.crazysunj.data.api;

import android.content.Context;

import com.crazysunj.domain.db.DaoMaster;
import com.crazysunj.domain.db.DaoSession;
import com.crazysunj.domain.db.NoteEntityDao;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author: sunjian
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
