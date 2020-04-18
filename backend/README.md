To set the external database, set spring.profiles.active=externaldb in application.properties file and configure the datasource in 
application-externaldb.properties. By default, application using h2 database.

Set client-id and client-secret with your own keys:

spring.security.oauth2.client.registration.github.client-id=id

spring.security.oauth2.client.registration.github.client-secret=secret

Visit https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/ for details.
