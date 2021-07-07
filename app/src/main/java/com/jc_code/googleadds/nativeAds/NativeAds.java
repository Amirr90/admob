package com.jc_code.googleadds.nativeAds;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class NativeAds extends AdsObject {
    AdLoader adLoader;
    public void initNativeAds(NativeAdsInterface nativeAdsInterface) {
        adLoader = new AdLoader.Builder(getActivity(), getNativeAddId())
                .forNativeAd(nativeAdsInterface::onSuccess)
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        nativeAdsInterface.onFailed(adError.getMessage());
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();


    }

    public void buildAds() {
        if (null != adLoader)
            adLoader.loadAd(new AdRequest.Builder().build());
    }


}
