package com.nervenets.general.service;

import com.nervenets.general.entity.WsMessage;

public interface WebSocketService {
    void convertAndSend(String topicId, WsMessage message);
}
