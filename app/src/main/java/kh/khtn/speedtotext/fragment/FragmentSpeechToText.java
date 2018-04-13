package kh.khtn.speedtotext.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import kh.khtn.speedtotext.R;
import kh.khtn.speedtotext.Utils.Utils;
import kh.khtn.speedtotext.activity.MainActivity;
import kh.khtn.speedtotext.databinding.FragmentSpeechToTextBinding;

public class FragmentSpeechToText extends Fragment implements View.OnClickListener {
    private String TAG = FragmentSpeechToText.class.getSimpleName();

    private FragmentManager fragmentManager;
    private FragmentSpeechToTextBinding binding;


    public FragmentSpeechToText() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_speech_to_text, container, false);
        getSpeechInput();
        initViews();
        setListeners();
        return binding.getRoot();
    }

    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        setHasOptionsMenu(true);
    }

    private void setListeners() {
        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                ((MainActivity) getActivity()).setSpeechText(binding.txvResult.getText().toString());
                ((MainActivity) getActivity()).replaceFragmentDictionaryText();
                break;
        }
    }

    public void getSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_tt_speech_prompt));

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, Utils.REQ_CODE_SPEECH_INPUT);
        } else {
            Toast.makeText(getActivity(), getString(R.string.speech_tt_speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Utils.REQ_CODE_SPEECH_INPUT:
                if (resultCode == getActivity().RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    binding.txvResult.setText(result.get(0));
                }
                break;
        }
    }
}
