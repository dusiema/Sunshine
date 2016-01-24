package jhe.com.sunshine;

import android.net.LocalSocketAddress;
import android.os.AsyncTask;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jens on 24.01.16.
 */
public abstract class AbstractAsyncSoapRequest extends AsyncTask<String, Void, SoapObject> {
    public static String sessionCookieValue = null;

    protected SoapRequestComplete soapRequestCompleteListener;

    final String NAMESPACE = "http://www.MBS5.de/WebServices";
    final String SOAP_ACTION = "";
    final String URL = "http://ibs.sunshine-catering.de/ibs/ibswebservice/IbsWebService.asmx";
    private String sessionCookieName = "Cookie";
    private List headerList = null;

    public SoapObject runSoapRequest(SoapObject request) {
        SoapObject response = null;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(request);

        // Create a transport layer for the J2SE platform. You should change this for
        // another transport on midp or j2me devices.
        HttpTransportSE transportSE = new HttpTransportSE(URL);
        // turn on debug mode if you want to see what is happening over the wire.

        if (sessionCookieValue != null) {
            HeaderProperty sessionCookie = new HeaderProperty(sessionCookieName, sessionCookieValue);
            if (headerList == null) {
                headerList = new ArrayList();
            }
            headerList.add(sessionCookie);
        }

        transportSE.debug = true;
        try {
            // call and print out the result
            headerList = transportSE.call(SOAP_ACTION, envelope, headerList);

            for (Object header : headerList) {
                HeaderProperty headerProperty = (HeaderProperty) header;
                String headerKey = headerProperty.getKey();
                String headerValue = headerProperty.getValue();
                if (headerKey != null && headerKey.equalsIgnoreCase("Set-Cookie")) {
                    sessionCookieValue = headerValue.split(";")[0];
                }
                System.out.println(headerKey +" : " + headerValue);
            }

            if (envelope != null && envelope.getResponse() != null) {
                response = (SoapObject) envelope.getResponse();
                System.out.println(envelope.getResponse());
            }
        } catch (Exception e) {
            // if we get an error print the stacktrace and dump the response out.
            e.printStackTrace();
            //System.out.println(transportSE.responseDump);
        }
        return response;
    }
}
