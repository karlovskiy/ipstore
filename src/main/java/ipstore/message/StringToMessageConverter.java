package ipstore.message;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.1, 3/10/14
 */
public class StringToMessageConverter implements Converter<String, Message> {
    @Override
    public Message convert(String source) {
        String[] fields = StringUtils.delimitedListToStringArray(source, MessageToStringConverter.SEPARATOR);
        Message message = new Message(fields[0], MessageType.valueOf(fields[1]), MessageStatus.valueOf(fields[2]));
        String url = fields[3];
        if (StringUtils.hasLength(url)) {
            message.setUrl(url);
        }
        String rightText = fields[4];
        if (StringUtils.hasLength(rightText)) {
            message.setRightText(rightText);
        }
        String leftText = fields[5];
        if (StringUtils.hasLength(leftText)) {
            message.setLeftText(leftText);
        }
        return message;
    }
}
