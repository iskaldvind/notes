package io.iskaldvind.notes.data;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import io.iskaldvind.notes.models.CardData;

@SuppressWarnings("WeakerAccess")
public abstract class BaseCardDataSource implements CardDataSource {
    private HashSet<CardDataSourceListener> mListeners = new HashSet<>();
    protected final LinkedList<CardData> mData = new LinkedList<>();

    public void addCardDataSourceListener(CardDataSourceListener listener){
        mListeners.add(listener);
    }

    public void removeCardDataSourceListener(CardDataSourceListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public List<CardData> getCardData() {
        return Collections.unmodifiableList(mData);
    }

    @Override
    public CardData getItemAt(int idx) {
        return mData.get(idx);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }


    @Override
    public void add(@NonNull CardData data) {
        mData.add(data);
        int idx = mData.size() - 1;
        for (CardDataSourceListener listener : mListeners) {
            listener.onItemAdded(idx);
        }
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
        for (CardDataSourceListener listener : mListeners) {
            listener.onItemRemoved(position);
        }
    }

    @Override
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void update(@NonNull CardData data) {
        String id = data.getId();
        if (id != null) {
            int idx = 0;
            for (CardData cardData : mData) {
                if (id.equals(cardData.getId())) {
                    cardData.setPhoto(data.getPhoto());
                    cardData.setUrl(data.getUrl());
                    cardData.setTitle(data.getTitle());
                    cardData.setDescription(data.getDescription());
                    cardData.setDate(data.getDate());
                    notifyUpdated(idx);
                    return;
                }
                idx++;
            }

        }
        add(data);
    }

    protected final void notifyUpdated(int idx) {
        for (CardDataSourceListener listener : mListeners) {
            listener.onItemUpdated(idx);
        }
    }

    protected final void notifyDataSetChanged() {
        for (CardDataSourceListener listener : mListeners) {
            listener.onDataSetChanged();
        }
    }
}