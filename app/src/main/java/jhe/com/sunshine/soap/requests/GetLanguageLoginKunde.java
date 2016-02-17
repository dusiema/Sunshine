package jhe.com.sunshine.soap.requests;

import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by jens on 24.01.16.
 */
public class GetLanguageLoginKunde extends AbstractAsyncSoapRequest {

    private final String user;
    private final String pin;

    public GetLanguageLoginKunde(SoapRequestComplete completeListener, String user, String pin) {
        soapRequestCompleteListener = completeListener;
        this.user = user;
        this.pin = pin;
    }

    @Override
    protected SoapObject doInBackground(String... strings) {

        final String METHOD_NAME = "GetLanguageLoginKunde";

        String response = null;

        // Create the outgoing message
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty(NAMESPACE, "psKundenNr", user);
        request.addProperty(NAMESPACE, "psPin", pin);
        request.addProperty(NAMESPACE, "pnMandantID", 1);
        request.addProperty(NAMESPACE, "psCultureDefinition", "de");

        return runSoapRequest(request);
    }

    @Override
    protected void onPostExecute(SoapObject soapObject) {
        soapRequestCompleteListener.onGetLanguageLoginKundeResponse(soapObject);
    }


}
