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
package com.crazysunj.domain.util;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

import javax.inject.Inject;

import androidx.annotation.NonNull;


/**
 * @author: sunjian
 * created on: 2017/9/23 下午9:56
 * description: https://github.com/crazysunj/CrazyDaily
 */
class NeihanPreference extends TrayPreferences {

    private static final String MODEL_NAME = "Neihan";

    @Inject
    NeihanPreference(@NonNull Context context) {
        super(context.getApplicationContext(), MODEL_NAME, 1);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
