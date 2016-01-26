package jhe.com.sunshine.soap.requests;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by jens on 24.01.16.
 */
public class GetLanguageLoginKunde extends AbstractAsyncSoapRequest {

    public GetLanguageLoginKunde(SoapRequestComplete completeListener) {
        soapRequestCompleteListener = completeListener;
    }

    @Override
    protected SoapObject doInBackground(String... strings) {

        final String METHOD_NAME = "GetLanguageLoginKunde";

        String response = null;

        // Create the outgoing message
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty(NAMESPACE, "psKundenNr", 123456);
        request.addProperty(NAMESPACE, "psPin", 1234);
        request.addProperty(NAMESPACE, "pnMandantID", 1);
        request.addProperty(NAMESPACE, "psCultureDefinition", "de");

        return runSoapRequest(request);
    }

    @Override
    protected void onPostExecute(SoapObject soapObject) {
        soapRequestCompleteListener.onGetLanguageLoginKundeResponse(soapObject);
    }


}
