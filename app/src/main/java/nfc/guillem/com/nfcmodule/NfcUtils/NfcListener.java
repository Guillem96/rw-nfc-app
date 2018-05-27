package nfc.guillem.com.nfcmodule.NfcUtils;

import android.nfc.tech.Ndef;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

// TEMPLATE
public abstract class NfcListener implements Observer {
    protected Ndef ndef;
    protected NfcStrategy success;
    protected NfcStrategy error;

    public NfcListener(Ndef ndef) {
        this.ndef = ndef;
    }

    public NfcListener(Ndef ndef, NfcStrategy success, NfcStrategy error) {
        this.ndef = ndef;
        this.success = success;
        this.error = error;
    }

    // TEMPLATE METHOD
    protected abstract void onNfcDetected(NfcModel object);

    // HOOK && STRATEGY
    protected void onSuccess() {
        if(success != null)
            success.execute(this);
    }

    // HOOK && STRATEGY
    protected void onError() {
        if (error != null)
            error.execute(this );
    }

    @Override
    public void update(Observable o, Object args) {
        try {
            ndef.connect();
            onNfcDetected((NfcModel)args);
            ndef.close();
            onSuccess();
        } catch (IOException e) {
            onError();
        }
    }
}
