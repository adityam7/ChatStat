package co.haptik.test.chatstat.model;

/**
 * Created by Aditya Mehta on 29/05/16.
 */
public class Stat {
    private String userName;
    private String name;
    private String imageUrl;
    private long messagesPosted;
    private long messagesFavourited;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getMessagesPosted() {
        return messagesPosted;
    }

    public void setMessagesPosted(long messagesPosted) {
        this.messagesPosted = messagesPosted;
    }

    public long getMessagesFavourited() {
        return messagesFavourited;
    }

    public void setMessagesFavourited(long messagesFavourited) {
        this.messagesFavourited = messagesFavourited;
    }
}
