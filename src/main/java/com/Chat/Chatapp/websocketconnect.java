//package com.Chat.Chatapp;
//
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class websocketconnect {
//
//        private final Set<String> connectedSessions = ConcurrentHashMap.newKeySet();
//
//        @EventListener
//        public void onConnect(SessionConnectEvent event) {
//            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//            String sessionId = accessor.getSessionId();
//            connectedSessions.add(sessionId);
//            System.out.println("✅ Connected: " + sessionId);
//        }
//
//        @EventListener
//        public void onDisconnect(SessionDisconnectEvent event) {
//            String sessionId = event.getSessionId();
//            connectedSessions.remove(sessionId);
//            System.out.println("❌ Disconnected: " + sessionId);
//        }
//
//        public boolean isConnected(String sessionId) {
//            return connectedSessions.contains(sessionId);
//        }
//
//}
