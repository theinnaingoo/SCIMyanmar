package com.text.demon.scimyanmar;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.binary.*;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Arrays;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class ImageEncrypt extends AppCompatActivity
{

    private ImageView iv,iv_source;
    private   EditText et;
    private String s1,s2,s3,s4,s5,s6,s7,s8,s9;

    private String sClickedRadioButton;

    private Button btn_en;
    TextView tv;

    Bitmap bmp;
    byte[] content;
    byte[] encrypted;
    byte[] decrypted;
    byte[] resultByte;

    Drawable drawable;




    boolean encryptMode=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_encrypt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //receive key from main activity
        Intent intent = getIntent();
        s1 = intent.getStringExtra("n1");
        s2  = intent.getStringExtra("n2");
        s3  = intent.getStringExtra("n3");
        s4  = intent.getStringExtra("n4");
        s5  = intent.getStringExtra("n5");
        s6  = intent.getStringExtra("n6");
        s7  = intent.getStringExtra("n7");
        s8  = intent.getStringExtra("n8");
        s9  = intent.getStringExtra("n9");


        iv = (ImageView)findViewById(R.id.iv);
        btn_en=(Button)findViewById(R.id.btn_en);


        tv=(TextView)findViewById(R.id.tv);



        iv_source = (ImageView)findViewById(R.id.source);
        iv_source.setImageResource(R.drawable.add_image);

        //to pick image from my device and set on image view
        iv_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in,"Select Picture"),PICK_IMAGE);

            }
        });

        btn_en.setEnabled(true);


    }



    //onClick for 3 butoons

    public void onClick(View v)
    {

        // to encrypt image this coding from sir NNL
        bmp = ((BitmapDrawable)iv_source.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        content = stream.toByteArray();
        try{
            SecretKeySpec secretKey;
            byte[] key;
            String myKey = s1+s2+s3+s4+s5+s6+s7+s8+s9;
            MessageDigest sha = null;
            key = myKey.getBytes("UTF-8");
            System.out.println(key.length);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
            encrypted = encrypt(secretKey, content);
            decrypted = decrypt(secretKey, encrypted);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),1).show();
        }

        //for encrypt btn
        Bitmap bmp2;
        String result = "";



        if (v.getId()==R.id.btn_en)
        {

            if(sClickedRadioButton == null){

                Toast.makeText(getApplicationContext(),"Please select one option",Toast.LENGTH_SHORT).show();

            }else if(sClickedRadioButton.equals("encrypt")){

                bmp = ((BitmapDrawable)iv_source.getDrawable()).getBitmap();


                bmp= BitmapFactory.decodeByteArray(encrypted, 0, encrypted.length);
                encryptMode=false;


                iv.setBackgroundColor(Color.WHITE);

                result ="Encryption Done";
                writeToFile(encrypted);
                Toast.makeText(getApplicationContext(),"Encrption finished and saved encrypted file in your device",Toast.LENGTH_LONG).show();



            }else if(sClickedRadioButton.equals("decrypt")) {


             File file = new File(Environment.getExternalStorageDirectory(),"encryptdata.dat");
             byte [] bytes = getByte(file.getAbsolutePath());
                try{
                    SecretKeySpec secretKey;
                    byte[] key;
                    String myKey = s1+s2+s3+s4+s5+s6+s7+s8+s9;
                    MessageDigest sha = null;
                    key = myKey.getBytes("UTF-8");
                    System.out.println(key.length);
                    sha = MessageDigest.getInstance("SHA-1");
                    key = sha.digest(key);
                    key = Arrays.copyOf(key, 16);
                    secretKey = new SecretKeySpec(key, "AES");
                    resultByte = decrypt(secretKey, bytes);

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),1).show();
                }
                try {
                    bmp= BitmapFactory.decodeByteArray(resultByte, 0, resultByte.length);
                    iv.setBackgroundColor(Color.TRANSPARENT);
                    iv.setImageBitmap(bmp);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Your key was incorrect or something was wrong",1).show();

                }


            }



//

        }



        else if(v.getId()==R.id.btn_save){
            iv_source.buildDrawingCache();
            Bitmap bm=iv_source.getDrawingCache();

            OutputStream fOut = null;
            Uri outputFileUri;
            try {
                File root = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "Decrypted Result" + File.separator);
                root.mkdirs();
                File sdImageMainDirectory = new File(root, "decrypted.jpg");
                outputFileUri = Uri.fromFile(sdImageMainDirectory);
                fOut = new FileOutputStream(sdImageMainDirectory);
                Toast.makeText(this, "Saved your decrypted file at"+root+"as"+sdImageMainDirectory,
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error occured. Please try again later.",
                        Toast.LENGTH_SHORT).show();
            }
            try {
                bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
            }



        }
        tv.setText(result);

    }

    public void writeToFile(byte[] encrypted){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"encryptdata.dat");
            FileOutputStream fos = new FileOutputStream(file.getPath());
            fos.write(encrypted);
            fos.close();
        }
        catch (Exception e){


        }


    }


    public static byte[] encrypt(SecretKey key, byte[] content) {
        Cipher cipher;
        byte[] encrypted = null;
        try {
            //String abc = Base64.getDecoder().decode(encrypted);
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = Base64.encodeBase64(cipher.doFinal(content));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return encrypted;
    }

private byte[] getByte(String path){
        byte[] bytes = {};
        try {
            File file = new File(path);
            bytes = new byte[(int)file.length()];
            InputStream is = new FileInputStream(file);
            is.read(bytes);
            is.close();
        } catch (FileNotFoundException e){

            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bytes;


}



    public static byte[] decrypt(SecretKey key, byte[] content) {
        Cipher cipher;
        byte[] decrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(Base64.decodeBase64(content));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return decrypted;
    }
    public  static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PICK_IMAGE){

            Uri result = data.getData();
//         imagePath.get
//         Bitmap bm = BitmapFactory.decodeFile(imagePath);
            iv_source.setImageURI(result);

        }


        // super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdo_en:
                if (checked)
                    sClickedRadioButton = "encrypt";
                break;
            case R.id.rdo_de:
                if (checked)
                    sClickedRadioButton = "decrypt";
                break;
        }
    }



}
