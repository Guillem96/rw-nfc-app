package nfc.guillem.com.nfcmodule.NfcUtils;

// STRATEGY
@FunctionalInterface
public interface NfcStrategy {
    void execute(NfcListener nfcListener);
}
