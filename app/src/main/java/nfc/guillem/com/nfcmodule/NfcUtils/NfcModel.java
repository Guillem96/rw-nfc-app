package nfc.guillem.com.nfcmodule.NfcUtils;

public interface NfcModel {
    String toNfcFormat();
    void fromNfc(String read);
}
