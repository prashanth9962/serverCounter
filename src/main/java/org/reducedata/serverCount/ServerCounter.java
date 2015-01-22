package org.reducedata.serverCount;


import com.google.common.util.concurrent.AtomicLongMap;
import org.apache.hadoop.hbase.util.Bytes;
import org.reducedata.serverCount.ServerFlushHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by prashanth on 1/13/15.
 */
public class ServerCounter {

    final private  org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    final private  static  ConcurrentHashMap<byte[], AtomicLong> totalBidResponseTimeSeconds = new ConcurrentHashMap<byte[], AtomicLong>();
    final private  static  ConcurrentHashMap<Long, AtomicLong> totalBid = new ConcurrentHashMap<Long, AtomicLong>();
    /*final private  static  ConcurrentHashMap<byte[], AtomicLongMap> totalBidResponseTimeSeconds = new ConcurrentHashMap<byte[], AtomicLongMap>();*/
    final public static ConcurrentHashMap<byte[], AtomicLong> totalBidCountSeconds=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> totalCampaignBidPriceCounter=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> totalCampaignWinPriceCounter=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> totalCampaignBidCounter=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> totalCampaignWinCounter=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> countryCampaignHourCounter=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> countrySizeHourCounter=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> countrySspHourCounter=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> countryDeviceHourCounter=new ConcurrentHashMap<byte[], AtomicLong>();
    final public static ConcurrentHashMap<byte[], AtomicLong> countryOsHourCounter=new ConcurrentHashMap<byte[], AtomicLong>();

    /*final private ServerFlushHandler flushHandler;*/

    final private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

 /*   public ServerCounter(int flushSeconds, ServerFlushHandler flushHandler) {

        this.flushHandler = flushHandler;

        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                //log.info("Starting flush due to flushSeconds");
                try {
                    flush();
                } catch (Exception e) {
                   // log.info("Error during flush", e);
                }
            }
        }, flushSeconds, flushSeconds, TimeUnit.SECONDS);
    }

    public void increment(byte[] key) {

            if (totalBidResponseTimeSeconds.get(key)== null) {

                synchronized (this) {
                    log.info("inside total bid response time synchronized");
                    totalBidResponseTimeSeconds.put(key, new AtomicLong());
                }
            }
            log.info("inside response time get key");
            totalBidResponseTimeSeconds.get(key).addAndGet(1L);


    }

    private synchronized void flush() {
        Map<byte[], Long> flushedCounts = new HashMap<byte[], Long>();
        for (Map.Entry<byte[], AtomicLong> entry : totalBidResponseTimeSeconds.entrySet()) {
            byte[] key = entry.getKey();
            AtomicLong value = entry.getValue();
            long flushedCount = value.get();
            if (flushedCount > 0) {
                flushedCounts.put(key, flushedCount);
            }
            value.getAndAdd(-flushedCount);
        }
        flushHandler.handle(flushedCounts);
        try {
            removeOlderEntries();
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Error removing older entries");
            }
        }
    }

    private void removeOlderEntries() {
        List<byte[]> keysToRemove = new ArrayList<byte[]>();

        for (Map.Entry<byte[], AtomicLong> entry : totalBidResponseTimeSeconds.entrySet()) {
            byte[] key = entry.getKey();
            int today = Integer.MAX_VALUE - (int) TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());

            //int keyDay = Integer.parseInt(key.split(Pattern.quote("|"))[0]);
           *//* if (keyDay != today) {
                keysToRemove.add(key);
            }*//*

            keysToRemove.add(key);
        }

        for (byte[] keyToRemove : keysToRemove) {
            totalBidResponseTimeSeconds.remove(keyToRemove);
        }
    }

    public Map<byte[], AtomicLong> getCountMap() {
        log.info("inside get count map");
        totalBidResponseTimeSeconds.put(Bytes.toBytes("123"),12L);
        log.info(totalBidResponseTimeSeconds.toString());
        return totalBidResponseTimeSeconds;
    }*/



    public void incrementBidResponseCounter(byte[] key,long time) {
        try {
            if (totalBidResponseTimeSeconds.get(key) == null) {
                synchronized (this) {
                    totalBidResponseTimeSeconds.put(key, new AtomicLong());
                }
            }
            totalBidResponseTimeSeconds.get(key).addAndGet(time);
        } catch (Exception e){
            log.debug(e.getMessage());
        }
    }

    public  ConcurrentHashMap<byte[], AtomicLong> getTotalBidResponseTimeSeconds() {
        return totalBidResponseTimeSeconds;
    }

    public ConcurrentHashMap<Long, AtomicLong> getTotalBid() {
        return totalBid;
    }

    public void incrementBidCounter(Long key) {

        System.out.println("row key increment"+key);
        try {
            if (totalBid.get(key) == null) {
                System.out.println("inside total bid increment");
                synchronized (this) {
                    System.out.println("inside synchronized");
                    totalBid.put(key, new AtomicLong());
                }
            }

            totalBid.get(key).incrementAndGet();
        } catch (Exception e){
            log.debug(e.getMessage());
        }
    }

}
