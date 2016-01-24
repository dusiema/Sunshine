package jhe.com.sunshine;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
        request.addProperty(NAMESPACE, "psKundenNr", 8150815);
        request.addProperty(NAMESPACE, "psPin", 8151);
        request.addProperty(NAMESPACE, "pnMandantID", 1);
        request.addProperty(NAMESPACE, "psCultureDefinition", "de");

        return runSoapRequest(request);
    }

    @Override
    protected void onPostExecute(SoapObject soapObject) {
        runGetSpeiseplan();
    }

    private void runGetSpeiseplan() {
        GetSpeiseplan speiseplanAsync = new GetSpeiseplan(soapRequestCompleteListener);
        speiseplanAsync.execute(null, null ,null);
    }

}
