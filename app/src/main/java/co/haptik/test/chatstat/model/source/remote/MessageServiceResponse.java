package co.haptik.test.chatstat.model.source.remote;

import java.util.List;

import co.haptik.test.chatstat.model.Message;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public class MessageServiceResponse {
    private long count;
    private List<Message> messages;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
