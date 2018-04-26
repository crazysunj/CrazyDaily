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
package com.crazysunj.domain.interactor.contact;

import android.os.Build;
import android.text.TextUtils;

import com.crazysunj.domain.constant.CodeConstant;
import com.crazysunj.domain.entity.contact.Contact;
import com.crazysunj.domain.entity.contact.ContactHeader;
import com.crazysunj.domain.entity.contact.MultiTypeIndexEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoEntity;
import com.crazysunj.domain.exception.ApiException;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.gaoxiao.GaoxiaoRepository;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: sunjian
 * created on: 2017/9/5 下午5:34
 * description: https://github.com/crazysunj/CrazyDaily
 */

public class ContactUseCase extends UseCase<List<MultiTypeIndexEntity>, ContactUseCase.Params> {

    private final GaoxiaoRepository mGaoxiaoRepository;

    @Inject
    public ContactUseCase(GaoxiaoRepository gaoxiaoRepository) {
        mGaoxiaoRepository = gaoxiaoRepository;
    }

    @Override
    protected Flowable<List<MultiTypeIndexEntity>> buildUseCaseObservable(Params params) {
        return mGaoxiaoRepository.getGaoxiaoList(Params.TYPE, params.page)
                .observeOn(Schedulers.io())
                .flatMap(gaoxiaoEntity -> {
                    if (gaoxiaoEntity == null) {
                        return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
                    }
                    List<GaoxiaoEntity.DataEntity> data = gaoxiaoEntity.getData();
                    if (data == null || data.isEmpty()) {
                        return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
                    }
                    List<GaoxiaoEntity.DataEntity> uniqueData;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        uniqueData = data.stream().collect(
                                Collectors.collectingAndThen(
                                        Collectors.toCollection(() -> new TreeSet<>((first, second) -> first.getName().compareTo(second.getName()))), ArrayList::new));
                    } else {
                        Set<GaoxiaoEntity.DataEntity> contactSet = new TreeSet<>((first, second) -> first.getName().compareTo(second.getName()));
                        contactSet.addAll(data);
                        uniqueData = new ArrayList<>(contactSet);
                    }
                    List<Contact> contactData = new ArrayList<>();
                    for (GaoxiaoEntity.DataEntity entity : uniqueData) {
                        final String avatar = entity.getProfile_image();
                        final String name = entity.getName();
                        contactData.add(new Contact(getFirstChar(name), name, avatar, "中国", "10086", false));
                    }
                    Collections.sort(contactData, (first, second) -> first.getIndex().compareTo(second.getIndex()));
                    return Flowable.just(contactData);
                })
                .flatMap(contactData -> {
                    List<MultiTypeIndexEntity> data = new ArrayList<>();
                    String index = "#";
                    int i = 0;
                    Contact firstEntity = contactData.get(i);
                    String firstIndex = firstEntity.getIndex();
                    index = index.equals(firstIndex) ? index : firstIndex;
                    data.add(new ContactHeader(index));
                    data.add(firstEntity);
                    i++;
                    for (int size = contactData.size(); i < size; i++) {
                        Contact contact = contactData.get(i);
                        String currentIndex = contact.getIndex();
                        if (index.equals(currentIndex)) {
                            data.add(contact);
                        } else {
                            index = currentIndex;
                            contactData.get(i - 1).setLast(true);
                            data.add(new ContactHeader(index));
                            data.add(contact);
                        }
                    }
                    return Flowable.just(data);
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static Pattern LETTER_PATTERN = Pattern.compile("[A-Z]{1}");

    private String getFirstChar(String name) {
        final String pinyin = Pinyin.toPinyin(name, "");
        if (TextUtils.isEmpty(pinyin)) {
            return "#";
        }
        final String letter = String.valueOf(pinyin.charAt(0));
        if (LETTER_PATTERN.matcher(letter).matches()) {
            return letter;
        }
        return "#";
    }


    public static final class Params {

        private final int page;
        private static final int TYPE = 4;

        private Params(int page) {
            this.page = page;
        }

        public static Params get(int page) {
            return new Params(page);
        }
    }
}
