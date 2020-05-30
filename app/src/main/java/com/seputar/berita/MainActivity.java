package com.seputar.berita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText edtTitle, edtDes, _judul;
    Button btnInsertIMG, btnS;
    ImageView img;
    public static final String upload_url="http://127.0.0.1/berita/select_foto.php";
    private Uri filePath;
    private Bitmap bitmap;
    private static final int Pick_image_result=1;
    private static final int code_permison_Storge=100;
    RequestQueue mRequest;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storagePermision();
        mRequest= Volley.newRequestQueue(getApplicationContext());
        pd=new ProgressDialog(MainActivity.this);
        edtTitle=(EditText) findViewById(R.id.insertTitle);
        edtDes=(EditText) findViewById(R.id.insertDes);
        btnInsertIMG= (Button) findViewById(R.id.btnInputImage);
        btnS=(Button) findViewById(R.id.btnSave);
        img=(ImageView) findViewById(R.id.ImageInput);
        _judul=findViewById(R.id.edtJudul);
        btnInsertIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });
    }
    private void UploadImage() {
        String judul1 = _judul.getText().toString();
        String nama = edtTitle.getText().toString();
        String email = edtDes.getText().toString();
        String path = getPath(filePath);
        try{
            String uploadid= UUID.randomUUID().toString();
            new MultipartUploadRequest(this,uploadid,upload_url)
                    .addFileToUpload(path,"image")
                    .addParameter("title",nama)
                    .addParameter("description",email)
                    .addParameter("name",judul1)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(3)
                    .startUpload();

        }catch(Exception e){
            Log.d("exeption", e.getMessage());
        }


    }
    private void showFileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "select image"),Pick_image_result);
    }

    private void storagePermision(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},code_permison_Storge);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode==code_permison_Storge){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "sukses granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "sukses denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Pick_image_result && data != null&& data.getData()!=null){
            filePath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                img.setImageBitmap(bitmap);
            }catch (Exception e){

            }
        }
    }
    private String getPath(Uri uri){
        Cursor c1 = getContentResolver().query(uri,null,null,null,null);
        c1.moveToFirst();
        String document_id=c1.getString(0);
        document_id=document_id.substring(document_id.lastIndexOf(":")+1);
        c1=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media._ID+"=?",new String[]{document_id},null);
        c1.moveToFirst();
        String path=c1.getString(c1.getColumnIndex(MediaStore.Images.Media.DATA));
        c1.close();
        return path;
    }

}
