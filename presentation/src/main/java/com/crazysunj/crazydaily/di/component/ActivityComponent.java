package com.crazysunj.crazydaily.di.component;

import android.app.Activity;

import com.crazysunj.crazydaily.di.module.ActivityModule;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.ui.HomeActivity;
import com.crazysunj.crazydaily.ui.ZhihuNewsDetailActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, EntityModule.class})
public interface ActivityComponent {
    Activity getActivity();

    void inject(HomeActivity homeActivity);

    void inject(ZhihuNewsDetailActivity zhihuNewsDetailActivity);
}
