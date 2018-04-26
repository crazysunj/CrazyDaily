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
package com.crazysunj.crazydaily.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.extension.Extension;
import com.crazysunj.crazydaily.extension.ItemTouchHelperExtension;
import com.crazysunj.crazydaily.ui.adapter.helper.ContactAdapterHelper;
import com.crazysunj.crazydaily.ui.contact.ContactDetailActivity;
import com.crazysunj.domain.entity.contact.Contact;
import com.crazysunj.domain.entity.contact.ContactHeader;
import com.crazysunj.domain.entity.contact.MultiTypeIndexEntity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author: sunjian
 * created on: 2018/4/16 下午6:39
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class ContactsAdapter extends BaseAdapter<MultiTypeIndexEntity, ContactsAdapter.ContactViewHolder, ContactAdapterHelper> {

    private ItemTouchHelperExtension mItemTouchHelper;

    public ContactsAdapter(ItemTouchHelperExtension touchHelper) {
        super(new ContactAdapterHelper(null));
        mItemTouchHelper = touchHelper;
    }

    public void notifyContacts(List<MultiTypeIndexEntity> contacts) {
        mHelper.notifyDataByDiff(contacts);
    }

    @Override
    protected void convert(ContactViewHolder helper, MultiTypeIndexEntity item) {
        switch (item.getItemType()) {
            case Contact.TYPE:
                renderContact(helper, (Contact) item);
                break;
            case ContactHeader.TYPE:
                renderContactHeader(helper, (ContactHeader) item);
                break;
            default:
                break;
        }
    }

    private void renderContactHeader(ContactViewHolder helper, ContactHeader item) {
        helper.setText(R.id.index, item.getStringId());
        View icon = helper.getView(R.id.header_icon);
        final boolean flod = item.isFlod();
        helper.setVisible(R.id.bottom_line, !flod);
        if (flod) {
            icon.animate().cancel();
            icon.setRotation(-90);
        } else {
            icon.animate().cancel();
            icon.setRotation(0);
        }
        helper.itemView.setOnClickListener(v -> switchStatus(item, icon));
    }

    private void switchStatus(ContactHeader item, View icon) {
        final int index = mData.indexOf(item);
        final String flag = item.getIndex();
        if (item.isFlod()) {
            icon.animate().rotation(90).setDuration(500).start();
            mHelper.addData(index + 1, item.getChilds());
        } else {
            icon.animate().rotation(-90).setDuration(500).start();
            List<MultiTypeIndexEntity> childs = new ArrayList<>();
            int count = 0;
            for (MultiTypeIndexEntity entity : mData) {
                if (entity.getIndex().equals(flag)) {
                    count++;
                }
            }
            count--;
            for (int i = 0; i < count; i++) {
                childs.add(mHelper.removeData(index + 1));
            }
            item.setChilds(childs);
        }
        item.switchStatus();
        mHelper.setData(index, item);
    }

    private void renderContact(ContactViewHolder helper, Contact contact) {
        CircleImageView icon = helper.getView(R.id.ic_head);
        Glide.with(mContext).load(contact.getAvatar()).centerCrop().dontAnimate().into(icon);
        helper.setText(R.id.tx_name, contact.getName());
        helper.setText(R.id.tx_location, contact.getLocation());
        helper.getView(R.id.content).setOnClickListener(v -> enterContactDetail(v.getContext(), helper, contact));
        helper.getView(R.id.msg).setOnClickListener(v -> enterSms(v.getContext(), contact.getPhone()));
        helper.getView(R.id.call).setOnClickListener(v -> enterPhone(v.getContext(), contact.getPhone()));
        helper.getView(R.id.delete).setOnClickListener(v -> deleteContact(helper));
        helper.setVisible(R.id.bottom_line, !contact.isLast());
    }

    @SuppressWarnings("unchecked")
    private void enterContactDetail(Context context, ContactViewHolder helper, Contact contact) {
        if (mItemTouchHelper.isOpened()) {
            mItemTouchHelper.closeOpened();
            return;
        }
        Activity activity = (Activity) context;
        View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (content.isDrawingCacheEnabled()) {
            content.setDrawingCacheEnabled(false);
        }
        content.setDrawingCacheEnabled(true);
        content.buildDrawingCache();
        Bitmap bitmap = content.getDrawingCache();


        ContactDetailActivity.start(activity, bitmap, contact,
                Pair.create(helper.getView(R.id.tx_name), activity.getString(R.string.transition_name)),
                Pair.create(helper.getView(R.id.ic_head), activity.getString(R.string.transition_head)),
                Pair.create(helper.getView(R.id.ic_location), activity.getString(R.string.transition_location_icon)),
                Pair.create(helper.getView(R.id.tx_location), activity.getString(R.string.transition_location)));
    }

    private void deleteContact(ContactViewHolder helper) {
        int position = helper.getLayoutPosition() - getHeaderLayoutCount();
        Contact removeData = (Contact) mHelper.removeData(position);
        if (removeData.isLast()) {
            int prePosition = position - 1;
            MultiTypeIndexEntity entity = mData.get(prePosition);
            if (entity instanceof ContactHeader) {
                mHelper.removeData(prePosition);
            } else {
                ((Contact) entity).setLast(true);
                mHelper.setData(prePosition, entity);
            }
        }
    }

    private void enterPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    private void enterSms(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
        context.startActivity(intent);
    }

    public static class ContactViewHolder extends BaseViewHolder implements Extension {

        private View menu;

        public ContactViewHolder(View view) {
            super(view);
            menu = getView(R.id.menu);
        }

        @Override
        public float getActionWidth() {
            return menu.getWidth();
        }
    }
}
