package com.seguros.config;

import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api_teste")
public class App extends ResourceConfig {

    public App() {packages("com.seguros");}

}
