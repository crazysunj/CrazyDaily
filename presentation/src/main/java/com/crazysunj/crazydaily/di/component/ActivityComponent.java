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
package com.crazysunj.crazydaily.di.component;

import android.app.Activity;

import com.crazysunj.crazydaily.di.module.ActivityModule;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.ui.MainActivity;
import com.crazysunj.crazydaily.ui.contact.ContactActivity;
import com.crazysunj.crazydaily.ui.home.HomeActivity;
import com.crazysunj.crazydaily.ui.note.NoteActivity;
import com.crazysunj.crazydaily.ui.note.NoteEditActivity;
import com.crazysunj.crazydaily.ui.photo.PhotoPickerActivity;
import com.crazysunj.crazydaily.ui.splash.SplashActivity;
import com.crazysunj.crazydaily.ui.zhihu.ZhihuNewsDetailActivity;

import dagger.Component;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:11
 * description: https://github.com/crazysunj/CrazyDaily
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, EntityModule.class})
public interface ActivityComponent {
    Activity getActivity();

    void inject(HomeActivity homeActivity);

    void inject(ZhihuNewsDetailActivity zhihuNewsDetailActivity);

    void inject(ContactActivity contactActivity);

    void inject(SplashActivity splashActivity);

    void inject(PhotoPickerActivity photoPickerActivity);

    void inject(NoteEditActivity noteEditActivity);

    void inject(NoteActivity noteActivity);

    void inject(MainActivity mainActivity);
}
