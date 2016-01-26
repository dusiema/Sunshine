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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import jhe.com.sunshine.soap.requests.GetLanguageLoginKunde;
import jhe.com.sunshine.soap.requests.GetSpeiseplan;
import jhe.com.sunshine.soap.requests.Logout;
import jhe.com.sunshine.soap.requests.SoapRequestComplete;
import jhe.com.sunshine.soap.requests.objects.MenuWeek;
import jhe.com.sunshine.soap.requests.objects.MenueDay;
import jhe.com.sunshine.soap.requests.objects.MenueNode;

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
    private MenuWeek menuWeek;

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
        speiseplanAsync.execute(null, null, null);
    }

    private void runLogout() {
        Logout logoutAsync = new Logout(this);
        logoutAsync.execute(null, null, null);
    }


    @Override
    public void onGetLanguageLoginKundeResponse(SoapObject soapObject) {
        runGetSpeiseplan();
    }

    @Override
    public void onGetSpeiseplanResponse(SoapObject soapObject) {

        menuWeek = new MenuWeek();

        SoapObject menueDays = ((SoapObject) soapObject.getProperty("MenueDays"));

        for (int i = 0; i < menueDays.getPropertyCount(); i++) {
            MenueDay menuDay = new MenueDay();
            menuWeek.getMenuDays().add(menuDay);

            menuDay.setDayString("Tag" + i);

            SoapObject soapObjectMenuDay = (SoapObject) menueDays.getProperty(i);

            SoapPrimitive bestellbar = (SoapPrimitive) soapObjectMenuDay.getPrimitiveProperty("Bestellbar");
            menuDay.setBestellbar(Boolean.valueOf(bestellbar.getValue().toString()));
            if (menuDay.getBestellbar()) {
                SoapObject soapObjectMenuNodes = (SoapObject) soapObjectMenuDay.getProperty("MenueNodes");

                for (int j = 0; j < soapObjectMenuNodes.getPropertyCount(); j++) {
                    MenueNode menueNode = new MenueNode();
                    menuDay.getMenueNodes().add(menueNode);

                    SoapObject soapObjectMenuNode = (SoapObject) soapObjectMenuNodes.getProperty(j);
                    SoapPrimitive menuBez = (SoapPrimitive) soapObjectMenuNode.getPrimitiveProperty("MenuBez");
                    menueNode.setMenuBezeichnung(menuBez.toString());
                }
            }
        }

        mSectionsPagerAdapter.notifyDataSetChanged();
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
            return PlaceholderFragment.newInstance(position + 1, menuWeek.getMenuDays().get(position));
        }

        @Override
        public int getCount() {
            if (menuWeek != null && menuWeek.getMenuDays() != null && menuWeek.getMenuDays().size() > 0) {
                return menuWeek.getMenuDays().size();
            } else {
                return 0;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (menuWeek != null && menuWeek.getMenuDays() != null && menuWeek.getMenuDays().size() > 0) {
                return menuWeek.getMenuDays().get(position).getDayString();
            } else {
                switch (position) {
                    case 0:
                        return "SECTION 1";
                    case 1:
                        return "SECTION 2";
                    case 2:
                        return "SECTION 3";
                }
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private MenueDay menuDay;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, MenueDay menueDay) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.menuDay = menueDay;
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

            StringBuilder sb = new StringBuilder();
            for (MenueNode menueNode : menuDay.getMenueNodes()) {
                sb.append(menueNode.getMenuBezeichnung());
                sb.append("\n\n");
            }
            textView.setText(sb.toString());

            return rootView;
        }
    }
}
