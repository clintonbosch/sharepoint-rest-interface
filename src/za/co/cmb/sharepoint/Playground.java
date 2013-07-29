package za.co.cmb.sharepoint;

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
                        "-Dusername=<sharepoint_username> " +
                        "-Dpassword=<sharepoint_password> " +
                        "-Ddomain=<sharepoint_domain> playground");
                System.exit(1);
            }
            SharepointService sharepointService = new SharepointService(args[0], Integer.parseInt(args[1]));
            List<SharepointUser> users = sharepointService.findAllUsers(args[2], args[3], args[4]);
            for (SharepointUser user : users) {
                System.out.println(user.toString());
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
