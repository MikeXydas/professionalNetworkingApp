package service;

import java.util.Set;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author mx_pk
 */
@javax.ws.rs.ApplicationPath("/*")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(filters.CrossOriginResourceSharingFilter.class);
        resources.add(service.UserEndpoint.class);
    }
    
}

/*@javax.ws.rs.ApplicationPath("services")
public class ApplicationConfig extends ResourceConfig {


	public ApplicationConfig() {
        packages("filters.");
    }
    
}*/
