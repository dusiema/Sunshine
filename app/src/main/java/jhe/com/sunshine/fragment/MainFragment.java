package jhe.com.sunshine.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import jhe.com.sunshine.R;
import jhe.com.sunshine.activity.MainActivity;
import jhe.com.sunshine.soap.requests.GetLanguageLoginKunde;
import jhe.com.sunshine.soap.requests.GetSpeiseplan;
import jhe.com.sunshine.soap.requests.Logout;
import jhe.com.sunshine.soap.requests.SoapRequestComplete;
import jhe.com.sunshine.soap.requests.objects.MenuWeek;
import jhe.com.sunshine.soap.requests.objects.MenueDay;
import jhe.com.sunshine.soap.requests.objects.MenueNode;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements SoapRequestComplete {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private MenuWeek menuWeek;

    private OnFragmentInteractionListener mListener;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        runGetLanguageLoginKunde();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_week_menu, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.menu_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void runGetLanguageLoginKunde() {
        getActivity().setTitle("Login...");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user = prefs.getString("username1", null);
        String pin = prefs.getString("pin1", null);
        if (user != null & pin != null) {
            GetLanguageLoginKunde soapRequest = new GetLanguageLoginKunde(this, user, pin);
            soapRequest.execute(null, null, null);
        }
    }

    private void runGetSpeiseplan() {
        getActivity().setTitle("Speiseplan laden...");
        GetSpeiseplan speiseplanAsync = new GetSpeiseplan(this);
        speiseplanAsync.execute(null, null, null);
    }

    private void runLogout() {
        getActivity().setTitle("Fertig");
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

            SoapObject soapObjectMenuDay = (SoapObject) menueDays.getProperty(i);
            menuDay.setDayString(soapObjectMenuDay.getPrimitivePropertyAsString("Datum"));

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

                    SoapPrimitive bestellteMenge = (SoapPrimitive) soapObjectMenuNode.getPrimitiveProperty("BestMenge");
                    menueNode.setBestellteMenge(Integer.parseInt(bestellteMenge.getValue().toString()));
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

            View rootView = inflater.inflate(R.layout.day, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);

            StringBuilder sb = new StringBuilder();
            sb.append("Tag: " + menuDay.getDayString() + "\n\n\n");
            for (MenueNode menueNode : menuDay.getMenueNodes()) {
                if (menueNode.getBestellteMenge() > 0) {
                    sb.append("BESTELLT:\n");
                }
                sb.append(menueNode.getMenuBezeichnung());
                sb.append("\n\n");
            }
            textView.setText(sb.toString());

            return rootView;
        }
    }

}
