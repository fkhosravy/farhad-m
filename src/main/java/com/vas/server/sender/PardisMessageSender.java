package com.vas.server.sender;

import com.vas.entity.PardisFields;
import com.vas.engine.entity.PardisConfig;
import com.vas.engine.entity.ProfilerConfig;
import com.vas.util.SSLUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/16/13 1:19 AM
 */
public class PardisMessageSender implements MessageSender {

    private static Logger logger = LoggerFactory.getLogger(PardisMessageSender.class);

    PardisConfig _pardisConfig;
    ProfilerConfig _profilerConfig;
    PardisFields _pardisFields;
    private ProfilerConfig profilerConfig;


    public void setPardisConfig(PardisConfig pardisConfig) {
        _pardisConfig = pardisConfig;
    }

    public void setPardisFields(PardisFields pardisFields) {
        _pardisFields = pardisFields;
    }

    public void setProfilerConfig(ProfilerConfig profilerConfig) {
        _profilerConfig = profilerConfig;
    }

    public ProfilerConfig getProfilerConfig() {
        return _profilerConfig;
    }

    @Override
    public void sendProfiler(String serviceID, String phone, String status)
    {
        logger.info("entered into send to profiler");

        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 2000);
        HttpConnectionParams.setSoTimeout(httpParams, 2000);

        HttpClient httpClient = new DefaultHttpClient(httpParams);

        if (_profilerConfig.getUrl().contains("https"))
            httpClient = SSLUtils.wrapClient(httpClient);

        URIBuilder builder = null;
        try
        {
            if (_profilerConfig.getUrl().endsWith("/"))
                builder = new URIBuilder(_profilerConfig.getUrl().concat(phone));
            else
                builder = new URIBuilder(_profilerConfig.getUrl().concat("/").concat(phone));

            if (!_profilerConfig.getStatus().isEmpty())
                builder = builder.addParameter(_profilerConfig.getStatus(), status);

            if (!_profilerConfig.getServiceName().isEmpty())
            {
                if (serviceID != null)
                    builder = builder.addParameter(_profilerConfig.getServiceName(), serviceID);
            }

            URI uri = builder.build();
            logger.info("uri = " + uri);

            for (int i = 0; i < 2; i++)
            {
                HttpResponse response = null;
                HttpPut httpPut = new HttpPut(uri);

                httpPut.addHeader(BasicScheme.authenticate(
                        new UsernamePasswordCredentials(_profilerConfig.getUserName(), _profilerConfig.getPassword()),
                        "UTF-8", false));
                try {
                    logger.info(httpPut.getURI().toString());

                    response = httpClient.execute(httpPut);
                    logger.warn("Profiler, sent request: " + httpPut.toString());

                    int code = response.getStatusLine().getStatusCode();
                    logger.warn("Profiler, response: " + code);

                    if (response != null)
                        httpPut.abort();

                    if (code == 200)
                        return;
                    else if (code == 401)
                        logger.warn("Profiler, Authentication Failed");
                    else if (code == 400)
                        logger.warn("Profiler, Bad Request");
                    else if (code == 403)
                        logger.warn("Profiler, User Is Forbidden");

                    if (response != null)
                        httpPut.abort();

                    //todo check why sleep
//                    Thread.sleep(4000);
                }
                catch (Exception e) {
                    logger.error("Unable to send message to url: " + uri.toString(), e);
                }
            }

        } catch (URISyntaxException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void sendMessage(String receiver, String message, String serviceID, int price) {

        logger.info("entered into pardis sendMessage");
        logger.info("receiver = " + receiver);
        //logger.info("message = " + message);

        logger.info("Send Message Len:" + message.length() + " ToAddress:" + receiver + " with price:" + price);
        logger.info("Send Message: " + message + " ToAddress:" + receiver);

        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 2000);
        HttpConnectionParams.setSoTimeout(httpParams, 2000);

        HttpClient client = new DefaultHttpClient(httpParams);
        System.out.println(client);

        if (_pardisConfig.getBaseUrl().contains("https")) {
            client = SSLUtils.wrapClient(client);
        }
        URIBuilder builder = null;
        try {
            builder = new URIBuilder(_pardisConfig.getBaseUrl());

            if (!_pardisFields.getServiceField().isEmpty()) {
                if(serviceID!=null){
                    builder = builder.addParameter(_pardisFields.getServiceField(), serviceID);
                }else{
                    builder = builder.addParameter(_pardisFields.getServiceField(), _pardisConfig.getService());
                }
            }

            if (!_pardisFields.getUsernameField().isEmpty()) {
                builder = builder.addParameter(_pardisFields.getUsernameField(), _pardisConfig.getUsername());
            }

            if (!_pardisFields.getPasswordField().isEmpty()) {
                builder = builder.addParameter(_pardisFields.getPasswordField(), _pardisConfig.getPassword());
            }

            if (!_pardisFields.getSenderField().isEmpty())
                builder = builder.addParameter(_pardisFields.getSenderField(), _pardisConfig.getFrom());

            if (!_pardisFields.getReceiverField().isEmpty()) {
                builder = builder.addParameter(_pardisFields.getReceiverField(), receiver);
            }

            if (!_pardisFields.getPriceField().isEmpty()) {
                builder = builder.addParameter(_pardisFields.getPriceField(), price + "");
            }

            if (!_pardisFields.getContentField().isEmpty()) {
                logger.debug("message = " + message);
                builder = builder.addParameter(_pardisFields.getContentField(), message);
//                builder = builder.addParameter(_pardisFields.getContentField(), URLEncoder.encode(message, "UTF-8"));
            }

            URI uri = builder.build();
            logger.info("uri = " + uri);

            for (int i = 0; i < 2; i++)
            {
                HttpResponse response = null;
                HttpGet httpGet = new HttpGet(uri);
                try {
                    logger.info(httpGet.getURI().toString());
                    response = client.execute(httpGet);
                    System.out.println("========================================");
                    System.out.println("========================================");
                    int code = response.getStatusLine().getStatusCode();
                    System.out.println("========================================");
                    logger.info("Redirecting message. code: " + code);
                    System.out.println("========================================");

                    if (code <= 300) {
                        if (response != null)
                            httpGet.abort();
                        return;
                    }

                    if (response != null)
                        httpGet.abort();

                    //todo check why sleep
//                    Thread.sleep(4000);
                }
                catch (Exception e) {
                    logger.error("Unable to send message to url: " + uri.toString(), e);
                }
            }

            logger.error("Unable to send message to url: " + uri.toString());
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendMessage(String receiver, String message,String serviceID) {
        sendMessage(receiver, message, serviceID, 0);
        logger.info("Send Message {}, to Receiver {} , With ServiceId {} ", new Object[]{message, receiver, serviceID});
    }

}
