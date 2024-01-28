package algonquin.cst2335.zhou0232.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import algonquin.cst2335.zhou0232.data.MainViewModel;
import algonquin.cst2335.zhou0232.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);
        model.editString.observe(this, s -> {variableBinding.textview.setText("Your edit text has: " + model.editString.getValue().toString());});
        model.isSelected.observe(this, selected -> {
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
            CharSequence text = "The value is now: " + selected.toString();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        });

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.mybuttons.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        variableBinding.checkBox.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        variableBinding.radioButton.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        variableBinding.switch1.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        variableBinding.myimagebutton.setOnClickListener(click -> {
            model.height.postValue(variableBinding.myimagebutton.getHeight());
            model.width.postValue(variableBinding.myimagebutton.getWidth());
            CharSequence text = "The width = " + variableBinding.myimagebutton.getWidth() + " and height = " + variableBinding.myimagebutton.getHeight();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        });
    }
}