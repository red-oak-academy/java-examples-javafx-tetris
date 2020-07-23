package me.redoak.tetrisfx.protocol;

import me.redoak.tetrisfx.protocol.messages.client.Init;
import org.junit.Test;

public class MessageConverterTests {

    @Test
    public void testSerializeInitMessage() throws MessageProcessingException {

        MessageConverter messageConverter = new MessageConverter();

        Init init = new Init();
        final String result = messageConverter.serialize(init);

        System.out.println(result);
    }
}
