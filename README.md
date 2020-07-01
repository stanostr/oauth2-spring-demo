# oauth2-spring-demo

<h2>Authorization Server</h2>
<br>
<p>
<code>dummy-authorization-server</code> plays the role of the Authorization Server. Spring-based Authorization servers are now deprecated. 
Instead, third-party Authorization Servers like Google, Ping, etc will be used. However, this simple Spring Boot project should perform as the Auth Server
for our purposes.
It has two important endpoints, which any Auth Server must have, which are configured in <code>AuthorizationServerConfiguration</code>:
  <ul>
   <li>http://localhost:8083/as/oauth/token - Where the client app can get a token through a POST request</li>
   <li>http://localhost:8083/as/oauth/check-token - Where the resource server (protected resource) goes to check whether the token provided to it by the client is valid</li>
  </ul>
For simple demo purposes, authorization server is aware of one client app called <code>client1</code> with password <code>abc123</code> 
It is aware of two users: <code>sven</code> with password <code>abc123</code> and <code>helga</code> with password <code>abc321</code>
</p> <br>
<h2>Resource Server</h2>
<br>
<p>
The Resource Server (<code>secured-webservice</code> module) is the app containing the secured endpoints that we want only authenticated users to access.
The secured endpoint is at <code>http://localhost:8081/webservice/secured</code>. There is also an unsecured endpoint at <code>http://localhost:8081/webservice/unsecured</code> that anyone can access.
To access the secured endpoint, an appropriate bearer token must be sent in the request's "Authorization" header. The Resource Server will then call the Auth Server's <code>http://localhost:8083/as/oauth/check-token</code>
endpoint to check the validity of the token sent by the client. If the token is valid and not expired, the resource server will grant access to the secured endpoint (otherwise you will see an error).
</p>

<h2>Running the Apps</h2>
<br>
<p>
Run both apps locally. To see how a client app would access the secured endpoint on the resource server, first make an appropriate POST request to the 
<code>http://localhost:8083/as/oauth/token</code> endpoint, to obtain the bearer token.
Then send this token with every request to the <code>http://localhost:8081/webservice/secured</code> endpoint of the resource server. The bearer token should be in the Authorization header,
prefixed with "Bearer ". As an aside, if testing with Postman just use the "Authorization: Bearer token" and put the token in the token field, it automatically prefixes it with "Bearer " when sending request.
</p>
