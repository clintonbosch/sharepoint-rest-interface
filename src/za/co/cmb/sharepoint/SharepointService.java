package za.co.cmb.sharepoint;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import za.co.cmb.sharepoint.dto.SharepointSearchResult;
import za.co.cmb.sharepoint.dto.SharepointUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SharepointService {

    private Logger LOG = Logger.getLogger(getClass());

    public static String URL_LISTDATA = "/_vti_bin/listdata.svc/";
    public static String URL_SEARCH = "/_api/search/query?querytext=";

    public static String LIST_USERS = "UserInformationList()";

    private DefaultHttpClient httpClient;
    private String serverUrl;
    private int port;
    private String domain;
    private String urlPrefix;

    public SharepointService(String serverUrl, int port, String domain) {
        this.serverUrl = serverUrl;
        this.port = port;
        this.domain = domain;

        urlPrefix = port == 443 ? "https://" : "http://";
    }

    private void initHttpConnection() {
        httpClient = new DefaultHttpClient();
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                request.setHeader("Accept", "application/json;odata=verbose");
            }
        });
    }

    /**
     * This method returns id, name and mugshot URL of all users on the sharepoint server.
     * @param username The username used for authentication against the sharepoint server
     * @param password The password used for authentication against the sharepoint server
     * @return The list of all users
     * @throws IOException
     */
    public List<SharepointUser> findAllUsers(String username, String password) throws IOException {
        List<SharepointUser> sharepointUsers = new ArrayList<>();
        String endpoint = urlPrefix + serverUrl + URL_LISTDATA + LIST_USERS;
        JsonNode results = getJsonNode(username, password, endpoint, "results");
        Iterator<JsonNode> iterator = results.getElements();
        while (iterator.hasNext()) {
            JsonNode user = iterator.next();
            SharepointUser sharpointUser = new SharepointUser();
            sharepointUsers.add(sharpointUser);
            sharpointUser.setId(user.get("Id").asText());
            sharpointUser.setName(user.get("Name").getTextValue());
            String pictureUrl = user.get("Picture").getTextValue();
            if (pictureUrl != null) {
                // WTF? why does sharepoint send the picture URL twice ... only Microsoft
                sharpointUser.setPictureUrl(pictureUrl.substring(0, pictureUrl.indexOf(", ")));
            }
        }
        return sharepointUsers;
    }

    public List<SharepointSearchResult> search(String username, String password, String searchWord)
            throws IOException {
        List<SharepointSearchResult> results = new ArrayList<>();
        String endpoint = urlPrefix + serverUrl + URL_SEARCH + "'" + searchWord + "'";
        JsonNode rows = getJsonNode(username, password, endpoint, "Rows");

        Map<String, String> values = new HashMap<>();
        if (rows != null) {
            for (JsonNode cell : rows.findValues("Cells")) {
                for (final JsonNode cellResult : cell.get("results")) {
                    values.put(cellResult.get("Key").getTextValue(), cellResult.get("Value").getTextValue());
                }
                SharepointSearchResult sharepointSearchResult = new SharepointSearchResult();
                sharepointSearchResult.setPath(values.get("Path"));
                sharepointSearchResult.setParentFolder(values.get("ParentLink"));
                sharepointSearchResult.setAuthor(values.get("Author"));
                sharepointSearchResult.setHitHighlightedSummary(values.get("HitHighlightedSummary"));
                sharepointSearchResult.setLastModified(values.get("LastModifiedTime"));
                sharepointSearchResult.setRank(Double.parseDouble(values.get("Rank")));
                sharepointSearchResult.setSiteName(values.get("SiteName"));
                sharepointSearchResult.setTitle(values.get("Title"));
                results.add(sharepointSearchResult);
            }
        }
        return results;
    }

    private void addCredentials(String username, String password) {
        httpClient.getCredentialsProvider().setCredentials(
                new AuthScope(serverUrl, port),
                new NTCredentials(username, password, "WORKSTATION", domain));
    }

    private JsonNode getJsonNode(String username, String  password, String endpointURL) throws IOException {
        return getJsonNode(username, password, endpointURL, null);
    }

    private JsonNode getJsonNode(String username, String  password, String endpointURL, String elementName)
            throws IOException {
        BufferedReader reader = null;
        JsonNode node = null;
        try {
            initHttpConnection();
            addCredentials(username, password);
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
            reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
//            System.out.println(stringBuilder.toString());
            EntityUtils.consume(entity);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readValue(stringBuilder.toString(), JsonNode.class);
            if (elementName == null) {
                node = rootNode;
            } else {
                node =  rootNode.findValue(elementName);
            }
        } catch (Exception e) {
            //code
        } finally {
            if (reader != null) {
                reader.close();
            }
            httpClient.getConnectionManager().shutdown();
        }
        return node;
    }

}
