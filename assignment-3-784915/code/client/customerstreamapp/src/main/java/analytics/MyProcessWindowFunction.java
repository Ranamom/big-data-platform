package analytics;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;


public class MyProcessWindowFunction extends ProcessWindowFunction<BTSEvent, String, String, TimeWindow> {
    
    // Simple function to detect a sequence of alarms in a round
    @Override
    public void process(String station_id, Context context, Iterable<BTSEvent> records, Collector<String> out) {
        // Define a simple analytics is that in a windows if an alarm happens N times (true) then we should send an alert.
        int number_active_threshold = 5; //for study purpose
        int count = 0;
        for (BTSEvent btsrecord: records) {
            if (btsrecord.isActive) {
                count++;
            }   
        }
        if (count > number_active_threshold) {
            out.collect (new BTSAlert(station_id).alarmMessage());
        }
    }
}
