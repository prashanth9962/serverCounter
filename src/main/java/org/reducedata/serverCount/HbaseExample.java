package org.reducedata.serverCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by prashanth on 1/21/15.
 */
public class HbaseExample {
    final  private  static  String tableName="error_report";
    final  private  static  String hbaseZookeeperQuorum="127.0.0.1";
    public static void writeHbase() {
        try {

            //String[] dateKey= key.split("\\-");
            //Timestamp timestamp = new Timestamp(Long.parseLong(dateKey[1]));
            //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            // String rowKey = dateKey[0]+"-"+dateFormat.format(timestamp);

			/*log.info("time stamp rowkey " + dateFormat.format(timestamp));
			log.info("rowkey  " + rowKey);*/

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Configuration hConf = HBaseConfiguration.create();
            hConf.set(HConstants.ZOOKEEPER_QUORUM, hbaseZookeeperQuorum);
            HTable table = new HTable(hConf, tableName);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            //table.getTableDescriptor().hasFamily(Bytes.toBytes("create_table"));

            //create hbase table
            HBaseAdmin admin = new HBaseAdmin(hConf);
            HColumnDescriptor create_table = new HColumnDescriptor("create_table");
            admin.addColumn(tableName, create_table);

            Put row1 = new Put(Bytes.toBytes(dateFormat.format(timestamp)));
            row1.add(Bytes.toBytes("create_table"), Bytes.toBytes("exception"), Bytes.toBytes(Math.random()*100));

            table.put(row1);
        } catch (Exception e) {
            System.out.println("exception in error report Table ");
		/*	e.printStackTrace();*/
        }
    }
    public static  void  run(){
        writeHbase();
    }
}
