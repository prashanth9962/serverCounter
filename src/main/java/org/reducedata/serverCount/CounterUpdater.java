package org.reducedata.serverCount;

import com.google.common.util.concurrent.AtomicLongMap;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by prashanth on 1/13/15.
 */
public class CounterUpdater {
    final private  static ConcurrentHashMap<byte[], AtomicLong> countryUpdate = new ConcurrentHashMap<byte[], AtomicLong>();
    final private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private static   ServerFlushHandler flushHandler = new ServerFlushHandler() {
        @Override
        public void handle(Map<byte[], Long> flushedData) {

        }
    };
     //private static ServerCounter counter= new ServerCounter(4,flushHandler);
     private static ServerCounter counter= new ServerCounter();



    public void update(long timestamp) {
        log.info("inside update key");
        byte[] key = Bytes.add(Bytes.toBytes("IN"), Bytes.toBytes(timestamp));

        //counter.increment(key);

    }

    public  void totalCamapignResponseTime(byte[] key,long time){
        counter.incrementBidResponseCounter(key,time);
    }

    public  void totalCamapignBids(Long key){
        counter.incrementBidCounter(key);
    }
   public    ConcurrentHashMap<byte[], AtomicLong> getCampaignResponseTime(){

      return  counter.getTotalBidResponseTimeSeconds();
 }
    public  ConcurrentHashMap<Long, AtomicLong> getBidRequest(){

        return counter.getTotalBid();
    }


}
