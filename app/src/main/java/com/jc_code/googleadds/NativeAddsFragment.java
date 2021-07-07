package com.jc_code.googleadds;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.jc_code.googleadds.databinding.FragmentNativeAddsBinding;
import com.jc_code.googleadds.modals.User;
import com.jc_code.googleadds.nativeAds.NativeAds;
import com.jc_code.googleadds.nativeAds.NativeAdsAdapter;
import com.jc_code.googleadds.nativeAds.NativeAdsClickListener;
import com.jc_code.googleadds.nativeAds.NativeAdsInterface;


public class NativeAddsFragment extends Fragment implements NativeAdsClickListener {

    FragmentNativeAddsBinding binding;
    NavController navController;
    private static final String TAG = "NativeAddsFragment";
    NativeAdsAdapter adsAdapter;
    NativeAds nativeAds = new NativeAds();

    private AdView adView;
    AdRequest adRequest;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNativeAddsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        initMobileAds();

        setUpNativeAds();

        initAdaptiveBanner(view);

        initRecData();


    }

    private void initAdaptiveBanner(View view) {
        adView = new AdView(requireActivity());
        adView.setAdUnitId(Credentials.BANNER_AD_ID_TEST);
        binding.frameLayout.addView(adView);
        loadBanner();
    }

    private void loadBanner() {
        adRequest =
                new AdRequest.Builder()
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);


        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireActivity(), adWidth);
    }

    private void initRecData() {

        adsAdapter = new NativeAdsAdapter(this);
        binding.recNativeAds.setAdapter(adsAdapter);
        loadData();
    }

    private void loadData() {
        for (int a = 0; a < 50; a++) {
            if (a % 10 == 0) {
                loadAds(nativeAds, a);

            } else
                adsAdapter.addItem(new User("Name " + a, "", "address " + a, Credentials.TYPE_USER, null));
        }
    }

    private void setUpNativeAds() {
        nativeAds.setActivity(requireActivity());
    }

    private void loadAds(NativeAds nativeAds, int position) {
        // nativeAds.setNativeAddId(Credentials.NATIVE_AD_ID_TEST);
        nativeAds.initNativeAds(new NativeAdsInterface() {
            @Override
            public void onSuccess(Object obj) {
                NativeAd nativeAd = (NativeAd) obj;
                Log.d(TAG, "loadAds onSuccess: " + nativeAd.getBody() + nativeAd.getPrice());
                adsAdapter.addItem(new User(null, "", null, Credentials.TYPE_NATIVE_AD, nativeAd), position);
            }

            @Override
            public void onFailed(String msg) {
                Log.d(TAG, "loadAds onFailed: " + msg);
            }
        });
        nativeAds.buildAds();
    }

    private void initMobileAds() {
        MobileAds.initialize(requireActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    @Override
    public void onClick(Object obj) {
        NativeAd nativeAd = (NativeAd) obj;
        Log.d(TAG, "onClick: " + nativeAd.getCallToAction());
    }
}