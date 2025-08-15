package com.bs.chat.mappers;

import com.bs.chat.dto.requests.ChatMessageRequest;
import com.bs.chat.dto.responses.ChatMessageResponse;
import com.bs.chat.entities.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);

    ChatMessage toChatMessage(ChatMessageRequest request);

    List<ChatMessageResponse> toChatMessageResponses(List<ChatMessage> chatMessages);
}