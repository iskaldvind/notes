package io.iskaldvind.notes.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import de.hdodenhof.circleimageview.CircleImageView;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.data.CardDataSourceFirebaseImpl;
import io.iskaldvind.notes.ui.OnDeleteNoteDialogListener;
import io.iskaldvind.notes.utils.ImageLoader;



public class MainActivity extends AppCompatActivity {
    
    public int lastNoteIndex = 0;
    public boolean settingOption1 = false;
    public ImageLoader imageLoader;
    public CardDataSourceFirebaseImpl notes;
    public OnDeleteNoteDialogListener onDeleteNoteDialogListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageLoader = ImageLoader.getInstance(this);
        Toolbar toolbar = initToolbar();
        notes = CardDataSourceFirebaseImpl.getInstance();
        setDeleteDialogListener();
        if (savedInstanceState != null) {
            lastNoteIndex = savedInstanceState.getInt(NoteFragment.KEY_NOTE_ID, 0);
            settingOption1 = savedInstanceState.getBoolean(AccountFragment.KEY_OPTION_1, false);
        }
        initDrawer(toolbar);
        showNotes();
    }
    
    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }
    
    @SuppressLint("NonConstantResourceId")
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.main_root);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.main_drawer);
        navigationView.setNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.account: {
                    showAccount();
                    drawer.closeDrawers();
                    return true;
                }
                case R.id.my: {
                    showNotes();
                    drawer.closeDrawers();
                    return true;
                }
                case R.id.create: {
                    showNoteEdit(lastNoteIndex, true);
                    drawer.closeDrawers();
                    return true;
                }
                case R.id.about: {
                    showAbout();
                    drawer.closeDrawers();
                    return true;
                }
                default: return false;
            }
        });

        View header = navigationView.getHeaderView(0);
        CircleImageView avatar = header.findViewById(R.id.drawer_header_avatar);
        imageLoader.displayImage(getString(R.string.user_avatar_url), avatar);
        TextView userName = header.findViewById(R.id.drawer_header_name);
        userName.setText(getString(R.string.user_name));
    }
    
    public void showNotes() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.main_container, new NotesListFragment());
        fragmentTransaction.commit();
    }
    
    void showNotePortrait() {
        showList(false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.main_container, new NoteFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    void showNoteLandscape() {
        showList(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.side_container, new NoteFragment());
        fragmentTransaction.commit();
    }

    void showNoteEdit(int index, boolean isNew) {
        showList(false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.main_container, NoteEditFragment.newInstance(index, isNew));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    
    void showAccount() {
        showList(false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.main_container, new AccountFragment());
        fragmentTransaction.commit();
    }

    void showAbout() {
        showList(false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.main_container, new AboutFragment());
        fragmentTransaction.commit();
    }
    
    private void showList(boolean show) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FrameLayout sideContainer = findViewById(R.id.side_container);
            int visibility = show ? View.VISIBLE : View.GONE;
            sideContainer.setVisibility(visibility);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NoteFragment.KEY_NOTE_ID, lastNoteIndex);
        outState.putBoolean(AccountFragment.KEY_OPTION_1, settingOption1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search: {
                Toast.makeText(MainActivity.this, "Search is not implemented yet", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.logout: {
                Toast.makeText(MainActivity.this, "Log out is not implemented yet", Toast.LENGTH_SHORT).show();
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public void showDeleteDialog() {
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "dialog_fragment");
    }
    
    private void setDeleteDialogListener() {
        onDeleteNoteDialogListener = new OnDeleteNoteDialogListener() {
            @Override
            public void onDialogPositive() {
                notes.remove(lastNoteIndex);
                FrameLayout noteLayout = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 
                        findViewById(R.id.side_container) : findViewById(R.id.main_container);
                int notesLeft = notes.getSize() - 1;
                if (notesLeft == 0) {
                    noteLayout.removeAllViews();
                } else {
                    if (notesLeft == lastNoteIndex) lastNoteIndex --;
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        showNoteLandscape();
                    } else {
                        showNotes();
                    }
                }
            }

            @Override
            public void onDialogNegative() { }
        };
    }
}
