package org.reducedata.serverCount;

import org.apache.hadoop.hbase.util.Bytes;
import org.codehaus.jettison.json.JSONArray;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import org.restexpress.Request;
import org.restexpress.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by prashanth on 1/13/15.
 */
public class RestExpressServer {

    private static final String SERVICE_NAME = "Test api";
    private static final int PORT_NUMBER = 9901;
    private static final int THREAD_POOL_SIZE = 10;
    private  static  final  RestExpressServer serverController = new RestExpressServer();
    private  static  final  CounterUpdater counter = new CounterUpdater();
    private static final Logger LOG = LoggerFactory.getLogger(RestExpressServer.class);
    public  static  void  main(String[] args){


        RestExpress restServer = null;
        try {
            restServer = initializeServer(args);
            restServer.awaitShutdown();
        }catch (Exception e){
            LOG.info(e.getMessage());
        }
    }
    public static RestExpress initializeServer(String[] args) {
        RestExpress server = new RestExpress()
                .setName(SERVICE_NAME)
                .setBaseUrl("http://localhost:" + PORT_NUMBER)
                .setExecutorThreadCount(THREAD_POOL_SIZE)
                .setPort(PORT_NUMBER)
                .addMessageObserver(new SimpleConsoleLogMessageObserver());

        server.uri("/bidResponse",serverController).action("setTotalBidResponseCounts", HttpMethod.GET).noSerialization();
        server.uri("/getBidResponse",serverController).action("getTotalBidResponse", HttpMethod.GET).noSerialization();
        server.uri("/bidCount",serverController).action("setTotalBidMinute", HttpMethod.GET).noSerialization();
        server.uri("/getBidCount",serverController).action("getTotalBidMinute", HttpMethod.GET).noSerialization();
        server.bind(PORT_NUMBER);
        return server;
    }


    public static  void  testCounts( Request request,Response response){
        //response.setBody("counts"+counter.returnCount());
    }



    public static  void  setTotalBidResponseCounts( Request request,Response response) {

            byte[] counterKeySeconds= Bytes.toBytes(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

            counter.totalCamapignResponseTime(counterKeySeconds, System.currentTimeMillis());

    }
    public static  void  setTotalBidMinute( Request request,Response response) {
        System.out.println("row key " + TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()));
       long counterKeyMinutes=TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
        counter.totalCamapignBids(counterKeyMinutes);

    }
    public static  void  getTotalBidResponse( Request request,Response response) {


             response.setBody("total bid response" + counter.getCampaignResponseTime());

    }
    public static  void  getTotalBidMinute( Request request,Response response) {
        JSONArray array = new JSONArray();
        for(Map.Entry<Long, AtomicLong> counterTest : counter.getBidRequest().entrySet()){

            long value = counterTest.getValue().get();
            array.put(counterTest.getKey()+":"+value);
        }

        response.setBody("total bids"+array.toString());


    }


}
