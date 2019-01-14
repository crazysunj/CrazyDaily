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
package com.crazysunj.crazydaily.ui.contact;

import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseFragment;
import com.crazysunj.crazydaily.extension.ItemTouchHelperExtension;
import com.crazysunj.crazydaily.presenter.ContactPresenter;
import com.crazysunj.crazydaily.presenter.contract.ContactContract;
import com.crazysunj.crazydaily.ui.adapter.ContactsAdapter;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.domain.entity.contact.MultiTypeIndexEntity;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author: sunjian
 * created on: 2018/4/16 下午6:26
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ContactFragment extends BaseFragment<ContactPresenter> implements ContactContract.View {

    @BindView(R.id.root)
    LinearLayout mRoot;
    @BindView(R.id.rv_contacts)
    RecyclerView mContacts;
    @BindView(R.id.side_bar)
    WaveSideBar mSideBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    private ItemTouchHelperExtension mItemTouchHelper;
    private List<MultiTypeIndexEntity> mContactList;
    private ContactsAdapter mAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initInject() {
        getFragmentComponent()
                .inject(this);
    }

    private static final String[] LETTER = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private boolean isCanScroll = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        showBack(mToolbar);
        addStatusBar(mRoot, getColor(R.color.colorPrimaryDark));
        mContacts.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return isCanScroll && super.canScrollVertically();
            }
        });
        mItemTouchHelper = new ItemTouchHelperExtension(new ContactItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mContacts);
        mAdapter = new ContactsAdapter(mItemTouchHelper);
        mContacts.setAdapter(mAdapter);

        mSideBar.setIndexItems(LETTER);

        mSearchView.setHint(getString(R.string.search_contact));
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);
        mSearchView.dismissSuggestions();
    }

    @Override
    protected void initListener() {
        mSideBar.setOnSelectIndexItemListener(this::handleSideBarSelect);
        mSearchView.setOnQueryTextListener(new SearchListener());
    }

    @Override
    protected void initData() {
        mPresenter.getConactList();
    }

    @Override
    public void showContent(List<MultiTypeIndexEntity> contacts) {
        mContactList = contacts;
        isCanScroll = true;
        mAdapter.notifyContacts(contacts);
    }

    @Override
    public void showLoading() {
        isCanScroll = false;
        mAdapter.showLoading();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mActivity.getMenuInflater().inflate(R.menu.menu_contact, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
    }

    //    @Override
//    public void onBackPressed() {
//        if (mSearchView.isSearchOpen()) {
//            mSearchView.closeSearch();
//            return;
//        }
//        if (mItemTouchHelper.isOpened()) {
//            mItemTouchHelper.closeOpened();
//            return;
//        }
//        super.onBackPressed();
//    }

    private void handleSideBarSelect(String index) {
        if (mContactList == null) {
            return;
        }
        for (int i = 0, size = mContactList.size(); i < size; i++) {
            if (mContactList.get(i).getIndex().equals(index)) {
                //noinspection ConstantConditions
                ((LinearLayoutManager) mContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                return;
            }
        }
    }

    class SearchListener implements MaterialSearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            if (TextUtils.isEmpty(query)) {
                SnackbarUtil.show(mActivity, "请输入搜索内容");
                return false;
            }
            for (int i = 0, size = mContactList.size(); i < size; i++) {
                MultiTypeIndexEntity entity = mContactList.get(i);
                if (entity.getStringId().contains(query) || query.contains(entity.getStringId())) {
                    //noinspection ConstantConditions
                    ((LinearLayoutManager) mContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                    return false;
                }
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                return false;
            }
            for (int i = 0, size = mContactList.size(); i < size; i++) {
                MultiTypeIndexEntity entity = mContactList.get(i);
                if (entity.getStringId().contains(newText) || newText.contains(entity.getStringId())) {
                    //noinspection ConstantConditions
                    ((LinearLayoutManager) mContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                    return false;
                }
            }
            return false;
        }
    }

    static class ContactItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int swipeFlags = viewHolder.getItemViewType() == 0 ? ItemTouchHelper.START : 0;
            return makeMovementFlags(0, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                final ContactsAdapter.ContactViewHolder holder = (ContactsAdapter.ContactViewHolder) viewHolder;
                final float actionWidth = holder.getActionWidth();
                final float translationX = dX < -actionWidth ? -actionWidth : dX;
                final float menuTranslationX = translationX / 2 + actionWidth / 2;
                holder.getView(R.id.content).setTranslationX(translationX);
                holder.getView(R.id.menu).setTranslationX(menuTranslationX);
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    }
}
