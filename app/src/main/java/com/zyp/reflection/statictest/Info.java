package com.zyp.reflection.statictest;

public class Info {
    public String name = "zyp";

    private static Info mCurInfo;

    public static Info curInfo() {
        return mCurInfo;
    }

    public void attach() {
        mCurInfo = this;
    }
}
