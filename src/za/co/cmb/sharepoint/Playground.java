package za.co.cmb.sharepoint;

import za.co.cmb.sharepoint.dto.SharepointSearchResult;
import za.co.cmb.sharepoint.dto.SharepointUser;

import java.util.List;

public class Playground {

    public static void main(String[] args) {

        try {
            if (args[0].length() == 0 ||
                    args[1].length() == 0 ||
                    args[2].length() == 0 ||
                    args[3].length() == 0 ||
                    args[4].length() == 0) {
                System.out.println("Incorrect usage, please run playground as follows: " +
                        "ant -Dserver.url=<sharepoint_server_url> " +
                        "-Dserver.port=<sharepoint_server_port> " +
                        "-Ddomain=<sharepoint_domain> " +
                        "-Dusername=<sharepoint_username> " +
                        "-Dpassword=<sharepoint_password> playground");
                System.exit(1);
            }

            SharepointService sharepointService = new SharepointServiceSupport(args[0], Integer.parseInt(args[1]), args[2]);
            sharepointService.test(args[3], args[4]);

            System.out.println("###################### USERS ######################");
            List<SharepointUser> users = sharepointService.findAllUsers(args[3], args[4]);
            for (SharepointUser user : users) {
                System.out.println(user.toString());
            }

            System.out.println("###################### SEARCH (" + args[5] + ") ######################");
            List<SharepointSearchResult> results = sharepointService.search(args[3], args[4], args[5]);
            for (SharepointSearchResult result : results) {
                System.out.println(result.toString());
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
