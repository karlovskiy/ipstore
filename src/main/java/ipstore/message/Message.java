package ipstore.message;

import java.io.Serializable;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.1, 3/10/14
 */
public class Message implements Serializable {

    public static final String MESSAGE_ATTRIBUTE = "UI_MESSAGE";

    private final String text;
    private final MessageStatus messageStatus;
    private final MessageType messageType;
    private String url;
    private String leftText;
    private String rightText;

    public Message(String text, MessageType messageType, MessageStatus messageStatus) {
        this.text = text;
        this.messageType = messageType;
        this.messageStatus = messageStatus;
    }

    public Message(String text, MessageType messageType, MessageStatus messageStatus,
                   String url, String leftText, String rightText) {
        this.text = text;
        this.messageStatus = messageStatus;
        this.messageType = messageType;
        this.url = url;
        this.leftText = leftText;
        this.rightText = rightText;
    }

    public String getText() {
        return text;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", messageStatus=" + messageStatus +
                ", messageType=" + messageType +
                ", url='" + url + '\'' +
                ", leftText='" + leftText + '\'' +
                ", rightText='" + rightText + '\'' +
                '}';
    }
}
