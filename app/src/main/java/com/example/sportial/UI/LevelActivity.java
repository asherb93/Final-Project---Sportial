package com.example.sportial.UI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sportial.R;

public class LevelActivity extends AppCompatActivity {
    String[] level = {"Beginner", "Intermediate", "Advanced"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterLevel;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterLevel = new ArrayAdapter<String>(this, R.layout.list_items, level);
        autoCompleteTextView.setAdapter(adapterLevel);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedLevel = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(LevelActivity.this,"Level"+level,Toast.LENGTH_SHORT).show();

                // Handle the selected level here

            }
        });
       //button
        Button upload_file=findViewById(R.id.uploadButton);
        upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission()){
                        filePicker();
                    }else{
                        requestPermission();
                    }
                    }else{
                        filePicker();
                    }

            }
        });

    }
    private void filePicker() {
        //now permission working
        Toast.makeText(LevelActivity.this,"File Picker call",Toast.LENGTH_SHORT).show();
        //let's pick  file
        Intent openGallery=new Intent(Intent.ACTION_PICK);
        openGallery.setType("image/*");
        startActivityForResult(openGallery,REQUEST_GALLERY);

    }
    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(LevelActivity.this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
           Toast.makeText(LevelActivity.this,"Please Give Permission To Upload File",Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(LevelActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }
    private boolean  checkPermission(){
        int result= ContextCompat.checkSelfPermission(LevelActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(LevelActivity.this,"Permission Successfull",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LevelActivity.this,"Permission Failed",Toast.LENGTH_SHORT).show();
                }

            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GALLERY&&resultCode== Activity.RESULT_OK){
            String filePath=getRealPathFromUri(data.getData(),LevelActivity.this);
            Log.d("File Path :",""+filePath);
        }
    }
    public String getRealPathFromUri(Uri uri, Activity activity){
        Cursor cursor=activity.getContentResolver().query(uri,null,null,null,null);
        if(cursor==null){
            return uri.getPath();
        }else{
            cursor.moveToFirst();
            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }
}
