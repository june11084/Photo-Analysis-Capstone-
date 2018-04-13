package june11084.whatisthis.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import june11084.whatisthis.ui.fragments.CameraFragment;
import june11084.whatisthis.ui.fragments.GalleryFragment;
import june11084.whatisthis.ui.fragments.ResultsFragment;

public class MainActivityTabAdapter extends FragmentStatePagerAdapter{
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {"Gallery", "Results" };
    private Context context;

    public MainActivityTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = GalleryFragment.newInstance(position);
                break;
            case 1:
                fragment = ResultsFragment.newInstance(position +1);
                break;
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
