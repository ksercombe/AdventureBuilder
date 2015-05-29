package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by evan on 5/28/15.
 */
public interface BriteService {
    @GET("/users/me/orders")
    void getOrders(Callback<List<BriteOrder>> cb);

    @GET("/event/{event_id}")
    void getEvent(@Path("event_id") Integer event_id, Callback<BriteOccasion> cb);
}
