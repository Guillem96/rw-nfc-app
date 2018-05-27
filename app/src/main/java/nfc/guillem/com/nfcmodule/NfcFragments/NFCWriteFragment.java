package nfc.guillem.com.nfcmodule.NfcFragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nfc.guillem.com.nfcmodule.MainActivity;
import nfc.guillem.com.nfcmodule.R;

public class NFCWriteFragment extends DialogFragment {

    public static final String TAG = NFCWriteFragment.class.getSimpleName();
    private static NFCWriteFragment instance;

    public static NFCWriteFragment newInstance() {
        if(instance == null)
            return new NFCWriteFragment();
        return instance;
    }

    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_write, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (MainActivity)context;
        mListener.onDialogDisplayed();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.onDialogDismissed();
    }

}