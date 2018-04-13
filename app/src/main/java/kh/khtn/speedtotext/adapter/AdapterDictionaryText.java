package kh.khtn.speedtotext.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import kh.khtn.speedtotext.R;
import kh.khtn.speedtotext.model.DictionaryItem;

/**
 * Created by 11200 on 4/13/2018.
 */

public class AdapterDictionaryText extends RecyclerView.Adapter<AdapterDictionaryText.ViewHolderDictionary> {
    private Context context;
    private String speechText;
    private List<DictionaryItem> listDictionaryText;

    public class ViewHolderDictionary extends RecyclerView.ViewHolder {
        private RelativeLayout relativeItem = null;
        private TextView tvDictionaryItem = null;

        public ViewHolderDictionary(final View view) {
            super(view);
            relativeItem = view.findViewById(R.id.relativeItem);
            tvDictionaryItem = view.findViewById(R.id.tvDictionaryItem);
        }
    }

    public AdapterDictionaryText(Context context, String speechText, List<DictionaryItem> listDictionaryText) {
        this.context = context;
        this.speechText = speechText;
        this.setListDictionaryText(listDictionaryText);
    }

    public List<DictionaryItem> getListDictionaryText() {
        return listDictionaryText;
    }

    public void setListDictionaryText(List<DictionaryItem> listDictionaryText) {
        this.listDictionaryText = listDictionaryText;
        if (speechText != null && !speechText.trim().equals("")) {
            counter();
        }
    }

    @Override
    public ViewHolderDictionary onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dictionary_text_rv_item, parent, false);
        return new ViewHolderDictionary(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolderDictionary holder, final int position) {
        String counter = (listDictionaryText.get(position).getNumberCounter() == 0 ? "" : " (" + listDictionaryText.get(position).getNumberCounter() + ")");
        holder.tvDictionaryItem.setText(listDictionaryText.get(position).getName() + counter);

        if (listDictionaryText.get(position).getNumberCounter() > 0) {
            holder.relativeItem.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue));
            holder.tvDictionaryItem.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        } else {
            holder.relativeItem.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
            holder.tvDictionaryItem.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        }
    }

    private void counter() {
        String text = speechText.toLowerCase();
        for (int index = 0; index < listDictionaryText.size(); index++) {
            int lastIndex = 0;
            int count = 0;
            while (lastIndex != -1) {
                lastIndex = text.indexOf(listDictionaryText.get(index).getName().toLowerCase(), lastIndex);
                if (lastIndex != -1) {
                    count++;
                    lastIndex += listDictionaryText.get(index).getName().length();
                }
            }
            listDictionaryText.get(index).setNumberCounter(count);
        }
    }

    @Override
    public int getItemCount() {
        return listDictionaryText.size();
    }
}
