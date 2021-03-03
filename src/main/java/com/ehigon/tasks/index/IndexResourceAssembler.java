package com.ehigon.tasks.index;

import com.ehigon.tasks.tasks.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Component
public class IndexResourceAssembler {

    private final EntityLinks entityLinks;

    private final LinkRelationProvider relProvider;

    public IndexResourceAssembler(EntityLinks entityLinks, LinkRelationProvider relProvider) {
        this.entityLinks = entityLinks;
        this.relProvider = relProvider;
    }

    public IndexModel buildIndex() {
        final List<Link> links = new ArrayList<>(asList(entityLinks.linkToCollectionResource(TaskModel.class)
                .withRel(relProvider.getCollectionResourceRelFor(TaskModel.class))));
        final IndexModel resource = new IndexModel();
        resource.add(links);
        return resource;
    }
}
