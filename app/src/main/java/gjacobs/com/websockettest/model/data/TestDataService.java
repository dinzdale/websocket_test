package gjacobs.com.websockettest.model.data;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by brettjacobs on 12/25/14.
 */
public interface TestDataService {
    @GET("/posts")
    List<Post> listPosts();

    @GET("/users")
    List<Users> listUsers();

    @GET("/photos")
    List<Photos> listPhotos();
}
