package StorageHandling;

import java.sql.Time;

public class ChronoLiveData implements Runnable {

    /* DATA SUBJECT */
    private final LiveData liveData;

    /* CHRONO STATUS */
    private volatile boolean exit = false;

    public ChronoLiveData(LiveData liveData) {
        this.liveData = liveData;
    }

    @Override
    public void run() {

        long startMs = System.currentTimeMillis();

        while (!exit){
            /* UPDATE TIME SINCE START EACH SECOND */
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
