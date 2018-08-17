package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author ic
 */
@javax.ws.rs.ApplicationPath("linkedInRestfulProject")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(filters.CrossOriginResourceFilter.class);
        //resources.add(model.service.UserFacadeREST.class);
        resources.add(service.AdvertismentEndpoint.class);
        resources.add(service.ArticleEndpoint.class);
        resources.add(service.ConnectionRequestEndpoint.class);
        resources.add(service.UserEndpoint.class);
        resources.add(service.ConversationEndpoint.class);
        resources.add(service.MessageEndpoint.class);

    }
    
}