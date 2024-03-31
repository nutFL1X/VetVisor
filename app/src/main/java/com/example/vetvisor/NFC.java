package com.example.vetvisor;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import android.os.Parcelable;
import android.os.Vibrator;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NFC extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private boolean tagReadSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Create PendingIntent for NFC intent
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);

        // Create IntentFilter array to handle NDEF and TECH discovered intents
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefIntent.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{ndefIntent};
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
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
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            // NFC tag is discovered
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                // Read NFC tag content
                String tagContent = readNfcTag(tag);
                if (tagContent != null) {
                    // NFC tag read successful
                    tagReadSuccess = true;
                    // Process tag content (you can do whatever you want with the tag content here)
                    Toast.makeText(this, "NFC tag content: " + tagContent, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onWriteButtonClick(View view) {
        // Show a box to wait for the device to read the NFC tag
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Write to NFC Tag");
        alertDialogBuilder.setMessage("Waiting for device to read NFC tag...");
        alertDialogBuilder.setCancelable(false);
        final AlertDialog waitingDialog = alertDialogBuilder.create();
        waitingDialog.show();

        // Set a flag to false indicating tag not read yet
        tagReadSuccess = false;

        // Simulate reading NFC tag with a timeout
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!tagReadSuccess) {
                    // Display timeout message if tag is not read within 5 seconds
                    Toast.makeText(getApplicationContext(), "Timeout: NFC tag not read.", Toast.LENGTH_SHORT).show();
                    waitingDialog.dismiss();
                } else {
                    // Proceed with writing NDEF message to NFC tag
                    // Create an EditText view to take input for website link
                    final EditText editText = new EditText(NFC.this);
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

                    // Show a dialog to input the website link
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NFC.this);
                    alertDialogBuilder.setTitle("Write to NFC Tag");
                    alertDialogBuilder.setMessage("Enter website link:");
                    alertDialogBuilder.setView(editText);
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final String websiteLink = editText.getText().toString().trim();
                            if (!websiteLink.isEmpty()) {
                                // Convert website link to NDEF message
                                NdefMessage ndefMessage = createNdefMessage(websiteLink);
                                if (ndefMessage != null) {
                                    // Write NDEF message to NFC tag
                                    boolean writeSuccess = writeNdefMessageToTag(ndefMessage, waitingDialog);
                                    if (!writeSuccess) {
                                        // Writing failed
                                        Toast.makeText(getApplicationContext(), "Failed to write NFC tag.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Failed to create NDEF message
                                    Toast.makeText(getApplicationContext(), "Failed to create NDEF message.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Website link cannot be empty.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    // Dismiss waiting dialog
                    waitingDialog.dismiss();
                }
            }
        }, 5000); // 5 seconds delay
    }


    public void onReadButtonClick(View view) {
        // Show a box to wait for the device to read the NFC tag
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Read from NFC Tag");
        alertDialogBuilder.setMessage("Waiting for device to read NFC tag...");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        // Set a flag to false indicating tag not read yet
        tagReadSuccess = false;

        // Simulate reading NFC tag with a timeout
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!tagReadSuccess) {
                    // Display timeout message if tag is not read within 5 seconds
                    Toast.makeText(getApplicationContext(), "Timeout: NFC tag not read.", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        }, 5000); // 5 seconds delay
    }


    // Method to create an NDEF message from website link
    private NdefMessage createNdefMessage(String websiteLink) {
        // Create an NDEF record for the website link
        NdefRecord record = NdefRecord.createUri(websiteLink);
        // Create an NDEF message with the record
        return new NdefMessage(record);
    }

    // Method to write NDEF message to NFC tag
    private boolean writeNdefMessageToTag(NdefMessage ndefMessage, AlertDialog waitingDialog) {
        Tag nfcTag = null;
        Ndef ndefTag = null; // Rename the variable

        try {
            // Get the tag object
            nfcTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (nfcTag != null) {
                // Write NDEF message to the tag
                ndefTag = Ndef.get(nfcTag); // Assign the Ndef object to ndefTag
                if (ndefTag != null) {
                    ndefTag.connect();
                    ndefTag.writeNdefMessage(ndefMessage);
                    waitingDialog.dismiss();
                    Toast.makeText(this, "NFC tag write successful.", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    // Format the tag as NDEF if it's NDEF formatable
                    NdefFormatable ndefFormatable = NdefFormatable.get(nfcTag);
                    if (ndefFormatable != null) {
                        ndefFormatable.connect();
                        ndefFormatable.format(ndefMessage);
                        waitingDialog.dismiss();
                        Toast.makeText(this, "NFC tag write successful.", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        // NFC tag does not support NDEF
                        return false;
                    }
                }
            }
        } catch (IOException | NullPointerException | IllegalStateException | FormatException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            if (ndefTag != null) { // Change to ndefTag
                try {
                    ndefTag.close(); // Close the NFC tag connection
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
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
        } catch (IOException | NullPointerException | IllegalStateException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            throw new RuntimeException(e);
        }
        return tagContent;
    }
}

