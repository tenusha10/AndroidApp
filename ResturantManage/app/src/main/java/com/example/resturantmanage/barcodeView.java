package com.example.resturantmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class barcodeView extends AppCompatActivity {
    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID=1001;
    String voucher="1009786784037";
    String result;
    String Tablenumber;
    String InitialTotal;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case RequestCameraPermissionID:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent() != null) {
            Tablenumber = getIntent().getStringExtra("TableNo");
            InitialTotal = getIntent().getStringExtra("Total");
        }
        cameraPreview =(SurfaceView)findViewById(R.id.cameraPreview);
        txtResult=(TextView)findViewById(R.id.txtBarcodeRes);

        barcodeDetector= new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource= new CameraSource
                .Builder(this,barcodeDetector)
                .setRequestedPreviewSize(640,480)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(barcodeView.this, new String[] {Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes =detections.getDetectedItems();
                if(qrCodes.size() != 0){
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            txtResult.setText(qrCodes.valueAt(0).displayValue);
                            result =txtResult.getText().toString();
                            checkResults(result);
                        }
                    });
                }
            }

        });
        /*if(result != null){
            checkResults();
        } */
    }

    public void checkResults(String res){
        if(res.equals(voucher)){
            //Toast.makeText(barcodeView.this,"Voucher Accepted for 5% off bill",Toast.LENGTH_SHORT).show();
            Intent bill = new Intent(barcodeView.this,Bill.class);
            bill.putExtra("TableNo",Tablenumber);
            bill.putExtra("Total",InitialTotal);
            bill.putExtra("status","true");
            startActivity(bill);
        }
    }
}
