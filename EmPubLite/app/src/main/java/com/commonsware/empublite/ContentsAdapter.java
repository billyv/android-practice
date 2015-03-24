package com.commonsware.empublite;

import android.app.Activity;
import android.app.Fragment;
import android.support.v13.app.FragmentStatePagerAdapter;

/**
 * Created by Billy on 2015-02-26.
 */
public class ContentsAdapter extends FragmentStatePagerAdapter {

    private BookContents contents = null;

    // You have to implement below two getItem and getCount but otherwise this is a standard
    // implementation of FragmentStatePagerAdapter. These methods are not even taken advantage of
    // in other parts in code. Just taking advantage of the superclass's workablity.
    public ContentsAdapter(Activity context, BookContents contents) {
        super(context.getFragmentManager());
        this.contents = contents;
    }

    @Override
    public Fragment getItem(int position) {
        return SimpleContentFragment.newInstance(contents.getChapterPath(position));
    }

    @Override
    public int getCount() {
        return contents.getChapterCount();
    }
}
