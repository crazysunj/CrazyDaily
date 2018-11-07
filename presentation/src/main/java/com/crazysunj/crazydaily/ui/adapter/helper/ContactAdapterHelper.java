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
package com.crazysunj.crazydaily.ui.adapter.helper;

import com.crazysunj.crazydaily.R;
import com.crazysunj.domain.entity.contact.Contact;
import com.crazysunj.domain.entity.contact.ContactHeader;
import com.crazysunj.domain.entity.contact.MultiTypeIndexEntity;
import com.crazysunj.multitypeadapter.helper.AsynAdapterHelper;

import java.util.List;

/**
 * @author: sunjian
 * created on: 2018/4/17 下午3:54
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ContactAdapterHelper extends AsynAdapterHelper<MultiTypeIndexEntity> {

    public static final int CONTACT_LEVEL = 0;

    public ContactAdapterHelper(List<MultiTypeIndexEntity> data) {
        super(data);
    }

    @Override
    protected void registerModule() {
        registerModule(CONTACT_LEVEL)
                .type(Contact.TYPE)
                .layoutResId(R.layout.item_contacts)
                .type(ContactHeader.TYPE)
                .layoutResId(R.layout.item_contacts_header)
                .loading()
                .loadingHeaderResId(R.layout.layout_loading_header)
                .loadingLayoutResId(R.layout.layout_loading_contact)
                .register();
    }
}
