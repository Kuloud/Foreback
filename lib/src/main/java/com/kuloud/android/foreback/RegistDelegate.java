package com.kuloud.android.foreback;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implements Register/Unregister for common items
 * Created by kuloud on 4/11/16.
 */
public class RegistDelegate<T> {

    private List<T> regedit = new CopyOnWriteArrayList<T>();

    private CallBack mCallback;

    public RegistDelegate() {
    }

    public RegistDelegate(CallBack callback) {
        this.mCallback = callback;
    }

    public void add(T registrant) {
        if (!regedit.contains(registrant)) {
            regedit.add(registrant);
            if (mCallback != null) {
                mCallback.onAdd(registrant);
            }
        }
    }

    public void remove(T registrant) {
        if (regedit.contains(registrant)) {
            if (mCallback != null) {
                mCallback.onRemove(registrant);
            }
            regedit.remove(registrant);
        }
    }

    /**
     *
     */
    public void clean() {
        for (T registrant : regedit) {
            if (registrant instanceof Clearable) {
                ((Clearable) registrant).onClear();
            }
            remove(registrant);
        }
    }

    /**
     * @param registrants
     */
    public void cleanOthers(T... registrants) {
        if (registrants == null) {
            return;
        }
        for (T keepMember : registrants) {
            for (T member : regedit) {
                if (member != keepMember) {
                    if (member instanceof Clearable) {
                        ((Clearable) member).onClear();
                    }
                    remove(member);
                }
            }
        }
    }

    public List<T> getRegedit() {
        return regedit;
    }

    public boolean isEmpty() {
        return regedit.isEmpty();
    }

    public interface CallBack<T> {
        void onAdd(T item);

        void onRemove(T item);
    }
}
