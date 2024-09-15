package debugger.app.shclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {




    private Sender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_activity);

        Button bMenu = (Button) findViewById(R.id.bMenu);
        Button bAddUser = (Button) findViewById(R.id.bAddUser);



        bMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        bAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.add_user_activity, null);

                Spinner spinRole = customLayout.findViewById(R.id.spUserRole);
                String[] userRoles = getResources().getStringArray(R.array.userRoles);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingsActivity.this,
                    R.layout.spinner_item, userRoles);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinRole.setAdapter(adapter);


                builder.setView(customLayout);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    EditText tName = customLayout.findViewById(R.id.tNameNewUser);
                    EditText tPass = customLayout.findViewById(R.id.tPassNewUser);
                    String name = tName.getText().toString();
                    String pass = tPass.getText().toString();
                    String role = spinRole.getSelectedItem().toString();


                    AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingsActivity.this);
                    final View customLayout2 = getLayoutInflater().inflate(R.layout.show_qr_activity, null);

                    builder2.setView(customLayout2);

                    ImageView imageView = customLayout2.findViewById(R.id.imQrUser);

                    String content = name+"/"+pass+"/"+role;

                    Bitmap qrCodeBitmap = generateQRCode(name+"/"+pass+"/"+role);
                    imageView.setImageBitmap(qrCodeBitmap);

                    builder2.setPositiveButton("OK", (alertDialog, which2) -> alertDialog.dismiss());

                    AlertDialog alertDialog = builder2.create();
                    alertDialog.show();
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }


    public Sender getSender() {
        return sender;
    }

    private Bitmap generateQRCode(String content) {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

}