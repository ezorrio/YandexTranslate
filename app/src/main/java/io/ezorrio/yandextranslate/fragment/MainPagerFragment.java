package io.ezorrio.yandextranslate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.ezorrio.yandextranslate.R;
import io.ezorrio.yandextranslate.adapter.MainPagerAdapter;

/**
 * Created by golde on 28.03.2017.
 */

public class MainPagerFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private MainPagerAdapter mAdapter;
    private BottomNavigationView mNavigation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new MainPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_pager, container, false);
        mViewPager = (ViewPager) root.findViewById(R.id.pager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
        mNavigation = (BottomNavigationView) root.findViewById(R.id.bottom_navigation);
        mNavigation.setOnNavigationItemSelectedListener(this);
        mViewPager.setAdapter(mAdapter);
        return root;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_translate:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.action_bookmarks:
                mViewPager.setCurrentItem(1);
                //((BookmarksFragment) mAdapter.getItem(1)).onUpdate(getActivity());
                break;
            case R.id.action_history:
                mViewPager.setCurrentItem(2);
                //((HistoryFragment) mAdapter.getItem(2)).onUpdate(getActivity());
                break;
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //do nothing
    }

    @Override
    public void onPageSelected(int position) {
        mAdapter.getItem(position).onResume();
        switch (position){
            case 0:
                mNavigation.setSelectedItemId(R.id.action_translate);
                break;
            case 1:
                mNavigation.setSelectedItemId(R.id.action_bookmarks);
                break;
            case 2:
                mNavigation.setSelectedItemId(R.id.action_history);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //do nothing
    }
}
