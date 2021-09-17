package com.jc_code.googleadds.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.jc_code.googleadds.databinding.AddAccountViewBinding;
import com.jc_code.googleadds.modals.AccountModel;

import java.util.List;

public class UserAdapter extends ListAdapter<AccountModel, UserAdapter.UserVH> {
    List<AccountModel> accountModels;

    public UserAdapter() {
        super(itemCallback);
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AddAccountViewBinding binding = AddAccountViewBinding.inflate(layoutInflater, parent, false);
        return new UserVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        AccountModel accountModel = getItem(position);
        StringBuilder accountText = new StringBuilder();
        accountText.append("" + (position + 1) + ". ");
        accountText.append("Name : " + accountModel.getName());
        accountText.append("\nAccountNumber : " + accountModel.getAccountNumber());
        accountText.append("\nProject Name : " + accountModel.getProjectName());
        accountText.append("\nAmount : " + accountModel.getSanctionAmount());
        accountText.append("\nDate : " + accountModel.getSanctionDate());
        accountText.append("\nMobile 1 : " + accountModel.getMobile1());
        accountText.append("\nMobile 2 : " + accountModel.getMobile2());
        holder.binding.textView.setText(accountText);
    }

    public static class UserVH extends RecyclerView.ViewHolder {
        AddAccountViewBinding binding;

        public UserVH(@NonNull AddAccountViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public static DiffUtil.ItemCallback<AccountModel> itemCallback = new DiffUtil.ItemCallback<AccountModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull AccountModel oldItem, @NonNull AccountModel newItem) {
            return oldItem.getAccountNumber().equalsIgnoreCase(newItem.getAccountNumber());
        }

        @Override
        public boolean areContentsTheSame(@NonNull AccountModel oldItem, @NonNull AccountModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
