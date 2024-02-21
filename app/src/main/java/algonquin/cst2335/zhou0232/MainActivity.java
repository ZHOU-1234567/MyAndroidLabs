package algonquin.cst2335.zhou0232;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/** This activity shows a login page
 * @author Zoe Zhou
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /** This holdsext at the center of the screen*/
    private TextView tv = null;
    /** This holds the password entered by the user*/
    private EditText et = null;
    /** This holds the button to request log-in*/
    private Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();
            if (checkPasswordComplexity(password))
                tv.setText(R.string.your_password_meets_the_requirements);
            else
                tv.setText(R.string.you_shall_not_pass);
        });
    }

    /**
     * This function checks if this string has an Upper Case letter, a lower case letter, a number,
     * and a special symbol (#$%^&amp;*!@?)
     *
     * @param pw The String object that we are checking
     * @return Return true if the password is complex enough
     */
    boolean checkPasswordComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            if (Character.isUpperCase(pw.charAt(i)))
                foundUpperCase = true;
            else if (Character.isLowerCase(pw.charAt(i)))
                foundLowerCase = true;
            else if (Character.isDigit(pw.charAt(i)))
                foundNumber = true;
            else if (isSpecialCharacter(pw.charAt(i)))
                foundSpecial = true;
        }

        if (!foundUpperCase) {
// Say that they are missing an upper case letter;
            Toast.makeText(MainActivity.this, "The password you entered is missing an upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            // Say that they are missing a lower case letter;
            Toast.makeText(MainActivity.this, "The password you entered is missing a lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            // Say that they are missing a number;
            Toast.makeText(MainActivity.this, "The password you entered is missing a number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            // Say that they are missing a special character;
            Toast.makeText(MainActivity.this, "The password you entered is missing a special character", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true; //only get here if they're all true
    }

    /**
     * This function checks if character is a special character
     *
     * @param c The character that we are checking
     * @return true if c is one of: #$%^&amp;*!@? and false otherwise
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}