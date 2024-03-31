package com.example.vetvisor;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NFC extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private boolean isWriteModeEnabled = false;
    private NdefMessage ndefMessageToWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Create PendingIntent for NFC intent
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);

        // Create IntentFilter array to handle NDEF and TECH discovered intents
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefIntent.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{ndefIntent};
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Log.e("NFC", "Error creating IntentFilter", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            // Enable foreground dispatch to receive NFC intents
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            // Disable foreground dispatch when the activity is paused
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle NFC intents
        handleNfcIntent(intent);
    }

    private void handleNfcIntent(Intent intent) {
        if (!isWriteModeEnabled) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                // Read the content of the NFC tag
                String tagContent = readNfcTag(tag);
                if (tagContent != null) {
                    // Display the content of the NFC tag
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("NFC Tag Content");
                    alertDialogBuilder.setMessage("Tag Content: " + tagContent);
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", (dialog, id) -> {
                        // Dismiss the dialog
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    // Handle case where tag content is null or empty
                    Toast.makeText(this, "Empty or unsupported NFC tag.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    public void onWriteButtonClick(View view) {
        // Set flag to indicate write mode is enabled
        isWriteModeEnabled = true;

        // Create an EditText view to take input for website link
        final EditText editText = new EditText(this);

        // Show a dialog to input the website link
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Write to NFC Tag");
        alertDialogBuilder.setMessage("Enter website link:");
        alertDialogBuilder.setView(editText);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", (dialog, id) -> {
            String websiteLink = editText.getText().toString().trim();
            if (!websiteLink.isEmpty()) {
                // Convert website link to NDEF message
                ndefMessageToWrite = createNdefMessage(websiteLink);
                if (ndefMessageToWrite != null) {
                    // Write NDEF message to NFC tag
                    writeNdefMessageToTag(ndefMessageToWrite);
                    Toast.makeText(getApplicationContext(), "Scan NFC tag to write...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to create NDEF message.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Website link cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", (dialog, id) -> {
            dialog.cancel();
            // Reset write mode flag and NDEF message to null
            isWriteModeEnabled = false;
            ndefMessageToWrite = null;
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onReadButtonClick(View view) {
        // Set flag to indicate write mode is disabled
        isWriteModeEnabled = false;

        // Show a dialog asking the user to scan an NFC tag
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Read from NFC Tag");
        alertDialogBuilder.setMessage("Scan an NFC tag to read its content.");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Continue waiting for NFC tag scan
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Method to create an NDEF message from website link
    private NdefMessage createNdefMessage(String websiteLink) {
        if (websiteLink == null || websiteLink.isEmpty()) {
            return null;
        }
        NdefRecord record = NdefRecord.createUri(websiteLink);
        return new NdefMessage(record);
    }

    // Method to write NDEF message to NFC tag
    private void writeNdefMessageToTag(NdefMessage ndefMessage) {
        Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            Ndef ndefTag = Ndef.get(tag);
            if (ndefTag != null) {
                try {
                    ndefTag.connect();
                    ndefTag.writeNdefMessage(ndefMessage);
                    Toast.makeText(this, "NFC tag write successful.", Toast.LENGTH_SHORT).show();
                } catch (IOException | FormatException e) {
                    Log.e("NFC", "Error writing NDEF message to NFC tag", e);
                    Toast.makeText(this, "Failed to write NFC tag.", Toast.LENGTH_SHORT).show();
                } finally {
                    try {
                        ndefTag.close();
                    } catch (IOException e) {
                        Log.e("NFC", "Error closing NDEF connection", e);
                    }
                }
            } else {
                NdefFormatable ndefFormatable = NdefFormatable.get(tag);
                if (ndefFormatable != null) {
                    try {
                        ndefFormatable.connect();
                        ndefFormatable.format(ndefMessage);
                        Toast.makeText(this, "NFC tag write successful.", Toast.LENGTH_SHORT).show();
                    } catch (IOException | FormatException e) {
                        Log.e("NFC", "Error formatting NFC tag as NDEF", e);
                        Toast.makeText(this, "Failed to write NFC tag.", Toast.LENGTH_SHORT).show();
                    } finally {
                        try {
                            ndefFormatable.close();
                        } catch (IOException e) {
                            Log.e("NFC", "Error closing NDEF formatable connection", e);
                        }
                    }
                }
            }
        }
        // Reset write mode flag and NDEF message to null
        isWriteModeEnabled = false;
        ndefMessageToWrite = null;
    }


    // Method to read NFC tag content
    private String readNfcTag(Tag nfcTag) {
        // Implement your NFC reading logic here
        String tagContent = null;
        try {
            // Get the NDEF messages from the tag
            Ndef ndef = Ndef.get(nfcTag);
            if (ndef != null) {
                ndef.connect();
                NdefMessage ndefMessage = ndef.getNdefMessage();
                if (ndefMessage != null && ndefMessage.getRecords().length > 0) {
                    // Assume only one record for simplicity
                    NdefRecord record = ndefMessage.getRecords()[0];
                    // Extract the payload (content) from the record
                    if (record != null) {
                        // Convert the payload bytes to string
                        tagContent = new String(record.getPayload(), StandardCharsets.UTF_8);
                    }
                }
                ndef.close();
            }
        } catch (IOException e) {
            // Log the error
            Log.e("NFC", "Error reading NFC tag", e);
        } catch (FormatException e) {
            // Log the error
            Log.e("NFC", "Error formatting NFC tag", e);
            throw new RuntimeException(e);
        }
        return tagContent;
    }
}
