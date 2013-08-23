package za.co.cmb.sharepoint;

import za.co.cmb.sharepoint.dto.SharepointSearchResult;
import za.co.cmb.sharepoint.dto.SharepointUser;

import java.io.IOException;
import java.util.List;

public interface SharepointService {

    /**
     * This method returns id, name and mugshot URL of all users on the sharepoint server.
     * @param username The username used for authentication against the sharepoint server
     * @param password The password used for authentication against the sharepoint server
     * @return The list of all users
     * @throws IOException
     */
    List<SharepointUser> findAllUsers(String username, String password) throws IOException;

    List<SharepointSearchResult> search(String username, String password, String searchWord) throws IOException;

    /**
     * This method tests the sharepoint connection and credentials
     */
    public boolean test(String username, String password);

}
