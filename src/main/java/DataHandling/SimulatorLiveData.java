package DataHandling;

import java.sql.Time;
import java.time.LocalTime;

public class SimulatorLiveData implements Runnable {

    private LiveData liveData;

    private LocalTime startTime;
    private long startMs;

    public SimulatorLiveData(LiveData liveData) {
        this.liveData = liveData;
    }

    @Override
    public void run() {
        int idx = 1;

        this.startMs = System.currentTimeMillis();
        this.startTime = new Time(startMs).toLocalTime();

        liveData.setStartTime(startTime);

        while (true){
            liveData.setDailyPNL((int) (Math.random()*200)-100);
            liveData.setUnrealizedPNL((int) (Math.random()*200)-100);
            liveData.setRealizedPNL((int) (Math.random()*200)-100);
            liveData.setSumOrder(idx/10);
            liveData.setSumAnalysis(idx);
            liveData.setCompellingAnalysisPercentage((int) (Math.random()*100));

            liveData.setAnalysisTime((int) (Math.random()*1000));
            liveData.setAverageAnalysisTime((int) (Math.random()*800));
            liveData.setNextAnalysis((int) (Math.random()*500));
            liveData.setTimeSinceStart(new Time(System.currentTimeMillis()-startMs-(7200*1000)).toLocalTime());

            liveData.setTimeToStart(new Time(System.currentTimeMillis()-startMs-(7200*1000)).toLocalTime());
            liveData.setInitCompleted(true);
            liveData.setStartPrice(idx);
            liveData.setCurrentPrice(idx);
            liveData.setAveragePrice(idx);

            liveData.notifyObservers();
            idx++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
