package com.crazysunj.crazydaily.di.component;

import android.app.Activity;

import com.crazysunj.crazydaily.di.module.FragmentModule;
import com.crazysunj.crazydaily.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();
}
