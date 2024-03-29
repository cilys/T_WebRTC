package com.cily.utils.app.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.cily.utils.app.pools.Pools;
import com.cily.utils.base.Logs;


/**
 * user:cily
 * time:2017/1/24
 * desc:可复用的Event
 */

public final class Event implements Parcelable {
    public final static int APP_EXIT = -9;
    public int what;
    public Object obj;
    public boolean isUsed = false;

    /**注释需放开*/
//    private final static Pools.SynchronizedPool<Event> sPool = new Pools.SynchronizedPool<Event>();

    public static Event obtain(){
        return obtain(0, null);
    }

    public static Event obtain(int what, Object obj){
        /**注释需放开*/
//        Event e = sPool.acquire();
//        if (e == null){
//            e = new Event();
//        }
        Event e = new Event();
        e.what = what;
        e.obj = obj;
        e.isUsed = false;
        return e;
    }

    public void recycle(){
        /**注释需放开*/
//        if (sPool != null){
//            this.what = -1;
//            this.obj = null;
//            this.isUsed = true;
//            sPool.release(this);
//        }
    }

    public Event(){
    }

    protected Event(Parcel in) {
        what = in.readInt();
        obj = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(what);
        if (obj != null){
            if (obj instanceof Parcelable) {
                Parcelable p = (Parcelable) obj;
                dest.writeParcelable(p, flags);
            }else{
                Logs.sysErr("The obj must be implements Parcelable!");
            }
        }
    }
}
