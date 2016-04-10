
package com.utn.tacs.tp2016c1g4.marvel_webapp;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import junit.framework.TestCase;


public class MainTest extends TestCase {

    private HttpServer httpServer;
    
    private WebResource r;

    public MainTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        //start the Grizzly2 web container 
        httpServer = Main.startServer();

        // create the client
        Client c = Client.create();
        r = c.resource(Main.BASE_URI);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        httpServer.stop();
    }

    public void testFavoritos() {
        String responseMsg = r.path("favoritos").get(String.class);
        assertEquals("{}", responseMsg);
    }
    
    public void testGrupos() {
        String responseMsg = r.path("grupos").get(String.class);
        assertEquals("{}", responseMsg);
    }
    
    public void testHeroes() {
        String responseMsg = r.path("heroes").get(String.class);
        assertEquals("{}", responseMsg);
    }
    
    public void testPerfiles() {
        String responseMsg = r.path("perfiles").get(String.class);
        assertEquals("{}", responseMsg);
    }

    /**
     * Test if a WADL document is available at the relative path
     * "application.wadl".
     */
    public void testApplicationWadl() {
        String serviceWadl = r.path("application.wadl").
                accept(MediaTypes.WADL).get(String.class);
                
        assertTrue(serviceWadl.length() > 0);
    }
}
