package wallboard.tasks.hudson;

import colony.JobResult;
import colony.Task;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

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
//        System.setProperty("javax.net.debug", "ssl:handshake");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));

        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("lala", "lalala");
        System.out.println("creds: " + credentials.toString());
        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);

        registerHttpsScheme(443, httpClient);

        HttpGet getRequest = new HttpGet("https://builds.proxy.tricode.nl/");
        getRequest.addHeader("Content-Type", "UTF-8");

        final HttpResponse response;
        try {
            response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                final int code = response.getStatusLine().getStatusCode();
                final String reason = response.getStatusLine().getReasonPhrase();
                throw new RuntimeException(code + " " + reason);
            }
            if (response.getEntity() == null) {
                final String reason = "No content";
                throw new RuntimeException(reason);
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerHttpsScheme(final int port, final DefaultHttpClient httpClient) {
        final Scheme scheme = createHttpsScheme(port);
        httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
    }

    private Scheme createHttpsScheme(final int port) {
        SSLSocketFactory sf = null;
        sf = SSLSocketFactory.getSocketFactory();
//        try {
//            sf = new SSLSocketFactory(
//                    new AcceptCertificateBasedOnHostName(certificateHostName),
//                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//        } catch (KeyStoreException ex) {
//            throw new HttpFileTransferSslException(ex);
//        } catch (UnrecoverableKeyException ex) {
//            throw new HttpFileTransferSslException(ex);
//        } catch (NoSuchAlgorithmException ex) {
//            throw new HttpFileTransferSslException(ex);
//        } catch (KeyManagementException ex) {
//            throw new HttpFileTransferSslException(ex);
//        }
        return new Scheme("https", port, sf);
    }

//    public void someOldStuff() {
//        // Via BASIC auth
//        AuthCache authCache = new BasicAuthCache();
//        BasicScheme basicAuth = new BasicScheme();
//        final HttpHost httpHost = new HttpHost(getRequest.getURI().getHost(), getRequest.getURI().getPort(),
//                getRequest.getURI().getScheme());
//        authCache.put(httpHost, basicAuth);
//
//        // Via DIGEST auth
////        AuthCache authCache = new BasicAuthCache();
////        DigestScheme digestAuth = new DigestScheme();
//////        digestAuth.overrideParamter("realm", "some realm");
//////        digestAuth.overrideParamter("nonce", "whatever");
////        final HttpHost httpHost = new HttpHost(getRequest.getURI().getHost(), getRequest.getURI().getPort(),
////                getRequest.getURI().getScheme());
////        authCache.put(httpHost, digestAuth);
//
//        // Via BASIC or DIGEST auth
//        BasicHttpContext localcontext = new BasicHttpContext();
//        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
//
//        HttpResponse response = null;
//        try {
//            getRequest.addHeader("accept", "application/xml");
//
//            // When using Scoped auth
//            response = httpClient.execute(getRequest);
//            // When using BASIC or DIGEST auth
//            response = httpClient.execute(getRequest, localcontext);
//
//            if (response.getStatusLine().getStatusCode() != 200) {
//                final String message = "Failed : HTTP error code : "
//                        + response.getStatusLine().getStatusCode()
//                        + " - " + response.getStatusLine().getReasonPhrase();
//                throw new RuntimeException(message);
//            }
//
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader((response.getEntity().getContent())));
//
//            String output;
//            System.out.println("Output from Server .... \n");
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
////            httpClient.getConnectionManager().shutdown();
//            HttpClientUtils.closeQuietly(httpClient);
//            HttpClientUtils.closeQuietly(response);
//        }
//    }
}
