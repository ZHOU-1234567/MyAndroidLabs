package algonquin.cst2335.zhou0232;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w(getString(R.string.TAG), getString(R.string.on_create_message));
        SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_filename), Context.MODE_PRIVATE);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        emailEditText.setText(prefs.getString(getString(R.string.login_variable_name), ""));

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(clk -> {
            String emailAddress = emailEditText.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getString(R.string.login_variable_name), emailAddress);
            editor.apply();

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra(getString(R.string.intent_email_variable_name), emailAddress);
            startActivity(nextPage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(getString(R.string.TAG), getString(R.string.on_start_message));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(getString(R.string.TAG), getString(R.string.on_resume_message));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(getString(R.string.TAG), getString(R.string.on_pause_message));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(getString(R.string.TAG), getString(R.string.on_stop_message));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(getString(R.string.TAG), getString(R.string.on_destroy_message));
    }
}