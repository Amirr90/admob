package com.jc_code.googleadds.nativeAds;

import android.app.Activity;

import com.jc_code.googleadds.Credentials;

public class AdsObject {

    Activity activity;
    String NativeAddId;

    public String getNativeAddId() {
        return null == NativeAddId ? Credentials.NATIVE_ADVANCED_AD_TEST : NativeAddId;
    }

    public void setNativeAddId(String nativeAddId) {
        NativeAddId = nativeAddId;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
