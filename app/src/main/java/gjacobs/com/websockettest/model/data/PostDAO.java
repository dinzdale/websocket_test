package gjacobs.com.websockettest.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by brettjacobs on 12/25/14.
 */
@Table(name = "post")
public class PostDAO extends Model {
    @Column(name = "user_id")
    int userId;

    //int id;
    @Column(name = "title")
    String title;
    @Column(name = "body")
    String body;

//    public PostDAO(Post post) {
//        this.userId = post.userId;
//        this.id = post.id;
//        this.title = post.title;
//        this.body = post.body;
//    }

    public PostDAO() {
    }

    public PostDAO build(Post post) {
        setUserId(post.userId).setUserId(post.getUserId()).setTitle(post.getTitle()).setBody(post.getBody());
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public PostDAO setUserId(int userId) {
        this.userId = userId;
        return this;
    }


    public String getTitle() {
        return title;
    }

    public PostDAO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public PostDAO setBody(String body) {
        this.body = body;
        return this;
    }
}
