package kh.khtn.speedtotext.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kh.khtn.speedtotext.R;
import kh.khtn.speedtotext.Utils.Utils;
import kh.khtn.speedtotext.fragment.FragmentDictionaryText;
import kh.khtn.speedtotext.fragment.FragmentSpeechToText;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment fragmentSpeechToText;
    private String speechText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_frame_container, new FragmentDictionaryText(), Utils.FRAGMENT_DICTIONARY_TEXT)
                    .commit();
        }
    }

    public void replaceFragmentDictionaryText() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.main_frame_container, new FragmentDictionaryText(), Utils.FRAGMENT_DICTIONARY_TEXT)
                .commit();
    }

    public void replaceFragmentSpeechToText() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.main_frame_container, new FragmentSpeechToText(), Utils.FRAGMENT_SPEECH_TO_TEXT)
                .commit();
    }

    public String getSpeechText() {
        return speechText;
    }

    public void setSpeechText(String speechText) {
        this.speechText = speechText;
    }

    @Override
    public void onBackPressed() {
        // Find the tag of sign up and forgot password fragment
        fragmentSpeechToText = fragmentManager.findFragmentByTag(Utils.FRAGMENT_SPEECH_TO_TEXT);
        if (fragmentSpeechToText != null) {
            replaceFragmentDictionaryText();
        } else {
            super.onBackPressed();
        }
    }
}
