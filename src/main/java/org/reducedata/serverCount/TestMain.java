package org.reducedata.serverCount;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by prashanth on 1/21/15.
 */
public class TestMain {
    public  static  void  main(String[] args){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
       // System.out.println(dateFormat.format(date)); //2013/10/15 16:16:39
        Timestamp timestamp = new Timestamp(1421805832000L);

        System.out.println(dateFormat.format(timestamp));
        System.out.println(dateFormat.format(date));
        //long timestamp =1421799711; //Example -> in ms
        /*Date d = new Date(timestamp);
        System.out.println(d);
*/


       /* String messag= "69.36.36.60-1421796057|nobid response for prefiltering|DSPController";*/
        String messag= "69.36.36.60-1421796057";
        String[] message = messag.split("\\-");


        /*Timestamp timestamp = new Timestamp(Long.parseLong(message[1]+"000"));
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        System.out.println(dateFormat.format(timestamp));
        System.out.println(dateFormat.format(date));*/
        /*for(int i=0;i<message.length;i++){
            System.out.println("messgae array message[" + i + "]" + message[i]);
        }*/
    }
}
