package com.app.kalyanbusiness.baseapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.kalyanbusiness.baseapp.interfaces.OnListItemClickListener;
import com.app.kalyanbusiness.baseapp.presenters.BaseFragmentView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseRecyclerViewFilterAdapter<T> extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<T> items;
    protected final Context context;
    public ArrayList<T> itemsCopy = new ArrayList<>();
    WeakReference<BaseFragmentView> fragmentViewWeakReference;
    public OnListItemClickListener onListItemClickListener;

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    protected abstract boolean isFiltered(T item, String text);

    public void updateItem(int index, T item) {
        items.set(index, item);
        try {
            itemsCopy.set(index, item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyItemChanged(index);
    }

    public BaseRecyclerViewFilterAdapter(Context context, ArrayList<T> items, BaseFragmentView baseFragmentView,
                                         OnListItemClickListener onListItemClickListener) {
        fragmentViewWeakReference = new WeakReference<>(baseFragmentView);
        this.context = context;
        updateItemsFromConstructor(items);
        this.onListItemClickListener = onListItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        return getViewHolder(view, viewType);
    }

    public Context getContext() {
        return context;
    }

    public ArrayList<T> getItems() {
        return items;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (items.size() > 0)
            onBindData(holder, items.get(position));
        else
            onBindData(holder, null);
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateItems(ArrayList<T> list) {
        items = list;
        itemsCopy.clear();
        itemsCopy.addAll(list);
        this.notifyDataSetChanged();

        updateNoDataVisiblity(list);
    }

    public void updateItemsFromConstructor(ArrayList<T> list) {
        items = list;
        itemsCopy.clear();
        itemsCopy.addAll(list);
        this.notifyDataSetChanged();
    }

    //override toString() method of T class and return the proper attribute on which filter is required in order to work this properly
    public void filter(String text) {
        items.clear();

        if (text == null || text.isEmpty()) {
            items.addAll(itemsCopy);
        } else {
            text = text.toLowerCase();
            for (T item : itemsCopy) {
                if (isFiltered(item, text.toLowerCase()))
                    items.add(item);
            }
        }

        notifyDataSetChanged();
        updateNoDataVisiblity(items);
    }

    public void updateNoDataVisiblity(ArrayList<T> items) {
        if (fragmentViewWeakReference.get() != null) {
            if (items == null || items.size() == 0)
                fragmentViewWeakReference.get().showNoData();
            else
                fragmentViewWeakReference.get().hideNoData();
        }
    }

    public void addItem(T item) {
        this.items.add(item);
        updateNoDataVisiblity(items);

        this.itemsCopy.add(item);
        this.notifyDataSetChanged();
    }

    public void addItem(int index, T item) {
        this.items.add(index, item);
        this.itemsCopy.add(index, item);
        updateNoDataVisiblity(items);
        this.notifyItemInserted(index);
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void deleteItem(int position) {
        this.items.remove(position);
        this.itemsCopy.remove(position);
        updateNoDataVisiblity(items);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.items.size());
    }

    public void restoreItem(T item, int position) {
        getItems().add(position, item);
        this.itemsCopy.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public abstract int getLayoutId(int viewType);

    public abstract RecyclerView.ViewHolder getViewHolder(View view, int viewType);

    public void reverseItems() {
        Collections.reverse(items);
        notifyDataSetChanged();
    }
}