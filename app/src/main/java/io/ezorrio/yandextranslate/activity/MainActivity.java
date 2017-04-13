package io.ezorrio.yandextranslate.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.fragment.BookmarksFragment;
import io.ezorrio.yandextranslate.fragment.HistoryFragment;
import io.ezorrio.yandextranslate.fragment.TranslationFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TranslationFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        mainFragment = TranslationFragment.newInstance();
        setPage(0);
    }

    private void setPage(int position){
        Fragment fragment;
        switch (position){
            case 0:
                fragment = mainFragment;
                break;
            case 1:
                fragment = BookmarksFragment.newInstance();
                break;
            case 2:
                fragment = HistoryFragment.newInstance();
                break;
            default:
                throw new IllegalArgumentException("No page found");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_translate:
                setPage(0);
                break;
            case R.id.action_bookmarks:
                setPage(1);
                break;
            case R.id.action_history:
                setPage(2);
                break;
        }
        return true;
    }
}
