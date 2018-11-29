package com.mikk.common_base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ddllxy
 * @date 2018/4/2
 */

public class BaseBean implements Parcelable {
    public int code;
    public String msg;

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isSuccess() {
        return code == 200;
    }
    public boolean isShowErrorMsg(){
        return code == 500;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
    }

    public BaseBean() {
    }

    protected BaseBean(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
    }

    public static final Creator<BaseBean> CREATOR = new Creator<BaseBean>() {
        @Override
        public BaseBean createFromParcel(Parcel source) {
            return new BaseBean(source);
        }

        @Override
        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
        }
    };
}
