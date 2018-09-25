package com.crazysunj.crazydaily.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * author: sunjian
 * created on: 2018/9/25 上午10:58
 * description:
 */
public abstract class BaseAdapter<T extends Object, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;
    private int mLayoutId;

    public BaseAdapter(@LayoutRes int layoutId) {
        this(null, layoutId);
    }

    public BaseAdapter(List<T> data, @LayoutRes int layoutId) {
        mData = data == null ? new ArrayList<>() : data;
        mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        convert(holder, mData.get(position));
    }

    protected abstract void convert(VH holder, T item);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected VH createBaseViewHolder(ViewGroup parent, int layoutResId) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        return createBaseViewHolder(mLayoutInflater.inflate(layoutResId, parent, false));
    }

    @SuppressWarnings("unchecked")
    protected VH createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        VH vh = createGenericKInstance(z, view);
        return null != vh ? vh : (VH) new BaseViewHolder(view);
    }

    @SuppressWarnings("unchecked")
    private VH createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            String buffer = Modifier.toString(z.getModifiers());
            String className = z.getName();
            if (className.contains("$") && !buffer.contains("static")) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                return (VH) constructor.newInstance(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }
}
