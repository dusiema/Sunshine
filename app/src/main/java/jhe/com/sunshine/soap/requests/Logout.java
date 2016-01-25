package jhe.com.sunshine.soap.requests;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by jens on 24.01.16.
 */
public class Logout extends AbstractAsyncSoapRequest {

    public Logout(SoapRequestComplete completeListener) {
        soapRequestCompleteListener = completeListener;
    }

    @Override
    protected SoapObject doInBackground(String... strings) {
        final String METHOD_NAME = "Logout";

        String response = null;

        // Create the outgoing message
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        return runSoapRequest(request);
    }

    @Override
    protected void onPostExecute(SoapObject soapObject) {
        soapRequestCompleteListener.onLogout(soapObject);
    }


}
