package com.app.kalyanbusiness.baseapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.app.kalyanbusiness.R;
import com.app.kalyanbusiness.baseapp.interfaces.EmployeeSelectorListener;
import com.app.kalyanbusiness.baseapp.model.ItemSelection;
import com.app.kalyanbusiness.baseapp.utils.GenericUtils;
import com.app.kalyanbusiness.baseapp.utils.ImageUtils;

import java.util.ArrayList;

public class AdapterListItemEmployeeSelector extends BaseRecyclerViewFilterAdapter<ItemSelection> {

    boolean isMultiSelection;
    EmployeeSelectorListener employeeSelectorListener;

    public AdapterListItemEmployeeSelector(Context context, ArrayList<? extends ItemSelection> items,
                                           EmployeeSelectorListener employeeSelectorListener,
                                           boolean isMultiSelection) {
        super(context, (ArrayList<ItemSelection>) items, null, null);

        this.isMultiSelection = isMultiSelection;
        this.employeeSelectorListener = employeeSelectorListener;
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder viewHolder, ItemSelection val) {
        EmployeeSelectorViewHolder holder = (EmployeeSelectorViewHolder) viewHolder;

        holder.tvName.setText(val.getTitle());
        ImageUtils.setImage(context, val.getTitle(), val.getIcon(), holder.ivProfile,
                true, true, false, R.drawable.image_placeholder);

        ViewGroup.LayoutParams layoutParams = holder.ivProfile.getLayoutParams();
        if (GenericUtils.isStringEmpty(val.getIcon()))
            layoutParams.width = 0;
        else
            layoutParams.width = layoutParams.height;

        holder.ivProfile.setLayoutParams(layoutParams);

        if (isMultiSelection) {
            holder.contCheck.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(val.isSelected());
        } else
            holder.contCheck.setVisibility(View.GONE);

        holder.mainCardView.setOnClickListener(v -> onListItemClicked(holder, val));

        holder.contCheck.setOnClickListener(view -> onListItemClicked(holder, val));
    }

    @Override
    protected boolean isFiltered(ItemSelection item, String text) {
        return item.getTitle() != null && item.getTitle().toLowerCase().contains(text.toLowerCase());
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_item_employee_selector;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        return new EmployeeSelectorViewHolder(view);
    }

    public ArrayList<ItemSelection> getSelectedItems() {
        if (itemsCopy == null)
            return new ArrayList<>();

        ArrayList<ItemSelection> selectedItems = new ArrayList<>();
        for (ItemSelection item : itemsCopy) {
            if (item.isSelected())
                selectedItems.add(item);
        }

        return selectedItems;
    }

    public ArrayList<Integer> getSelectedItemsIds() {
        ArrayList<Integer> selectedIds = new ArrayList<>();
        ArrayList<ItemSelection> selectedItems = getSelectedItems();
        for (ItemSelection selectedItem : selectedItems)
            selectedIds.add((int) selectedItem.getId());

        return selectedIds;
    }

    public void onListItemClicked(EmployeeSelectorViewHolder holder, ItemSelection val) {
        if (isMultiSelection) {
            val.setSelected(!holder.checkBox.isChecked());
            holder.checkBox.setChecked(!holder.checkBox.isChecked());
            //updateItems(items);
        } else
            employeeSelectorListener.onEmployeeSelected(holder.getAdapterPosition(), val);
    }

}
