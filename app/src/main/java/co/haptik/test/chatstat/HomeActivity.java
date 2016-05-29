package co.haptik.test.chatstat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import co.haptik.test.chatstat.stat.StatFragment;

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
                return StatFragment.newInstance();
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
