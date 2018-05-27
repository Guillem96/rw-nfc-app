package nfc.guillem.com.nfcmodule.NfcUtils;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.tech.Ndef;
import java.io.IOException;

public class NfcReader extends NfcListener {
    private NfcModelFactory factory;
    private String result;

    public NfcReader(Ndef ndef, NfcModelFactory factory, NfcStrategy success, NfcStrategy error) {
        super(ndef, success, error);
        this.factory = factory;
    }

    public NfcReader(Ndef ndef, NfcModelFactory factory) {
        super(ndef);
        this.factory = factory;
    }

    @Override
    protected void onNfcDetected(NfcModel object) {
        readFromNfc();
    }


    private void readFromNfc() {
        try {
            NdefMessage ndefMessage = ndef.getNdefMessage();
            result = new String(ndefMessage.getRecords()[0].getPayload());
        } catch (IOException | FormatException e) {
            throw new UncheckedNfcException();
        }
    }

    public NfcModel getResult() {
        return factory.create(result);
    }
}
