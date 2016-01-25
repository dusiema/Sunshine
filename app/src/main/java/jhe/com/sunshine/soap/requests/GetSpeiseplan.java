package jhe.com.sunshine.soap.requests;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by jens on 24.01.16.
 */
public class GetSpeiseplan extends AbstractAsyncSoapRequest {

    public GetSpeiseplan(SoapRequestComplete completeListener) {
        soapRequestCompleteListener = completeListener;
    }

    @Override
    protected SoapObject doInBackground(String... strings) {
        final String METHOD_NAME = "GetSpeiseplan";

        String response = null;

        // Create the outgoing message
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty(NAMESPACE, "week", 4);
        request.addProperty(NAMESPACE, "year", 2016);

        return runSoapRequest(request);
    }

    @Override
    protected void onPostExecute(SoapObject soapObject) {
        soapRequestCompleteListener.onGetSpeiseplanResponse(soapObject);
    }


}
