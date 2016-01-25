package jhe.com.sunshine.soap.requests;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by jens on 24.01.16.
 */
public interface SoapRequestComplete {
    public void onGetLanguageLoginKundeResponse(SoapObject soapObject);
    public void onGetSpeiseplanResponse(SoapObject soapObject);
    public void onLogout(SoapObject soapObject);
}
