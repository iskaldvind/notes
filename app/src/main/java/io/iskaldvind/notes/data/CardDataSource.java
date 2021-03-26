package io.iskaldvind.notes.data;

import androidx.annotation.NonNull;

import java.util.List;

import io.iskaldvind.notes.models.CardData;

public interface CardDataSource {
    interface CardDataSourceListener {
        void onItemAdded(int idx);
        void onItemRemoved(int idx);
        void onItemUpdated(int idx);
        void onDataSetChanged();
    }

    void addCardDataSourceListener(CardDataSourceListener listener);
    void removeCardDataSourceListener(CardDataSourceListener listener);

    List<CardData> getCardData();
    CardData getItemAt(int idx);
    int getItemsCount();

    void add(@NonNull CardData data);
    void update(@NonNull CardData data);
    void remove(int position);
    void clear();
}
