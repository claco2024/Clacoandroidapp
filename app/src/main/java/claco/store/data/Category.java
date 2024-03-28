package claco.store.data;

public class Category {
    private String mImageUrl;
    private String mName;
    private String mId;
    private String mDescription;

    public Category(String imageUrl, String name, String id, String description) {
        mImageUrl = imageUrl;
        mName = name;
        mId = id;
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }
}
