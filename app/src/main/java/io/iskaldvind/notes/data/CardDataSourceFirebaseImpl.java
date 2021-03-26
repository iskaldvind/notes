package io.iskaldvind.notes.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.LinkedList;
import io.iskaldvind.notes.models.CardData;
import io.iskaldvind.notes.models.CardDataFromFirestore;

public class CardDataSourceFirebaseImpl extends BaseCardDataSource {
    private static final String TAG = "CardDataSourceFirebase";
    private final static String COLLECTION_NOTES = "Notes";
    private volatile static CardDataSourceFirebaseImpl sInstance;

    private final FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private final CollectionReference mCollection = mStore.collection(COLLECTION_NOTES);

    public static CardDataSourceFirebaseImpl getInstance() {
        CardDataSourceFirebaseImpl instance = sInstance;
        if (instance == null) {
            synchronized (CardDataSourceImpl.class) {
                if (sInstance == null) {
                    instance = new CardDataSourceFirebaseImpl();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private CardDataSourceFirebaseImpl() {
        mCollection.orderBy("date", Query.Direction.DESCENDING);
        mCollection
                .get()
                .addOnCompleteListener(this::onFetchComplete)
                .addOnFailureListener(this::onFetchFailed);
    }

    private void onFetchComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            LinkedList<CardData> data = new LinkedList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                data.add(new CardDataFromFirestore(
                        document.getId(), document.getData()));
            }
            mData.clear();
            mData.addAll(data);
            data.clear();
            notifyDataSetChanged();
        }
    }

    private void onFetchFailed(Exception e) {
        Log.e(TAG, "Fetch failed", e);
    }

    @Override
    public void add(@NonNull CardData data) {
        final CardDataFromFirestore cardData;
        Log.d("ADD", data + "");
        if (data instanceof CardDataFromFirestore) {
            Log.d("ADD", "TRUE");
            cardData = (CardDataFromFirestore) data;
        } else {
            Log.d("ADD", "FALSE");
            cardData = new CardDataFromFirestore(data);
        }
        mCollection.add(cardData.getFields()).addOnSuccessListener(documentReference -> {
            
            cardData.setId(documentReference.getId());
            super.add(cardData);
            Log.d("ADD", "NEW " + mData);
        });
    }

    @Override
    public void update(CardData data) {
        String id = data.getId();
        mCollection.document(id).update(new CardDataFromFirestore(data).getFields());
        super.update(data);
    }

    @Override
    public void remove(int position) {
        String id = mData.get(position).getId();
        mCollection.document(id).delete();
        super.remove(position);
    }

    @Override
    public void clear() {
        for (CardData cardData : mData) {
            mCollection.document(cardData.getId()).delete();
        }
        super.clear();
    }
}
