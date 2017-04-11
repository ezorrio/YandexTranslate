package io.ezorrio.yandextranslate.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


import io.ezorrio.yandextranslate.fragment.BookmarksFragment;
import io.ezorrio.yandextranslate.fragment.HistoryFragment;
import io.ezorrio.yandextranslate.fragment.TranslationFragment;
import io.ezorrio.yandextranslate.model.History;

/**
 * Created by golde on 28.03.2017.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TranslationFragment.newInstance();
            case 1:
                return BookmarksFragment.newInstance();
            case 2:
                return HistoryFragment.newInstance();
        }
        return null;
    }
}
