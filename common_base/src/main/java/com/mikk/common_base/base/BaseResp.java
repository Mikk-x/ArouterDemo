package com.mikk.common_base.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ddllxy
 * @date 2018/4/2
 */

public class BaseResp implements Parcelable {
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

    public BaseResp() {
    }

    protected BaseResp(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
    }

    public static final Creator<BaseResp> CREATOR = new Creator<BaseResp>() {
        @Override
        public BaseResp createFromParcel(Parcel source) {
            return new BaseResp(source);
        }

        @Override
        public BaseResp[] newArray(int size) {
            return new BaseResp[size];
        }
    };
}
