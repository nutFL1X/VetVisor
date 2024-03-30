package com.example.vetvisor;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.nio.charset.StandardCharsets;

public class NFC extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10.0f; // 10 meters

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available on this device.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, NFC.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isLocationEnabled()) {
            showLocationSettingsDialog();
        } else {
            requestLocationUpdates();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            handleNfcIntent(intent);
        }
    }

    private void handleNfcIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                try {
                    ndef.connect();
                    NdefMessage ndefMessage = ndef.getNdefMessage();
                    if (ndefMessage != null) {
                        String message = new String(ndefMessage.getRecords()[0].getPayload(), StandardCharsets.UTF_8);
                        Toast.makeText(this, "Read from NFC tag: " + message, Toast.LENGTH_SHORT).show();
                    }
                    ndef.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error reading from NFC tag.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void writeLocationToNfcTag() {
        try {
            Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                Ndef ndef = Ndef.get(tag);
                if (ndef != null) {
                    Location location = getLastKnownLocation();
                    if (location != null) {
                        String latitude = String.valueOf(location.getLatitude());
                        String longitude = String.valueOf(location.getLongitude());
                        String locationData = "Latitude: " + latitude + ", Longitude: " + longitude;

                        // Create NDEF message with a custom MIME type
                        NdefRecord mimeRecord = NdefRecord.createMime("text/plain", locationData.getBytes(StandardCharsets.UTF_8));
                        NdefMessage ndefMessage = new NdefMessage(mimeRecord);

                        ndef.connect();
                        ndef.writeNdefMessage(ndefMessage);
                        Toast.makeText(this, "Location written to NFC tag.", Toast.LENGTH_SHORT).show();
                        ndef.close();
                    } else {
                        Toast.makeText(this, "Unable to retrieve location.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Tag is not NDEF formatted.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No tag detected.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error writing to NFC tag.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onReadButtonClick(View view) {
        // Read from NFC tag functionality
        Toast.makeText(this, "Read from NFC tag button clicked.", Toast.LENGTH_SHORT).show();
    }

    public void onWriteButtonClick(View view) {
        // Write to NFC tag functionality
        writeLocationToNfcTag();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
    }

    private Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            // Do nothing here
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            // Do nothing here
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            // Do nothing here
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Do nothing here
        }
    };

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showLocationSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Location services are disabled. Please enable location services to use this feature.")
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
