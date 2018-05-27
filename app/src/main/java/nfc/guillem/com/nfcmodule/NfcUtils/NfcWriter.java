package nfc.guillem.com.nfcmodule.NfcUtils;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.tech.Ndef;

import java.io.IOException;
import java.nio.charset.Charset;

public class NfcWriter extends NfcListener {

    public NfcWriter(Ndef ndef) {
        super(ndef);
    }

    public NfcWriter(Ndef ndef, NfcStrategy success, NfcStrategy error) {
        super(ndef, success, error);
    }

    @Override
    protected void onNfcDetected(NfcModel object) {
        writeToNfc(object);
    }

    private void writeToNfc(NfcModel toWrite) {
        String content = toWrite.toNfcFormat();
        NdefRecord mimeRecord = NdefRecord.createMime("text/plain", content.getBytes(Charset.forName("US-ASCII")));
        try {
            ndef.writeNdefMessage(new NdefMessage(mimeRecord));
        } catch (IOException | FormatException e) {
            throw new UncheckedNfcException();
        }
    }
}
