package jhe.com.sunshine;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import jhe.com.sunshine.soap.requests.GetLanguageLoginKunde;
import jhe.com.sunshine.soap.requests.GetSpeiseplan;
import jhe.com.sunshine.soap.requests.Logout;
import jhe.com.sunshine.soap.requests.SoapRequestComplete;

public class MainActivity extends AppCompatActivity implements SoapRequestComplete {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        runGetLanguageLoginKunde();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void runGetLanguageLoginKunde() {
        GetLanguageLoginKunde soapRequest = new GetLanguageLoginKunde(this);
        soapRequest.execute(null, null, null);
    }

    private void runGetSpeiseplan() {
        GetSpeiseplan speiseplanAsync = new GetSpeiseplan(this);
        speiseplanAsync.execute(null, null ,null);
    }

    private void runLogout() {
        Logout logoutAsync = new Logout(this);
        logoutAsync.execute(null, null ,null);
    }


    @Override
    public void onGetLanguageLoginKundeResponse(SoapObject soapObject) {
        runGetSpeiseplan();
    }

    @Override
    public void onGetSpeiseplanResponse(SoapObject soapObject) {
        SoapObject menuDays = (SoapObject) soapObject.getProperty("MenueDays");
        SoapObject menuDay = (SoapObject) menuDays.getProperty("MenueDay");
        SoapObject menuNodes = (SoapObject) menuDay.getProperty("MenueNodes");

        StringBuilder menusStringBuilder = new StringBuilder();
        for (int i = 0; i < menuNodes.getPropertyCount(); i++) {
            SoapObject menuNode = (SoapObject) menuNodes.getProperty(i);
            SoapPrimitive menuBez = (SoapPrimitive) menuNode.getPrimitiveProperty("MenuBez");
            menusStringBuilder.append(menuBez.getValue());
            menusStringBuilder.append("\n\n");
        }
//        SoapObject menuNode = (SoapObject) menuNodes.getProperty("MenueNode");
//        String menuBez = (String) menuNode.getPrimitiveProperty("MenuBez");


        TextView sectionLabel = (TextView) findViewById(R.id.section_label);
        sectionLabel.setText(menusStringBuilder.toString());

        // logout
        runLogout();
    }

    @Override
    public void onLogout(SoapObject soapObject) {
        Log.i("LOGOUT", "Logout successful.\n\n");
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
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

        public PlaceholderFragment() {
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
}
