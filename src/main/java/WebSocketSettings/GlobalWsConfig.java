/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocketSettings;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 * @author development
 */
public class GlobalWsConfig extends ServerEndpointConfig.Configurator {

    public static final String SESSION = "session";

    @Override
    public void modifyHandshake(ServerEndpointConfig conf, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(conf, request, response);

        conf.getUserProperties().put(SESSION, request.getHttpSession());

    }

}
