package com.text.demon.scimyanmar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.*;
import java.util.*;
import javax.crypto.*;


import org.apache.commons.codec.binary.Base64;



import javax.crypto.spec.SecretKeySpec;

public class Main2Activity extends AppCompatActivity {

    Button btn_en,btn_de;
    private String pass;
    private ImageButton btn_copy,btn_paste,btn_share;
    private   EditText et_1,et_2;
    private   int k1,k2,k3,k4,k5,k6,k7,k8,k9;
    private   String s1,s2,s3,s4,s5,s6,s7,s8,s9;

    byte[] input;
    byte[] result;
    SecretKeySpec secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        getActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        btn_en = (Button) findViewById(R.id.encrypt);
        btn_de = (Button) findViewById(R.id.decrypt);
        btn_copy = (ImageButton) findViewById(R.id.copy);
        btn_paste = (ImageButton) findViewById(R.id.paste);
        btn_share = (ImageButton) findViewById(R.id.share);
        et_1 = (EditText) findViewById(R.id.et1);
        et_2 = (EditText) findViewById(R.id.et2);


        s1 = intent.getStringExtra("n1");
        s2 = intent.getStringExtra("n2");
        s3 = intent.getStringExtra("n3");
        s4 = intent.getStringExtra("n4");
        s5 = intent.getStringExtra("n5");
        s6 = intent.getStringExtra("n6");
        s7 = intent.getStringExtra("n7");
        s8 = intent.getStringExtra("n8");
        s9 = intent.getStringExtra("n9");



      //  et_1.setText(password);
btn_copy.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(et_2.getText());

        Toast.makeText(getApplicationContext(),"Copied your result",Toast.LENGTH_LONG).show();


    }
});
btn_paste.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String pasteData = "";
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

        // Gets the clipboard as text.
        pasteData = item.getText().toString();
        et_1.setText(pasteData);

    }
});
btn_share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int startSelection=et_2.getSelectionStart();
        int endSelection=et_2.getSelectionEnd();
        String selectedText = et_2.getText().toString().substring(startSelection, endSelection);

        //if no text is selected share the entire text area.
        if(selectedText.length() == 0){

            String dataToShare = et_2.getText().toString();
            selectedText = dataToShare;
        }

        //Share the text
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, selectedText);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);


    }
});
    }

    public void encryptClick(View v){
        pass = s1+s2+s3+s4+s5+s6+s7+s8+s9;
        if(pass.length()==0){
            Toast.makeText(getApplicationContext(),
                    "Please input password.",0).show();
            return;
        }
        createKey(pass);
        try
        {
            input = et_1.getText().toString().getBytes("UTF-8");
            result=encrypt(secretKey,input);
            et_2.setText(new String(result, "UTF-8"));
        }catch (Exception e){
            et_2.setText(e.toString());
        }



    }

    public void decryptClick(View v){
        pass = s1+s2+s3+s4+s5+s6+s7+s8+s9;
        if(pass.length()==0){
            Toast.makeText(getApplicationContext(),
                    "Please input password.",0).show();
            return;
        }
        createKey(pass);
        try
        {
            input = et_1.getText().toString().getBytes("UTF-8");
            result=decrypt(secretKey,input);
            et_2.setText(new String(result, "UTF-8"));
        }catch (Exception e){
            et_2.setText(e.toString());
        }

    }

    private void createKey(String pass){

        try{
            byte[] key;
            MessageDigest sha = null;
            key = pass.getBytes("UTF-8");
            System.out.println(key.length);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Is your key correct?",1).show();
        }
    }

    public static byte[] encrypt(SecretKey key, byte[] content) {
        Cipher cipher;
        byte[] encrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = Base64.encodeBase64(cipher.doFinal(content));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return encrypted;
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
}
