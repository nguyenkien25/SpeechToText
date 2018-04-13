package kh.khtn.speedtotext.fragment;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import kh.khtn.speedtotext.R;
import kh.khtn.speedtotext.Utils.Utils;
import kh.khtn.speedtotext.activity.MainActivity;
import kh.khtn.speedtotext.adapter.AdapterDictionaryText;
import kh.khtn.speedtotext.databinding.FragmentDictionaryTextBinding;
import kh.khtn.speedtotext.widget.GridSpacingItemDecoration;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentDictionaryText extends Fragment implements View.OnClickListener {
    private String TAG = FragmentDictionaryText.class.getSimpleName();

    private FragmentManager fragmentManager;
    private FragmentDictionaryTextBinding binding;
    private AdapterDictionaryText adapterDictionaryText;

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public FragmentDictionaryText() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dictionary_text, container, false);
        initViews();
        setListeners();
        return binding.getRoot();

    }

    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        setHasOptionsMenu(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.listDictionary.setLayoutManager(gridLayoutManager);
        binding.listDictionary.setHasFixedSize(true);
        binding.listDictionary.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        binding.listDictionary.setItemAnimator(new DefaultItemAnimator());

        adapterDictionaryText = new AdapterDictionaryText(getActivity(), ((MainActivity) getActivity()).getSpeechText(), new ArrayList<>());
        binding.listDictionary.setAdapter(adapterDictionaryText);

        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(Utils.URL_DICTIONARY_TEXT);
    }

    private void setListeners() {
        binding.btnSpeech.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSpeech:
                ((MainActivity) getActivity()).replaceFragmentSpeechToText();
                break;
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class OkHttpHandler extends AsyncTask<Object, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                String getResponse = get(params[0].toString());
                return getResponse;
            } catch (Exception e) {
                return null;
            }
        }

        private String get(String url) throws IOException {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (result != null) {
                adapterDictionaryText.setListDictionaryText(Utils.jsonParseDictionaryText((String) result));
                adapterDictionaryText.notifyDataSetChanged();
            }
        }
    }

}
