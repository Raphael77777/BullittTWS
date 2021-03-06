package DataHandling;

import java.sql.Time;
import java.time.LocalTime;

public class ChronoLiveData implements Runnable {

    private final LiveData liveData;
    private volatile boolean exit = false;

    public ChronoLiveData(LiveData liveData) {
        this.liveData = liveData;
    }

    @Override
    public void run() {

        long startMs = System.currentTimeMillis();

        while (!exit){
            liveData.setTimeSinceStart(new Time(System.currentTimeMillis()- startMs -(7200*1000)).toLocalTime());
            liveData.update();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        exit = true;
    }
}
