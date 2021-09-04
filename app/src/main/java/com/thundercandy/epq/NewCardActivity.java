package com.thundercandy.epq;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.thundercandy.epq.data.Card;
import com.thundercandy.epq.database.DbUtils;
import com.thundercandy.epq.events.CardAddedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Locale;

public class NewCardActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView txtCategoryName;
    private EditText txtTerm, txtDefinition;
    private Button btnFinish;

    private GestureDetector gDetector;
    private EditText selectedTextField;

    private SpeechRecognizer speechRecognizer;
    private Intent defaultSpeechIntent;

    private static int targetCategoryID = -1;
    private static int targetCategoryPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        checkVoiceCommandPermission();

        overridePendingTransition(R.anim.forward_slide_in, R.anim.forward_slide_out);

        btnBack = findViewById(R.id.btnBack);
        txtCategoryName = findViewById(R.id.txtCategoryName);
        txtTerm = findViewById(R.id.txtTerm);
        txtDefinition = findViewById(R.id.txtDefinition);
        btnFinish = findViewById(R.id.btnFinish);

        Intent intent = getIntent();
        targetCategoryID = intent.getIntExtra("targetCategoryID", -1);
        targetCategoryPosition = intent.getIntExtra("targetCategoryPosition", 0);
        txtCategoryName.setText(DbUtils.getCategoryNameByID(this, targetCategoryID));

        btnBack.setOnClickListener(v -> {
            //TODO: Check if user has entered anything into text fields before taking user to previous screen
            onBackPressed();
        });

        btnFinish.setOnClickListener(v -> {
            String inputTerm = txtTerm.getText().toString();
            String inputDefinition = txtDefinition.getText().toString();

            if (inputTerm.length() > 0 && inputDefinition.length() > 0 && targetCategoryID != -1) {

                //TODO: Make sure to pass an actual date instead of 0
                int card_id = DbUtils.addCard(this, targetCategoryID, inputTerm, inputDefinition, 0);
                Log.d("DB_ADD", "ID: " + card_id + ", Term: " + inputTerm + " added to DB");

                CardAddedEvent event = new CardAddedEvent();
                event.setCategory(targetCategoryID);
                event.setAddedCard(new Card(card_id, inputTerm, inputDefinition));
                event.setTargetPosition(targetCategoryPosition);
                Log.d("DB_ADD", "Event Pre-fire");
                EventBus.getDefault().post(event);
                Log.d("DB_ADD", "Event Fired");

                finish();
            }
        });

        // Creates the SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        // Sets up the default speech intent
        defaultSpeechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        defaultSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        defaultSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        gDetector = new GestureDetector(getBaseContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                setTextFields_OFF();
                speechRecognizer.destroy();
                createRecognitionListener();
                speechRecognizer.startListening(defaultSpeechIntent);
                return true;
            }
        });

        txtTerm.setOnTouchListener((v, event) -> {
            selectedTextField = txtTerm;
            gDetector.onTouchEvent(event);
            txtTerm.clearFocus();
            return false;
        });
        txtDefinition.setOnTouchListener((v, event) -> {
            selectedTextField = txtDefinition;
            gDetector.onTouchEvent(event);
            txtDefinition.clearFocus();
            return false;
        });
        txtTerm.setOnFocusChangeListener((v, hasFocus) -> {
            if (txtTerm.getText().length() != 0 || hasFocus) {
                txtTerm.setGravity(Gravity.CENTER_HORIZONTAL);
                txtTerm.setHint("");
            } else {
                txtTerm.setGravity(Gravity.CENTER);
                txtTerm.setHint(R.string.term);
            }
        });
        txtDefinition.setOnFocusChangeListener((v, hasFocus) -> {
            if (txtDefinition.getText().length() != 0 || hasFocus) {
                txtDefinition.setGravity(Gravity.CENTER_HORIZONTAL);
                txtDefinition.setHint("");
            } else {
                txtDefinition.setGravity(Gravity.CENTER);
                txtDefinition.setHint(R.string.definition);
            }
        });

    }

    public void createRecognitionListener() {
        speechRecognizer.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle params) {
                setTextFieldState(selectedTextField, true);
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                setTextFieldState(selectedTextField, false);
            }

            @Override
            public void onError(int error) {
                switch (error) {
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    case SpeechRecognizer.ERROR_NETWORK:
                    case SpeechRecognizer.ERROR_AUDIO:
                    case SpeechRecognizer.ERROR_CLIENT:
                    case SpeechRecognizer.ERROR_SERVER:
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        setTextFieldState(selectedTextField, false);
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        break;
                }
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> spokenStringArray = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (spokenStringArray != null) {
                    String oldText = selectedTextField.getText().toString();
                    if (oldText.length() != 0) {                                            // Checks if there is a space at the end of the string in the Textview, if not then it adds one.
                        oldText += oldText.charAt(oldText.length() - 1) == ' ' ? "" : " ";
                    }
                    String newText = spokenStringArray.get(0) + ". ";
                    newText = Utils.capitalize(newText);
                    selectedTextField.setText(oldText + newText);
                }
                setTextFieldState(selectedTextField, false);
                selectedTextField.setSelection(selectedTextField.getText().length());
                hideKeyboard();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                onResults(partialResults);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    public void setTextFields_OFF() {
        setTextFieldState(txtTerm, false);
        setTextFieldState(txtDefinition, false);
    }

    public void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtTerm.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(txtDefinition.getWindowToken(), 0);
    }

    public void setTextFieldState(EditText field, boolean state) {
        if (state) {
            field.setBackgroundResource(R.drawable.custom_input_mic_on);
        } else {
            field.setBackgroundResource(R.drawable.custom_input_mic_off);
            hideKeyboard();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
    }

    private void checkVoiceCommandPermission() {
        if (isAtLeastMarshmellow()) {
            if (!isMicPermissionGranted()) {
                ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(NewCardActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                    } else {
                        showPermissionRequestDialog();
                    }
                });
                requestMicPermission(launcher);
            } else {
                Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPermissionRequestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission necessary");
        builder.setMessage("Without the microphone permission, voice recognition will not work.\n"
                + "Are you sure you want to deny this permission");
        builder.setPositiveButton("Allow", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("Deny", (dialog, which) -> {
        });
        builder.setCancelable(true);
        builder.create().show();
    }

    private void requestMicPermission(ActivityResultLauncher<String> launcher) {
        launcher.launch(Manifest.permission.RECORD_AUDIO);
    }

    public boolean isAtLeastMarshmellow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public boolean isMicPermissionGranted() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
    }
}
