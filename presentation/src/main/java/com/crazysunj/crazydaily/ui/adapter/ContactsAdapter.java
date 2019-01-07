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
package com.crazysunj.crazydaily.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseHelperAdapter;
import com.crazysunj.crazydaily.entity.ContactLoadingEntity;
import com.crazysunj.crazydaily.extension.Extension;
import com.crazysunj.crazydaily.extension.ItemTouchHelperExtension;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.ui.adapter.helper.ContactAdapterHelper;
import com.crazysunj.crazydaily.ui.contact.ContactDetailActivity;
import com.crazysunj.crazydaily.ui.shimmer.ShimmerViewHolder;
import com.crazysunj.domain.entity.contact.Contact;
import com.crazysunj.domain.entity.contact.ContactHeader;
import com.crazysunj.domain.entity.contact.MultiTypeIndexEntity;
import com.crazysunj.multitypeadapter.adapter.LoadingEntityAdapter;
import com.crazysunj.multitypeadapter.helper.AdapterHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: sunjian
 * created on: 2018/4/16 下午6:39
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ContactsAdapter extends BaseHelperAdapter<MultiTypeIndexEntity, ContactsAdapter.ContactViewHolder, ContactAdapterHelper> {

    private ItemTouchHelperExtension mItemTouchHelper;

    public ContactsAdapter(ItemTouchHelperExtension touchHelper) {
        super(new ContactAdapterHelper(null));
        mItemTouchHelper = touchHelper;
        mHelper.setLoadingAdapter(new LoadingEntityAdapter<MultiTypeIndexEntity>() {
            @Override
            public MultiTypeIndexEntity createLoadingEntity(int type, int level) {
                return new ContactLoadingEntity(type);
            }

            @Override
            public MultiTypeIndexEntity createLoadingHeaderEntity(int type, int level) {
                return new ContactLoadingEntity(type);
            }

            @Override
            public void bindLoadingEntity(MultiTypeIndexEntity loadingEntity, int position) {

            }
        });
    }

    public void notifyContacts(List<MultiTypeIndexEntity> contacts) {
        mHelper.notifyDataByDiff(contacts);
    }

    public void showLoading() {
        AdapterHelper.with(ContactAdapterHelper.CONTACT_LEVEL)
                .loading()
                .header()
                .data(10)
                .into(mHelper);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ContactViewHolder holder) {
        holder.startAnim();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ContactViewHolder holder) {
        holder.stopAnim();
    }

    @Override
    protected void convert(@NonNull ContactViewHolder holder, MultiTypeIndexEntity item) {
        switch (item.getItemType()) {
            case Contact.TYPE:
                renderContact(holder, (Contact) item);
                break;
            case ContactHeader.TYPE:
                renderContactHeader(holder, (ContactHeader) item);
                break;
            default:
                break;
        }
    }

    private void renderContactHeader(ContactViewHolder holder, ContactHeader item) {
        holder.setText(R.id.index, item.getStringId());
        View icon = holder.getView(R.id.header_icon);
        final boolean flod = item.isFlod();
        holder.setVisible(R.id.bottom_line, !flod);
        if (flod) {
            icon.animate().cancel();
            icon.setRotation(-90);
        } else {
            icon.animate().cancel();
            icon.setRotation(0);
        }
        holder.itemView.setOnClickListener(v -> switchStatus(item, icon));
    }

    private void switchStatus(ContactHeader item, View icon) {
        final List<MultiTypeIndexEntity> data = mHelper.getData();
        final int index = data.indexOf(item);
        final String flag = item.getIndex();
        if (item.isFlod()) {
            icon.animate().rotation(90).setDuration(500).start();
            mHelper.addData(index + 1, item.getChilds());
        } else {
            icon.animate().rotation(-90).setDuration(500).start();
            List<MultiTypeIndexEntity> childs;
            int count = 0;
            for (MultiTypeIndexEntity entity : data) {
                if (entity.getIndex().equals(flag)) {
                    count++;
                }
            }
            count--;
            if (count == 0) {
                childs = new ArrayList<>();
            } else {
                childs = mHelper.removeData(index + 1, count);
            }
            item.setChilds(childs);
        }
        item.switchStatus();
        mHelper.setData(index, item);
    }

    private void renderContact(ContactViewHolder holder, Contact contact) {
        CircleImageView icon = holder.getView(R.id.ic_head);
        ImageLoader.load(mContext, contact.getAvatar(), R.mipmap.ic_huaji, icon);
        holder.setText(R.id.tx_name, contact.getName());
        holder.setText(R.id.tx_location, contact.getLocation());
        holder.getView(R.id.content).setOnClickListener(v -> enterContactDetail(v.getContext(), holder, contact));
        holder.getView(R.id.msg).setOnClickListener(v -> enterSms(v.getContext(), contact.getPhone()));
        holder.getView(R.id.call).setOnClickListener(v -> enterPhone(v.getContext(), contact.getPhone()));
        holder.getView(R.id.delete).setOnClickListener(v -> deleteContact(holder));
        holder.setVisible(R.id.bottom_line, !contact.isLast());
    }

    @SuppressWarnings("unchecked")
    private void enterContactDetail(Context context, ContactViewHolder holder, Contact contact) {
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
                Pair.create(holder.getView(R.id.tx_name), activity.getString(R.string.transition_name)),
                Pair.create(holder.getView(R.id.ic_head), activity.getString(R.string.transition_head)),
                Pair.create(holder.getView(R.id.ic_location), activity.getString(R.string.transition_location_icon)),
                Pair.create(holder.getView(R.id.tx_location), activity.getString(R.string.transition_location)));
    }

    private void deleteContact(ContactViewHolder holder) {
        final int position = holder.getLayoutPosition();
        Contact removeData = (Contact) mHelper.removeData(position);
        if (removeData.isLast()) {
            int prePosition = position - 1;
            MultiTypeIndexEntity entity = mHelper.getItem(prePosition);
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

    public static class ContactViewHolder extends ShimmerViewHolder implements Extension {

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
