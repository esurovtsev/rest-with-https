package org.jtalks.poulpe.retlet.helloworld.client;

import java.io.IOException;

import org.jtalks.poulpe.web.controller.rest.Authentication;
import org.jtalks.poulpe.web.controller.rest.Credentials;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;

public class CheckCredentialsClient {

    public static void main(final String[] args) throws ResourceException, IOException {
        Client clientConnector = new Client(new Context(), Protocol.HTTPS);
        Series<Parameter> parameters = clientConnector.getContext().getParameters();
        parameters.add("truststorePath", "src/main/resources/clientKey.jks");
        parameters.add("truststorePassword", "password");
        parameters.add("truststoreType", "JKS");

        ClientResource clientResource = new ClientResource("https://localhost:8443/poulpe/rest/authenticate");
        Authentication auth = new Authentication();
        Credentials creds = new Credentials();
        creds.setUsername("admin");
        creds.setPasswordHash("21232f297a57a5a743894a0e4a801fc3");
        auth.setCredintals(creds);
        JaxbRepresentation<Authentication> request = new JaxbRepresentation<Authentication>(auth);
        request.setFormattedOutput(true);

        clientResource.setNext(clientConnector);
        clientResource.post(auth).write(System.out);
    }

}
