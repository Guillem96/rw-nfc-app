package nfc.guillem.com.nfcmodule.Models;

import nfc.guillem.com.nfcmodule.NfcUtils.NfcModel;

// Example of NfcModel
// The NfcModels must be capable to serialize in an String format "JSON, XML.."
public class PlainText implements NfcModel {
    public PlainText(String content) {
        this.content = content;
    }

    PlainText() {
    }

    private String content;

    @Override
    public String toNfcFormat() {
        return content;
    }

    @Override
    public void fromNfc(String readed) {
        content = readed;
    }
}
