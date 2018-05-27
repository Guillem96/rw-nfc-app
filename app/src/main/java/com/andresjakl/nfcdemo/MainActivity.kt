package com.andresjakl.nfcdemo

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.nfc.tech.NfcF
import android.content.IntentFilter
import android.app.PendingIntent
import android.os.PatternMatcher
import android.text.Html
import kotlinx.android.synthetic.main.activity_main.*
import android.text.Spanned

class MainActivity : AppCompatActivity() {

    private val KEY_LOG_TEXT = "logText"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Restore saved text if available
        if (savedInstanceState != null) {
            tv_messages.text = savedInstanceState.getCharSequence(KEY_LOG_TEXT)
        }

        logMessage("Welcome", "App started")

        // TODO Check for NFC Support
        var nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        logMessage("NFC supported", (nfcAdapter != null).toString())
        logMessage("NFC enabled", (nfcAdapter?.isEnabled).toString())

        // TODO Handle intent

        scrollDown()
    }

    // TODO Override onNewIntent
    private fun processIntent(checkIntent: Intent) {
        // Check if intent has the action of a discovered NFC tag
        // with NDEF formatted contents
        if (checkIntent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {

            // Retrieve the raw NDEF message from the tag
            val rawMessages = checkIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

            // ...

        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            processIntent(intent)
        }
    }
    // TODO Create function processIntent(checkIntent: Intent)


    // --------------------------------------------------------------------------------
    // Utility functions

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putCharSequence(KEY_LOG_TEXT, tv_messages.text)
        super.onSaveInstanceState(outState)
    }

    private fun logMessage(header: String, text: String?) {
        tv_messages.append(if (text.isNullOrBlank()) fromHtml("<b>$header</b><br>") else fromHtml("<b>$header</b>: $text<br>"))
        scrollDown()
    }

    private fun fromHtml(html: String): Spanned {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(html)
        }
    }

    private fun scrollDown() {
        sv_messages.post({ sv_messages.smoothScrollTo(0, sv_messages.bottom) })
    }
}
