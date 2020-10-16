package com.example.pam;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    int notifyId = 0;
    String imageUri;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateNotificationChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel("PAM", "PAM", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("PAM1");
        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
    }

    public void SendNotification(View view) {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "PAM").setContentTitle("AppName").setContentText("TestText").setSmallIcon(R.mipmap.pril).setPriority(NotificationCompat.PRIORITY_HIGH);
                NotificationManagerCompat.from(MainActivity.this).notify(notifyId, builder.build());
                notifyId++;
            }
        }, 2 * 1000);
    }

    public void searchInfo(View view) {
        EditText editText = findViewById(R.id.editTextTextPersonName);
        String searchValue = editText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + searchValue));
        startActivity(intent);
    }

    public void openCamera(View view) throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File image = File.createTempFile(String.valueOf(System.currentTimeMillis()),".jpg",getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        imageUri=image.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,"com.example.android.fileprovider",image));
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent(this,PhotoActivity.class);
                    intent.putExtra(PhotoActivity.IMAGE_URI,imageUri);
                    startActivity(intent);
                }
        }
    }
}