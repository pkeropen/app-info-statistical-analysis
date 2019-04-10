package com.vita.collection.app.api.flume;

import com.google.gson.Gson;
import com.vita.collection.app.api.base.Constants;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

import java.nio.charset.Charset;

public class FlumeRpcClientFacade {

    private static FlumeRpcClientFacade instance;


    private RpcClient client;
    private String hostname;
    private int port;

    public static synchronized FlumeRpcClientFacade getInstance() {
        if (instance == null) {
            instance = new FlumeRpcClientFacade();
            instance.init(Constants.FLUME_IP, Constants.FLUME_PORT);
        }
        return instance;
    }

    public void init(String hostname, int port) {
        // Setup the RPCconnection
        this.hostname = hostname;
        this.port = port;
        this.client = RpcClientFactory.getDefaultInstance(hostname, port);
        // Use thefollowing method to create a thrift client (instead of the above line):
        // this.client = RpcClientFactory.getThriftInstance(hostname, port);
    }

    public void sendObjectToFlume(Object object) {
        Gson gson = new Gson();
        String data = gson.toJson(object);
        sendDataToFlume(data);
    }

    public void sendDataToFlume(String data) {
        // Create aFlume Event object that encapsulates the sample data
        Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"));

        // Send theevent
        try {
            client.append(event);
        } catch (EventDeliveryException e) {
            // clean up andrecreate the client
            client.close();
            client = null;
            client = RpcClientFactory.getDefaultInstance(hostname, port);
            // Use thefollowing method to create a thrift client (instead of the above line):
            // this.client =RpcClientFactory.getThriftInstance(hostname, port);
        }
    }

    public void cleanUp() {
        // Close the RPCconnection
        client.close();
    }


}
