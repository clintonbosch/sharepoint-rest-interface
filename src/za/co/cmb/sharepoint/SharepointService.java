package za.co.cmb.sharepoint;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import za.co.cmb.sharepoint.dto.SharepointUser;
import za.co.cmb.sharepoint.dto.SharepointDocument;
import za.co.cmb.sharepoint.mapping.SharepointResult;
import za.co.cmb.sharepoint.mapping.SharepointWebserviceResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SharepointService {

    private Logger LOG = Logger.getLogger(getClass());

    public static String URL_LISTDATA = "/_vti_bin/listdata.svc/";
    public static String URL_SEARCH = "/_api/search/query?querytext=";

    public static String LIST_USERS = "UserInformationList()";

    private DefaultHttpClient httpClient = new DefaultHttpClient();
    private String serverUrl;
    private int port;
    private String urlPrefix;

    public SharepointService(String serverUrl, int port) {
        this.serverUrl = serverUrl;
        this.port = port;

        urlPrefix = port == 443 ? "https://" : "http://";
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                request.setHeader("Accept", "application/json");
            }
        });
    }

    /**
     * This method returns id, name and mugshot URL of all users on the sharepoint server.
     * @param username The username used for authentication against the sharepoint server
     * @param password The password used for authentication against the sharepoint server
     * @param domain The domain used for authentication against the sharepoint server
     * @return The list of all users
     * @throws IOException
     */
    public List<SharepointUser> findAllUsers(String username, String password, String domain) throws IOException {
        try {
            addCredentials(username, password, domain);

            SharepointWebserviceResponse result = (SharepointWebserviceResponse) getData(
                    urlPrefix + serverUrl + URL_LISTDATA + LIST_USERS,
                    SharepointWebserviceResponse.class);

            List<SharepointUser> sharepointUsers = new ArrayList<>();
            for (SharepointResult user : result.getSharepointResultList().getResults()) {
                SharepointUser sharpointUser = new SharepointUser();
                sharepointUsers.add(sharpointUser);
                sharpointUser.setId(Integer.parseInt(user.getId()));
                sharpointUser.setName(user.getName());
                String pictureUrl = user.getPicture();
                if (pictureUrl != null) {
                    // WTF? why does sharepoint send the picture URL twice ... only Microsoft
                    sharpointUser.setPictureUrl(pictureUrl.substring(0, pictureUrl.indexOf(", ")));
                }
            }
            return sharepointUsers;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public List<SharepointDocument> search(String username, String password, String domain, String searchWord)
            throws IOException {
        try {
            addCredentials(username, password, domain);

            SharepointWebserviceResponse result = (SharepointWebserviceResponse) getData(
                    urlPrefix + serverUrl + URL_SEARCH + "'" + searchWord + "'",
                    SharepointWebserviceResponse.class);

//            List<SharepointDocument> sharepointDocuments = new ArrayList<>();
//            for (SharepointWebserviceDocumentResponse.SPResult document : result.getSpResultList().getResults()) {
//                SharepointDocument spDocument = new SharepointDocument();
//                sharepointDocuments.add(spDocument);
//                spDocument.setName(document.getName());
//                SharepointWebserviceDocumentResponse.SPMetadata metadata = document.getMetadata();
//                if (metadata != null)
//                spDocument.setPath(metadata.getPath());
//            }
//            return sharepointDocuments;
            return null;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    private void addCredentials(String username, String password, String domain) {
        httpClient.getCredentialsProvider().setCredentials(
                new AuthScope(serverUrl, port),
                new NTCredentials(username, password, "WORKSTATION", domain));
    }

    private Object getData(String endpointURL, Class mappingClass) throws IOException {
        HttpGet httpget = new HttpGet(endpointURL);

        LOG.debug("Executing request: " + httpget.getRequestLine());
        HttpResponse response = httpClient.execute(httpget);
        HttpEntity entity = response.getEntity();

        LOG.debug(response.getStatusLine());
        if (entity != null) {
            LOG.debug("Response content length: " + entity.getContentLength());
        } else {
            LOG.error("No response received");
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

        LOG.debug("Mapping JSON to object");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stringBuilder.toString(), mappingClass);
    }

}
