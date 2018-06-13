/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.domain.bus;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * author: sunjian
 * created on: 2018/6/13 下午2:51
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class RxBus {
    private static volatile RxBus instance;
    private FlowableProcessor<Object> processorBus;

    private RxBus() {
        this.processorBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void post(Object event) {
        processorBus.onNext(event);
    }

    public  <T> Flowable<T> toFlowable(Class<T> eventType) {
        return processorBus.ofType(eventType);
    }

    public boolean hasSubscribers() {
        return processorBus.hasSubscribers();
    }
}
