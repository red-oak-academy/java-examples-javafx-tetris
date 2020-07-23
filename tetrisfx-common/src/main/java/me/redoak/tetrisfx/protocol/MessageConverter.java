package me.redoak.tetrisfx.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MessageConverter {

    ObjectMapper objectMapper = new ObjectMapper();

    public Message deserialize(String from) throws MessageProcessingException {
        try {
            final ObjectNode node = objectMapper.readValue(from, ObjectNode.class);

            if(node.has("messageType")) {
                final JsonNode messageTypeNode = node.get("messageType");
                final String messageType = messageTypeNode.asText();

                final MessageType messageTypeEnum = MessageType.valueOf(messageType);
                final Class<? extends Message> messageClass = messageTypeEnum.getTargetClass();

                if(messageClass == null) {
                    throw new MessageProcessingException("Unknown Message Type " + messageType);
                }

                return objectMapper.readValue(from, messageClass);

            } else {
                throw new MessageProcessingException("JSON Object did not have a message type property.");
            }


        } catch (JsonProcessingException e) {
            throw new MessageProcessingException(e);
        }
    }

    public String serialize(Message message) throws MessageProcessingException {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new MessageProcessingException(e);
        }
    }
}
