package com.amunna.negotiator.service.resources;

import com.amunna.negotiator.sdk.core.ClientProductPrice;
import com.amunna.negotiator.sdk.core.TargetClient;
import com.amunna.negotiator.service.datacollect.ProductPriceManager;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.yammer.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/price")
@Produces(MediaType.APPLICATION_JSON)
public class NegotiatorResource {
    private static final Logger logger = LoggerFactory.getLogger(NegotiatorResource.class);

    @Inject ProductPriceManager productPriceManager;

    @GET
    @Timed(group = "amunna.negotiator", type = "NegotiatorResource")
    public Map<String, String> getPriceForCategory(@QueryParam("clientUrl") String clientUrl,
                                                   @QueryParam("categoryId") String categoryId) {
        Preconditions.checkNotNull(clientUrl, "Please provide clientUrl");
        Preconditions.checkArgument(!isEmpty(clientUrl), "Please provide valid clientUrl");
        Preconditions.checkNotNull(categoryId, "Please provide categoryId");
        Preconditions.checkArgument(!isEmpty(categoryId), "Please provide valid categoryId");


        Map<String, String> productPrice = Maps.newHashMap();
        try {
            productPrice = productPriceManager.getPriceForClientAndCategory(clientUrl, categoryId);
        } catch (Exception e) {
            Throwables.propagate(e);
        }
        return productPrice;
    }


    @POST
    @Path("/batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(group = "amunna.negotiator", type = "NegotiatorResource")
    public List<ClientProductPrice> getPriceForCategoryInBatch(List<TargetClient> targetClientList) {
        for(TargetClient targetClient : targetClientList) {
            Preconditions.checkNotNull(targetClient.getClientUrl(), "Please provide clientUrl");
            Preconditions.checkArgument(!isEmpty(targetClient.getClientUrl()), "Please provide valid clientUrl");
            Preconditions.checkNotNull(targetClient.getCategoryId(), "Please provide categoryId");
            Preconditions.checkArgument(!isEmpty(targetClient.getCategoryId()), "Please provide valid categoryId");
        }
        List<ClientProductPrice> productPrice = Lists.newArrayList();
        try {
            productPrice = productPriceManager.getPriceForClientAndCategoryInBatch(targetClientList);
        } catch (Exception e) {
            Throwables.propagate(e);
        }
        return productPrice;
    }

    private static boolean isEmpty(String str) {
        str = str.trim();
        return str.length() > 0 ? false : true;
    }

}
