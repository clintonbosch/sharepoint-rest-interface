sharepoint-rest-interface
========================

Java interface for sharepoint restful webservices
--------------

This API allows you perform RESTful webservice calls to your sharepoint server and work with simple POJOs taking away
the need to parse XML or JSON responses. It has only been tested using Sharepoint 2013 server.

To use this API include the sharepoint-rest-api.jar in your project classpath as well as all the dependencies in the
lib directory. You can then use as follows:
    SharepointService service = new SharepointService(*my.sharepoint.server.com*, 443);
    List<SharepointUser> users = service.findAllUsers(*mySharepointUsername*, *mySharepointPassword*, *mySharepointDomain*);
    ...

You can also wire in the bean using spring as follows:
    <bean id="sharepointService" class="za.co.cmb.sharepoint.SharepointService">
      <constructor-arg>*my.sharepoint.server.com*</constructor-arg>
      <constructor-arg type="int"><value>443</value></constructor-arg>
    </bean>

*So far the only methods that are available are as follows:*

**findAllUsers** which returns the name, mugshotUrl and id of all users
**search** which searches the sharepoint server for a given phrase, this will return the equivalent of using the search field on the sharepoint web frontend