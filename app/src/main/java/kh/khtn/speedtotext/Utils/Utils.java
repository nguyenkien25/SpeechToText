package kh.khtn.speedtotext.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kh.khtn.speedtotext.model.DictionaryItem;

/**
 * Created by 11200 on 4/13/2018.
 */

public class Utils {
    public static final int REQ_CODE_SPEECH_INPUT = 100;

    //Fragment
    public static final String FRAGMENT_SPEECH_TO_TEXT = "FragmentSpeechToText";
    public static final String FRAGMENT_DICTIONARY_TEXT = "FragmentDictionaryText";

    public static final String URL_DICTIONARY_TEXT = "https://s3-ap-southeast-1.amazonaws.com/hoan-xtaypro/document/text.json";
    public static final String KEY_ARRAY_DICTIONARY_TEXT = "data";

    public static List<DictionaryItem> jsonParseDictionaryText(String jsonData) {
        List<DictionaryItem> result = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(jsonData);
            JSONArray jsonArray = data.getJSONArray(KEY_ARRAY_DICTIONARY_TEXT);
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new DictionaryItem(jsonArray.getString(i), 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
