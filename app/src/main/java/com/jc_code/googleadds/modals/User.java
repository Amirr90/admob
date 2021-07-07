package com.jc_code.googleadds.modals;

import com.google.android.gms.ads.nativead.NativeAd;

public class User {
    String name;
    String image;
    String address;
    String type;
    NativeAd nativeAd;

    public User(String name, String image, String address, String type, NativeAd nativeAd) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.type = type;
        this.nativeAd = nativeAd;
    }

    public NativeAd getNativeAd() {
        return nativeAd;
    }

    public void setNativeAd(NativeAd nativeAd) {
        this.nativeAd = nativeAd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
