package jhe.com.sunshine;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by jens on 24.01.16.
 */
public interface SoapRequestComplete {
    public void onGetSpeiseplanResponse(SoapObject soapObject);
}
