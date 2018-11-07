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
package com.crazysunj.domain.entity.contact;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.crazysunj.domain.entity.gaoxiao.GaoxiaoEntity;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: sunjian
 * created on: 2018/4/16 下午6:26
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class Contact extends MultiTypeIndexEntity implements Parcelable {
    public static final int TYPE = 0;
    private String index;
    private String name;
    private String avatar;
    private String location;
    private String phone;
    private boolean isLast;

    private static Pattern LETTER_PATTERN = Pattern.compile("[A-Z]{1}");

    private static String getFirstChar(String name) {
        final String pinyin = Pinyin.toPinyin(name, "").toUpperCase();
        if (TextUtils.isEmpty(pinyin)) {
            return "#";
        }
        final String letter = String.valueOf(pinyin.charAt(0));
        if (LETTER_PATTERN.matcher(letter).matches()) {
            return letter;
        }
        return "#";
    }

    public static Contact get(GaoxiaoEntity.DataEntity dataEntity) {
        final String avatar = dataEntity.getProfile_image();
        final String name = dataEntity.getName();
        return new Contact(getFirstChar(name), name, avatar, "中国", "10086", false);
    }

    public Contact(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public Contact(String index, String name, String location) {
        this.index = index;
        this.name = name;
        this.location = location;
    }

    public Contact(String index, String name, boolean isLast) {
        this.index = index;
        this.name = name;
        this.isLast = isLast;
    }

    public Contact(String index, String name, String location, boolean isLast) {
        this.index = index;
        this.name = name;
        this.location = location;
        this.isLast = isLast;
    }

    public Contact(String index, String name, String avatar, String location, String phone, boolean isLast) {
        this.index = index;
        this.name = name;
        this.avatar = avatar;
        this.location = location;
        this.phone = phone;
        this.isLast = isLast;
    }

    public String getPhone() {
        if (TextUtils.isEmpty(phone)) {
            phone = "10086";
        }
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String getStringId() {
        return name;
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public static List<MultiTypeIndexEntity> getEnglishContacts() {
        List<MultiTypeIndexEntity> contacts = new ArrayList<>();
        contacts.add(new ContactHeader("A"));
        contacts.add(new Contact("A", "Abbey"));
        contacts.add(new Contact("A", "Alex"));
        contacts.add(new Contact("A", "Amy"));
        contacts.add(new Contact("A", "Anne", true));
        contacts.add(new ContactHeader("B"));
        contacts.add(new Contact("B", "Betty"));
        contacts.add(new Contact("B", "Bob"));
        contacts.add(new Contact("B", "Brian", true));
        contacts.add(new ContactHeader("C"));
        contacts.add(new Contact("C", "Carl"));
        contacts.add(new Contact("C", "Candy"));
        contacts.add(new Contact("C", "Carlos"));
        contacts.add(new Contact("C", "Charles"));
        contacts.add(new Contact("C", "Christina", true));
        contacts.add(new ContactHeader("D"));
        contacts.add(new Contact("D", "David"));
        contacts.add(new Contact("D", "Daniel", true));
        contacts.add(new ContactHeader("E"));
        contacts.add(new Contact("E", "Elizabeth"));
        contacts.add(new Contact("E", "Eric"));
        contacts.add(new Contact("E", "Eva", true));
        contacts.add(new ContactHeader("F"));
        contacts.add(new Contact("F", "Frances"));
        contacts.add(new Contact("F", "Frank", true));
        contacts.add(new ContactHeader("I"));
        contacts.add(new Contact("I", "Ivy", true));
        contacts.add(new ContactHeader("J"));
        contacts.add(new Contact("J", "James"));
        contacts.add(new Contact("J", "John"));
        contacts.add(new Contact("J", "Jessica", true));
        contacts.add(new ContactHeader("K"));
        contacts.add(new Contact("K", "Karen"));
        contacts.add(new Contact("K", "Karl"));
        contacts.add(new Contact("K", "Kim", true));
        contacts.add(new ContactHeader("L"));
        contacts.add(new Contact("L", "Leon"));
        contacts.add(new Contact("L", "Lisa", true));
        contacts.add(new ContactHeader("P"));
        contacts.add(new Contact("P", "Paul"));
        contacts.add(new Contact("P", "Peter", true));
        contacts.add(new ContactHeader("S"));
        contacts.add(new Contact("S", "Sarah"));
        contacts.add(new Contact("S", "Steven", true));
        contacts.add(new ContactHeader("R"));
        contacts.add(new Contact("R", "Robert"));
        contacts.add(new Contact("R", "Ryan", true));
        contacts.add(new ContactHeader("T"));
        contacts.add(new Contact("T", "Tom"));
        contacts.add(new Contact("T", "Tony", true));
        contacts.add(new ContactHeader("W"));
        contacts.add(new Contact("W", "Wendy"));
        contacts.add(new Contact("W", "Will"));
        contacts.add(new Contact("W", "William", true));
        contacts.add(new ContactHeader("Z"));
        contacts.add(new Contact("Z", "Zoe", true));
        return contacts;
    }

    public static List<MultiTypeIndexEntity> getChineseContacts() {
        List<MultiTypeIndexEntity> contacts = new ArrayList<>();
        contacts.add(new ContactHeader("B"));
        contacts.add(new Contact("B", "白虎", "中国", true));
        contacts.add(new ContactHeader("C"));
        contacts.add(new Contact("C", "常羲", "中国"));
        contacts.add(new Contact("C", "嫦娥", "中国", true));
        contacts.add(new ContactHeader("E"));
        contacts.add(new Contact("E", "二郎神", "中国", true));
        contacts.add(new ContactHeader("F"));
        contacts.add(new Contact("F", "伏羲", "中国", true));
        contacts.add(new ContactHeader("G"));
        contacts.add(new Contact("G", "观世音", "中国", true));
        contacts.add(new ContactHeader("J"));
        contacts.add(new Contact("J", "精卫", "中国", true));
        contacts.add(new ContactHeader("K"));
        contacts.add(new Contact("K", "夸父", "中国", true));
        contacts.add(new ContactHeader("N"));
        contacts.add(new Contact("N", "女娲", "中国"));
        contacts.add(new Contact("N", "哪吒", "中国", true));
        contacts.add(new ContactHeader("P"));
        contacts.add(new Contact("P", "盘古", "中国", true));
        contacts.add(new ContactHeader("Q"));
        contacts.add(new Contact("Q", "青龙", "中国", true));
        contacts.add(new ContactHeader("R"));
        contacts.add(new Contact("R", "如来", "中国", true));
        contacts.add(new ContactHeader("S"));
        contacts.add(new Contact("S", "孙悟空", "中国"));
        contacts.add(new Contact("S", "沙僧", "中国"));
        contacts.add(new Contact("S", "顺风耳", "中国", true));
        contacts.add(new ContactHeader("T"));
        contacts.add(new Contact("T", "太白金星", "中国"));
        contacts.add(new Contact("T", "太上老君", "中国", true));
        contacts.add(new ContactHeader("X"));
        contacts.add(new Contact("X", "羲和", "中国"));
        contacts.add(new Contact("X", "玄武", "中国", true));
        contacts.add(new ContactHeader("Z"));
        contacts.add(new Contact("Z", "猪八戒", "中国"));
        contacts.add(new Contact("Z", "朱雀", "中国"));
        contacts.add(new Contact("Z", "祝融", "中国", true));
        return contacts;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.index);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.location);
        dest.writeString(this.phone);
        dest.writeByte(this.isLast ? (byte) 1 : (byte) 0);
    }

    protected Contact(Parcel in) {
        this.index = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.location = in.readString();
        this.phone = in.readString();
        this.isLast = in.readByte() != 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
