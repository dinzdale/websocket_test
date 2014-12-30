package gjacobs.com.websockettest.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;


public class PhotosDAO extends Model {
    @Column(name = "albumid")
    int albumId;
    @Column(name = "title")
    String title;
    @Column(name = "url")
    String url;
    @Column(name = "thumbnail")
    String thumbnailUrl;

    public PhotosDAO(Photos photos) {
        setAlbumId(photos.getAlbumId());
        setTitle(photos.getTitle());
        setUrl(photos.getUrl());
        setThumbnailUrl(photos.getThumbnailUrl());
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

