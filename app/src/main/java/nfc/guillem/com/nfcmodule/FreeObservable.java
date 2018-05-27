package nfc.guillem.com.nfcmodule;

import java.util.Observable;

// Make some protected methods public
// Android is evil and every class is extending from another one,
// So Observables must be implemented using an object relationship
public class FreeObservable extends Observable {
    @Override
    public void setChanged() {
        super.setChanged();
    }
    @Override
    public void clearChanged() {
        super.clearChanged();
    }
}
