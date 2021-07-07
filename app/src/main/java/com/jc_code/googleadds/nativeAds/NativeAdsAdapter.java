package com.jc_code.googleadds.nativeAds;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jc_code.googleadds.App;
import com.jc_code.googleadds.Credentials;
import com.jc_code.googleadds.R;
import com.jc_code.googleadds.databinding.NativeAdsViewBinding;
import com.jc_code.googleadds.databinding.UserViewBinding;
import com.jc_code.googleadds.modals.User;

import java.util.ArrayList;
import java.util.List;


public class NativeAdsAdapter extends RecyclerView.Adapter {
    List<User> userList;
    NativeAdsClickListener adsClickListener;

    public NativeAdsAdapter(NativeAdsClickListener adsClickListener) {
        this.adsClickListener = adsClickListener;
    }

    private static final String TAG = "NativeAdsAdapter";

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UserViewBinding userViewBinding = UserViewBinding.inflate(inflater, parent, false);
        NativeAdsViewBinding nativeAdsViewBinding = NativeAdsViewBinding.inflate(inflater, parent, false);
        nativeAdsViewBinding.setAdsClickListener(adsClickListener);

        if (viewType == 1) {
            //view type user
            return new UserVH(userViewBinding);
        } else return new NativeAdsVH(nativeAdsViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User snapshot = userList.get(position);
        try {
            if (getItemViewType(position) == 1) {
                UserVH senderViewHolder = (UserVH) holder;
                senderViewHolder.senderViewBinding.setUser(snapshot);
            } else {
                NativeAdsVH receiverVH = (NativeAdsVH) holder;
                receiverVH.botReceiverViewBinding.setNativeAd(snapshot.getNativeAd());
                Glide.with(App.context)
                        .load(snapshot.getNativeAd().getIcon().getDrawable())
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(receiverVH.botReceiverViewBinding.imageView);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onBindViewHolder: " + e.getLocalizedMessage());
        }
    }

    public void addItem(User user) {
        if (null == userList)
            userList = new ArrayList<>();

        userList.add(user);
        notifyItemInserted(userList.size() - 1);

    }

    public void addItem(User user, int position) {
        if (null == userList)
            userList = new ArrayList<>();

        userList.add(position, user);
        notifyDataSetChanged();
    }

    public void addItems(List<User> user) {
        if (null == userList)
            userList = new ArrayList<>();

        userList.addAll(user);
        notifyItemInserted(userList.size() - 1);
    }


    @Override
    public int getItemViewType(int position) {
        if (userList.get(position).getType().equalsIgnoreCase(Credentials.TYPE_NATIVE_AD))
            return 0;
        else return 1;

    }

    @Override
    public int getItemCount() {
        return null == userList ? 0 : userList.size();
    }

    public class UserVH extends RecyclerView.ViewHolder {
        UserViewBinding senderViewBinding;

        public UserVH(@NonNull UserViewBinding senderViewBinding) {
            super(senderViewBinding.getRoot());
            this.senderViewBinding = senderViewBinding;
        }
    }

    public class NativeAdsVH extends RecyclerView.ViewHolder {
        NativeAdsViewBinding botReceiverViewBinding;

        public NativeAdsVH(@NonNull NativeAdsViewBinding botReceiverViewBinding) {
            super(botReceiverViewBinding.getRoot());
            this.botReceiverViewBinding = botReceiverViewBinding;
        }
    }
}
