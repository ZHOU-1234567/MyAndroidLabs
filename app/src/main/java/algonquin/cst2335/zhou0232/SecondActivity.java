package algonquin.cst2335.zhou0232;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra(getString(R.string.intent_email_variable_name));
        TextView welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        welcomeTextView.setText(String.format(getString(R.string.welcome_message_template), emailAddress));

        SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_filename), Context.MODE_PRIVATE);
        EditText editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPhone.setText(prefs.getString(getString(R.string.phone_number_variable_name), ""));

        Button callNumberButton = (Button) findViewById(R.id.callNumberButton);
        callNumberButton.setOnClickListener(clk -> {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1000);
            } else {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse(String.format(getString(R.string.call_uri_format), editTextPhone.getText().toString())));
                startActivity(call);
            }
        });

        ImageView profileImage = (ImageView) findViewById(R.id.profileImageView);
        File file = new File(getFilesDir(), getString(R.string.bitmap_filename));
        if (file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getPath());
            profileImage.setImageBitmap(theImage);
        }

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        profileImage.setImageBitmap(thumbnail);

                        FileOutputStream fOut = null;
                        try {
                            fOut = openFileOutput(getString(R.string.bitmap_filename), Context.MODE_PRIVATE);
                            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


        Button changePictureButton = (Button) findViewById(R.id.changePictureButton);
        changePictureButton.setOnClickListener(clk -> {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraResult.launch(cameraIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        String phoneNumber = editTextPhone.getText().toString();
        SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.phone_number_variable_name), phoneNumber);
        editor.apply();
    }
}