package io.iskaldvind.notes.data;

import android.content.res.Resources;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.models.CardData;

public class CardDataSourceImpl extends BaseCardDataSource {
    private volatile static CardDataSourceImpl sInstance;

    public static CardDataSourceImpl getInstance(Resources resources) {
        CardDataSourceImpl instance = sInstance;
        if (instance == null) {
            synchronized (CardDataSourceImpl.class) {
                if (sInstance == null) {
                    instance = new CardDataSourceImpl(resources);
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private CardDataSourceImpl(Resources resources) {
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        String[] dates = resources.getStringArray(R.array.dates);
        for (int i = 0; i < titles.length; i++) {
            mData.add(new CardData("", "", titles[i], descriptions[i], dates[i]));
        }
        notifyDataSetChanged();
    }
}
