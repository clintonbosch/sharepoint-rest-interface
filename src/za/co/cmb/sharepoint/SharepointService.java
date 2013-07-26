package za.co.cmb.sharepoint;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import za.co.cmb.sharepoint.dto.SPDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SharepointService {

    public static String URL_LISTDATA = "/_vti_bin/listdata.svc/";
    public static String URL_SEARCH = "/_api/search/query?querytext=";

    public static String LIST_DOCUMENTS = "Documents()";
    public static String LIST_USERS = "UserInformationList()";

    public static String FILTER_CONTENT_TYPE_DOCUMENT = "(ContentType eq 'Document')";

    private DefaultHttpClient httpclient = new DefaultHttpClient();
    private String serverUrl;
    private int port;
    private String urlPrefix;

    public SharepointService(String serverUrl, int port) {
        this.serverUrl = serverUrl;
        this.port = port;

        urlPrefix = port == 443 ? "https://" : "http://";
        httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(
                    HttpRequest request, HttpContext context)
                    throws HttpException, IOException {
                request.setHeader("Accept", "application/json");
            }
        });
    }

    public List<SPDocument> search(String username, String password, String domain,String searchWord) throws IOException {
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(serverUrl, port),
                    new NTCredentials(username, password, "WORKSTATION", domain));

            SharepointWebserviceDocumentResponse result = (SharepointWebserviceDocumentResponse) getData(
                    urlPrefix + serverUrl + URL_SEARCH + "'" + searchWord + "'",
                    SharepointWebserviceDocumentResponse.class);

            List<SPDocument> spDocuments = new ArrayList<>();
            for (SharepointWebserviceDocumentResponse.SPResult document : result.getSpResultList().getResults()) {
                SPDocument spDocument = new SPDocument();
                spDocuments.add(spDocument);
                spDocument.setName(document.getName());
                SharepointWebserviceDocumentResponse.SPMetadata metadata = document.getMetadata();
                if (metadata != null)
                spDocument.setPath(metadata.getPath());
            }
            return spDocuments;
        } finally {
            // When HttpClient instance is no longer needed, shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    public List<SPDocument> findAllUsers(String username, String password, String domain) throws IOException {
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(serverUrl, port),
                    new NTCredentials(username, password, "WORKSTATION", domain));

            SharepointWebserviceUserResponse result = (SharepointWebserviceUserResponse) getData(
                    urlPrefix + serverUrl + URL_LISTDATA + LIST_USERS,
                    SharepointWebserviceUserResponse.class);
            System.out.println(result);

            List<SPDocument> spDocuments = new ArrayList<>();
//            for (SharepointWebserviceDocumentResponse.SPResult document : result.getSpResultList().getResults()) {
//                SPDocument spDocument = new SPDocument();
//                spDocuments.add(spDocument);
//                spDocument.setName(document.getName());
//                SharepointWebserviceDocumentResponse.SPMetadata metadata = document.getMetadata();
//                if (metadata != null)
//                spDocument.setPath(metadata.getPath());
//            }
            return spDocuments;
        } finally {
            // When HttpClient instance is no longer needed, shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    public List<SPDocument> searchDocuments(String username, String password, String domain,String searchWord) throws IOException {
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(serverUrl, port),
                    new NTCredentials(username, password, "WORKSTATION", domain));

            String filter = "?$filter=" + FILTER_CONTENT_TYPE_DOCUMENT + "and substringof('" + searchWord + "',Name)";

            SharepointWebserviceDocumentResponse result = (SharepointWebserviceDocumentResponse) getData(
                    urlPrefix + serverUrl + URL_LISTDATA + LIST_DOCUMENTS,
                    SharepointWebserviceDocumentResponse.class);
//            SharepointWebserviceDocumentResponse result = (SharepointWebserviceDocumentResponse) getData(
//                    urlPrefix + serverUrl + URL_LISTDATA + LIST_DOCUMENTS + filter,
//                    SharepointWebserviceDocumentResponse.class);

            List<SPDocument> spDocuments = new ArrayList<>();
//            for (SharepointWebserviceDocumentResponse.SPResult document : result.getSpResultList().getResults()) {
//                SPDocument spDocument = new SPDocument();
//                spDocuments.add(spDocument);
//                spDocument.setName(document.getName());
//                SharepointWebserviceDocumentResponse.SPMetadata metadata = document.getMetadata();
//                if (metadata != null)
//                spDocument.setPath(metadata.getPath());
//            }
            return spDocuments;
        } finally {
            // When HttpClient instance is no longer needed, shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    private Object getData(String endpointURL, Class mappingClass) throws IOException {
        HttpGet httpget = new HttpGet(endpointURL);

        System.out.println("Executing request: " + httpget.getRequestLine());
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        System.out.println(response.getStatusLine());
        if (entity != null) {
            System.out.println("Response content length: " + entity.getContentLength());
        } else {
            System.out.println("No response received");
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
//        System.out.println(stringBuilder.toString());
        reader.close();
        EntityUtils.consume(entity);

        System.out.println("Mapping JSON to object");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stringBuilder.toString(), mappingClass);
    }

}
