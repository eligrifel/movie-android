package view;


import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


/**
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> tabName;
    int NumberOfPagers = 3;
    Context _context;
    private FragmentManager mFragmentManager;


    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentManager = fm;
        _context = context;
        tabName = new ArrayList<>();
        tabName.add("Category");
        tabName.add("search");
        tabName.add("Top rated");

    }

    //constructor for admin panel
    public MyPagerAdapter(FragmentManager fm, Context context, boolean is_admin) {

        super(fm);
        mFragmentManager = fm;
        _context = context;
        tabName = new ArrayList<>();
        tabName.add("Category");
        tabName.add("Search");
        tabName.add("Dashboard");
        if (is_admin) {
            tabName.add("admin panel");
        }

    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:


                return new Tab1();

            case 1:

                return new Tab2();
            case 2:

                return new Tab3();
            case 3:

                return new AdminFragmentTab();
            case 4:

                return new AdminFragmentTab();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NumberOfPagers;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return (tabName.get(position).toString());
    }

    public Fragment getActiveFragment(ViewPager container, int position) {
        String name = makeFragmentName(container.getId(), position);
        return mFragmentManager.findFragmentByTag(name);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}
