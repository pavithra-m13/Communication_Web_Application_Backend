
package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.demo.handlers.SocketConnectionHandler;
import com.example.demo.handlers.groupChatConnectionHandler;
import com.example.demo.service.GroupService;
import com.example.demo.service.chatservice;
import com.example.demo.service.messagefileservice;
import com.example.demo.service.messageservice;
import com.example.demo.service.regservice;



@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
    private  messageservice ms ;
	@Autowired
    private  messagefileservice mfs;
    @Autowired
    private  chatservice cs;
    @Autowired
    private  regservice rs;

    @Autowired
    private GroupService gs;

  


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketConnectionHandler(ms,mfs,cs,rs), "/hello")
                .setAllowedOrigins("http://localhost:3000", "http://localhost:3001");
        
        registry.addHandler(new groupChatConnectionHandler(ms,mfs,cs,rs,gs), "/ws/group")
                .setAllowedOrigins("http://localhost:3000", "http://localhost:3001");
    }
}
