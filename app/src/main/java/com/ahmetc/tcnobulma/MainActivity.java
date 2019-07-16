package com.ahmetc.tcnobulma;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button goster;
    private EditText scan_tc;
    private Dialog dialog;
    private int onuncu_rakam,onbirinci_rakam, tc_no;
    private String sonuc, s_tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        goster = findViewById(R.id.goster);
        scan_tc = findViewById(R.id.scan_tc);


        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_tc = (scan_tc.getText().toString().isEmpty())?"0":scan_tc.getText().toString();
                tc_no = Integer.parseInt(s_tc);
                if(s_tc.length() != 9) {
                    // 9 haneli bir sayı girmelisiniz.
                    Toast.makeText(MainActivity.this, "Girilen numara 9 haneli olmalıdır", Toast.LENGTH_SHORT).show();
                }
                else {

                        Goster();
                }
                scan_tc.setText("");
            }
        });
    }
    private void hesapla(int tc_no) {
        int temp=tc_no,i=1,tekler=0,ciftler=0;
        while(temp > 0)
        {
            if(i%2 == 1) tekler += temp%10;
            else ciftler += temp%10;
            temp /= 10;
            i++;
        }
        onuncu_rakam = (tekler * 7 + ciftler * 9) % 10;
        onbirinci_rakam = (tekler + ciftler + onuncu_rakam) % 10;
    }
    private void Goster() {
        hesapla(tc_no);
        sonuc = (s_tc+String.valueOf(onuncu_rakam)+String.valueOf(onbirinci_rakam));

        /* ************** POPUP *************************************/

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_popup);

        TextView text_sonuc = dialog.findViewById(R.id.text_sonuc);
        ImageView exit = dialog.findViewById(R.id.exit);
        Button again = dialog.findViewById(R.id.again);

        text_sonuc.setText(sonuc);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onPause() {
        super.onPause();
        scan_tc.setText("");
        if(dialog != null)dialog.dismiss();
    }
}
