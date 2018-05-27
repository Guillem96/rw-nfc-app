package nfc.guillem.com.nfcmodule.Models;

import nfc.guillem.com.nfcmodule.NfcUtils.NfcModel;
import nfc.guillem.com.nfcmodule.NfcUtils.NfcModelFactory;

// FACTORY METHOD
// Creates an NfcModel from an string, used by the NfcReader to create an instance after reading
// the tag
public class PlainTextFactory implements NfcModelFactory {
    @Override
    public NfcModel create(String content) {
        return new PlainTextBuilder().text(content).build();
    }

    // BUILDER
    private static class PlainTextBuilder {
        private String text;

        PlainTextBuilder text(String text) {
            this.text = text;
            return this;
        }

        PlainText build() {
            return  new PlainText(text);
        }
    }
}
