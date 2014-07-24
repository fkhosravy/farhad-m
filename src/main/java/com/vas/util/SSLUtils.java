package com.vas.util;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLUtils {

    @SuppressWarnings({"deprecation"})
    public static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            return null;
        }
    }

/*
    public static String getResponse(String username, String url, boolean get)
			throws Exception {
		HttpClient client = new DefaultHttpClient();
		if (url.startsWith("https"))
			client = wrapClient(client);
		HttpRequestBase httpPost = get ? new HttpGet(url) : new HttpPost(url);

		String result = "EXCEPTION on get response";
		try {
			result = readLines(client.execute(httpPost));
			return result;
		} finally {
			LogUtils.getLogger(username, WebUtils.class).info(
					URLDecoder.decode(url, "UTF8")
							+ "##### Response = " + result);
			client.getConnectionManager().shutdown();
		}
	}

	public static String readLines(HttpResponse response) throws IOException {

		boolean gzip = false;
		Header[] headers = response.getHeaders("Content-Encoding");
		if (headers != null && headers.length > 0) {
			for (Header h : headers)
				if (h.getValue().equals("gzip")) {
					gzip = true;
					break;
				}
		}

		InputStream input = response.getEntity().getContent();
		if (gzip)
			input = new GZIPInputStream(input);
		StringWriter writer = new StringWriter();
		IOUtils.copy(input, writer, "UTF8");
		return writer.toString();
	}
*/
}
