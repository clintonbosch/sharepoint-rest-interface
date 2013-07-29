Java Sharepoint REST API
========================

This API allows you perform RESTful webservice calls to your sharepoint server and work with simple POJOs taking away
the need to parse XML or JSON responses. It has only been tested using Sharepoint 2013 server.

To use this API include the sharepoint-rest-api.jar in your project classpath as well as all the dependencies in the
lib directory. You can then use as follows:
```java
    SharepointService service = new SharepointService(foo.com, 443);
    List<SharepointUser> users = service.findAllUsers(myusername, mypassword, mydomain);
    ...
```

You can also wire in the bean using spring by adding this to your application-context.xml:
```
    <bean id="sharepointService" class="za.co.cmb.sharepoint.SharepointService">
      <constructor-arg>*my.sharepoint.server.com*</constructor-arg>
      <constructor-arg type="int"><value>443</value></constructor-arg>
    </bean>
```


*So far the only methods that are available are as follows:*

**findAllUsers** which returns the name, mugshotUrl and id of all users<br/>
**search** which searches the sharepoint server for a given phrase, this will return the equivalent of using the search field on the sharepoint web frontend