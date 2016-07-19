package com.sixtel.instacam;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by branden on 7/19/16.
 */
public class ContractFragment<T> extends Fragment {

    private T mContract;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mContract = (T) getActivity();
        } catch (ClassCastException e) {
            throw new IllegalStateException("Activity does not implement contract");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContract = null;
    }


    public T getContract() {
        return mContract;
    }
}