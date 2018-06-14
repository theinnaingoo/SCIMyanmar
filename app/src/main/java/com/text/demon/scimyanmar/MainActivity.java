package com.text.demon.scimyanmar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btn_capture,btn_clear;

    private EditText et1,et2,et3,et4,et5,et6,et7,et8,et9;
    private String sClickedRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},00);


        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        et3 = (EditText)findViewById(R.id.et3);
        et4 = (EditText)findViewById(R.id.et4);
        et5 = (EditText)findViewById(R.id.et5);
        et6 = (EditText)findViewById(R.id.et6);
        et7 = (EditText)findViewById(R.id.et7);
        et8 = (EditText)findViewById(R.id.et8);
        et9 = (EditText)findViewById(R.id.et9);


        btn_capture = (Button)findViewById(R.id.capture);
        btn_clear = (Button)findViewById(R.id.clear);

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureScreen();  // capture method


            }
        });


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et5.setText("");
                et6.setText("");
                et7.setText("");
                et8.setText("");
                et9.setText("");

                Toast.makeText(getApplicationContext(),"Cleared all text",Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                Intent in = new Intent(getApplicationContext(),About.class);
                startActivity(in);
                return true;
            case R.id.version:
                Toast.makeText(getApplicationContext(),"This is a version 1, next version will avilable later!",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    //  on Click method for next btn
    public void onClick(View v){
        if(sClickedRadioButton == null){
            Toast.makeText(getApplicationContext(),"Please select one option",Toast.LENGTH_SHORT).show();

        }else if(sClickedRadioButton.equals("text")){


            String num1 = et1.getText().toString().trim();
            String num2 = et2.getText().toString().trim();
            String num3 = et3.getText().toString().trim();
            String num4 = et4.getText().toString().trim();
            String num5 = et5.getText().toString().trim();
            String num6 = et6.getText().toString().trim();
            String num7 = et7.getText().toString().trim();
            String num8 = et8.getText().toString().trim();
            String num9 = et9.getText().toString().trim();
if (num1.equals("") && num2.equals("") && num3.equals("") && num4.equals("")&& num5.equals("") &&
        num6.equals("") && num7.equals("") && num8.equals("") && num9.equals("") ){
 Toast.makeText(getApplicationContext(),"Please enter keys",Toast.LENGTH_SHORT).show();

            }

else {
    //transfer activity with 9keys to main2Activity
    Intent i = new Intent(getApplicationContext(), Main2Activity.class);
    i.putExtra("n1", num1);
    i.putExtra("n2", num2);
    i.putExtra("n3", num3);
    i.putExtra("n4", num4);
    i.putExtra("n5", num5);
    i.putExtra("n6", num6);
    i.putExtra("n7", num7);
    i.putExtra("n8", num8);
    i.putExtra("n9", num9);

    startActivity(i);

}

        }else if(sClickedRadioButton.equals("image")) {


            // Do something when contractor is
            // Toast.makeText(getApplicationContext(), "work 2", Toast.LENGTH_SHORT).show();
            String num1 = et1.getText().toString().trim();
            String num2 = et2.getText().toString().trim();
            String num3 = et3.getText().toString().trim();
            String num4 = et4.getText().toString().trim();
            String num5 = et5.getText().toString().trim();
            String num6 = et6.getText().toString().trim();
            String num7 = et7.getText().toString().trim();
            String num8 = et8.getText().toString().trim();
            String num9 = et9.getText().toString().trim();

            if (num1.equals("") && num2.equals("") && num3.equals("") && num4.equals("")&& num5.equals("") &&
                    num6.equals("") && num7.equals("") && num8.equals("") && num9.equals("") ){
                Toast.makeText(getApplicationContext(),"Please enter keys",Toast.LENGTH_SHORT).show();

            } else {
                //transfer activity with 9keys to main2Activity
                Intent i = new Intent(getApplicationContext(), ImageEncrypt.class);
                i.putExtra("n1", num1);
                i.putExtra("n2", num2);
                i.putExtra("n3", num3);
                i.putExtra("n4", num4);
                i.putExtra("n5", num5);
                i.putExtra("n6", num6);
                i.putExtra("n7", num7);
                i.putExtra("n8", num8);
                i.putExtra("n9", num9);

                startActivity(i);
            }
        }

    }





    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdo_text:
                if (checked)
                    sClickedRadioButton = "text";
                break;
            case R.id.rdo_image:
                if (checked)
                    sClickedRadioButton = "image";
                break;
        }
    }


    //to take a screen shot
    private void captureScreen() {
        View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"SCREENShot"+System.currentTimeMillis()+".png");
            FileOutputStream fos = new FileOutputStream(file.getPath());

            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getApplicationContext(),"Saved screen shot in internal storage"+file.getPath(),Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
