package service;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("services")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(filters.CrossOriginResourceFilter.class);
        resources.add(service.ArticleEndpoint.class);
        resources.add(service.ConnectionRequestEndpoint.class);
        resources.add(service.UserEndpoint.class);
        resources.add(service.ConversationEndpoint.class);
        resources.add(service.MessageEndpoint.class);
        resources.add(service.AdvertismentEndpoint.class);

    }
    
}