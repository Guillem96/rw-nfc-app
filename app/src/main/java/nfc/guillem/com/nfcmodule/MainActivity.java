package nfc.guillem.com.nfcmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import nfc.guillem.com.nfcmodule.Models.PlainText;
import nfc.guillem.com.nfcmodule.Models.PlainTextFactory;
import nfc.guillem.com.nfcmodule.NfcFragments.Listener;
import nfc.guillem.com.nfcmodule.NfcUtils.NfcListener;
import nfc.guillem.com.nfcmodule.NfcFragments.NFCReadFragment;
import nfc.guillem.com.nfcmodule.NfcFragments.NFCWriteFragment;
import nfc.guillem.com.nfcmodule.NfcUtils.NfcReader;
import nfc.guillem.com.nfcmodule.NfcUtils.NfcWriter;

// The complex NFC stuff comes from: https://www.learn2crack.com/2016/10/android-reading-and-writing-nfc-tags.html
public class MainActivity extends AppCompatActivity implements Listener {

    private FreeObservable observable;

    private EditText mEtMessage;

    private boolean isDialogDisplayed = false;
    private boolean isWrite = false;

    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initNFC();
        initStructure();
    }

    private void initViews() {

        mEtMessage = (EditText) findViewById(R.id.et_message);
        Button mBtWrite = (Button) findViewById(R.id.btn_write);
        Button mBtRead = (Button) findViewById(R.id.btn_read);

        mBtWrite.setOnClickListener(view -> showWriteFragment());
        mBtRead.setOnClickListener(view -> showReadFragment());

    }

    private void initNFC(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    private void initStructure(){
        observable = new FreeObservable();
    }


    private void showWriteFragment() {
        isWrite = true;
        NFCWriteFragment mNfcWriteFragment = (NFCWriteFragment) getFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);

        if (mNfcWriteFragment == null)
            mNfcWriteFragment = NFCWriteFragment.newInstance();

        mNfcWriteFragment.show(getFragmentManager(),NFCWriteFragment.TAG);
    }

    private void showReadFragment() {
        NFCReadFragment mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);

        if (mNfcReadFragment == null)
            mNfcReadFragment = NFCReadFragment.newInstance();

        mNfcReadFragment.show(getFragmentManager(),NFCReadFragment.TAG);
        isWrite = false;
    }

    @Override
    public void onDialogDisplayed() {
        observable.setChanged();
    }

    @Override
    public void onDialogDismissed() {
        observable.clearChanged();
        isWrite = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mNfcAdapter!= null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if(tag != null) {
            // OBSERVER
            observable.deleteObservers();
            observable.addObserver(createListener(Ndef.get(tag)));
            observable.notifyObservers(new PlainText(mEtMessage.getText().toString()));
        }
    }

    // FACTORY METHOD
    private NfcListener createListener(Ndef ndef) {
        NfcListener listener;

        if(isWrite)
            listener = new NfcWriter(ndef,
                    (nfcListener) -> {
                        Toast.makeText(this,"Successfully written" , Toast.LENGTH_SHORT).show();
                    },
                    (nfcListener) -> {
                        Toast.makeText(this,"Error writing" , Toast.LENGTH_SHORT).show();
                    }
            );
        else
            listener = new NfcReader(ndef, new PlainTextFactory(),
                    (nfcListener) -> {
                        Toast.makeText(this,"Successfully read " + ((NfcReader)nfcListener).getResult().toNfcFormat(), Toast.LENGTH_SHORT).show();
                    },
                    (nfcListener) -> {
                        Toast.makeText(this,"Error reading" , Toast.LENGTH_SHORT).show();
                    });

        return listener;
    }
}