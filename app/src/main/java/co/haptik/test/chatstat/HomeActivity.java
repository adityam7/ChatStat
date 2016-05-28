package co.haptik.test.chatstat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.haptik.test.chatstat.chat.ChatFragment;
import co.haptik.test.chatstat.model.source.MessageDataManager;
import co.haptik.test.chatstat.model.source.MessageDataManagerApi;
import co.haptik.test.chatstat.model.source.local.MessagePersistenceImpl;
import co.haptik.test.chatstat.model.source.remote.MessageServiceImpl;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private HomeContract.Presenter mPresenter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @BindView(R.id.container)
    protected ViewPager mViewPager;

    @BindView(R.id.tabs)
    protected TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.progressbar)
    protected ProgressBar mProgressBar;

    @BindView(R.id.error_layout)
    protected RelativeLayout mErrorLayout;

    @BindView(R.id.errorText)
    protected TextView mErrorText;

    @BindView(R.id.retry)
    protected Button mRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        MessageDataManagerApi dataManager = new MessageDataManager(getApplicationContext(),
                new MessageServiceImpl(),
                new MessagePersistenceImpl(getApplicationContext()));
        mPresenter = new HomePresenter(this, dataManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadContent(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.cleanUp();
    }

    @Override
    public void showContent() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showProgress(boolean show) {
        if(show) {
            mViewPager.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mViewPager.setVisibility(View.VISIBLE);
            mTabLayout.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message, boolean showRetry) {
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
        mErrorText.setText(message);
        mRetry.setVisibility(showRetry ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.retry)
    void RetryClicked() {
        if(mPresenter != null) {
            mPresenter.loadContent(true);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return ChatFragment.newInstance();
            } else {
                return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Chat";
                case 1:
                    return "Stat";
            }
            return null;
        }
    }
}
