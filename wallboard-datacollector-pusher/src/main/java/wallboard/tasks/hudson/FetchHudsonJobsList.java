package wallboard.tasks.hudson;

import colony.JobResult;
import colony.Task;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class FetchHudsonJobsList implements Task {

    @Override
    public void execute(final JobResult job) {

        final HudsonJobList jobList = new HudsonJobList();

        fetchHudsonJobs();

//        jobList.with {
//            add()
//        }
        jobList.add(new HudsonJob("job1", URI.create("http://localhost/job1")));
        jobList.add(new HudsonJob("job2", URI.create("http://localhost/job2")));

        job.put("hudson.jobs", jobList);
    }

    public void fetchHudsonJobs() {
        // enable debugging
//        System.setProperty("javax.net.debug", "true");
        System.setProperty("javax.net.debug", "all");

        SSLSocketFactory socketFactory = null;
        try {
//            socketFactory = new SSLSocketFactory(new TrustSelfSignedStrategy(), SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            socketFactory = new SSLSocketFactory();
            final TrustStrategy trustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(final X509Certificate[] chain, final String authType)
                        throws CertificateException {
                    return true;
                }
            };
            socketFactory = new SSLSocketFactory(trustStrategy, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyStoreException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Scheme sch = new Scheme("https", 443, socketFactory);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet("https://builds.proxy.tricode.nl/");
        httpClient.getConnectionManager().getSchemeRegistry().register(sch);

        // Via Scoped authentication (whatever that means...)
        final AuthScope authScope = new AuthScope(getRequest.getURI().getHost(), sch.getDefaultPort());
        System.out.println("authScope: " + authScope.toString());
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user", "passwd");
        System.out.println("creds: " + credentials.toString());
        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);

        // Via BASIC auth
//        AuthCache authCache = new BasicAuthCache();
//        BasicScheme basicAuth = new BasicScheme();
//        final HttpHost httpHost = new HttpHost(getRequest.getURI().getHost(), getRequest.getURI().getPort(),
//                getRequest.getURI().getScheme());
//        authCache.put(httpHost, basicAuth);

        // Via DIGEST auth
//        AuthCache authCache = new BasicAuthCache();
//        DigestScheme digestAuth = new DigestScheme();
////        digestAuth.overrideParamter("realm", "some realm");
////        digestAuth.overrideParamter("nonce", "whatever");
//        final HttpHost httpHost = new HttpHost(getRequest.getURI().getHost(), getRequest.getURI().getPort(),
//                getRequest.getURI().getScheme());
//        authCache.put(httpHost, digestAuth);

        // Via BASIC or DIGEST auth
//        BasicHttpContext localcontext = new BasicHttpContext();
//        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpResponse response = null;
        try {
            getRequest.addHeader("accept", "application/xml");

            // When using Scoped auth
            response = httpClient.execute(getRequest);
            // When using BASIC or DIGEST auth
//            response = httpClient.execute(getRequest, localcontext);

            if (response.getStatusLine().getStatusCode() != 200) {
                final String message = "Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode()
                        + " - " + response.getStatusLine().getReasonPhrase();
                throw new RuntimeException(message);
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            httpClient.getConnectionManager().shutdown();
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
    }
}
