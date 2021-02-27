package DataHandling;

import java.sql.Time;
import java.time.LocalTime;

public class SimulatorLiveData implements Runnable {

    private LiveData liveData;

    private LocalTime startTime;
    private LocalTime stopTime;
    private long startMs;
    private long stopMs;

    public SimulatorLiveData(LiveData liveData) {
        this.liveData = liveData;
    }

    @Override
    public void run() {
        int idx = 1;

        this.startMs = System.currentTimeMillis();
        this.stopMs = System.currentTimeMillis()+(7200*1000);
        this.startTime = new Time(startMs).toLocalTime();
        this.stopTime = new Time(stopMs).toLocalTime();

        liveData.setStartTime(startTime);
        liveData.setAutoStopTime(stopTime);

        while (true){
            liveData.setProfitLoss((int) (Math.random()*200)-100);
            liveData.setProfitLossPercentage((int) (Math.random()*200)-100);
            liveData.setSumOrder(idx/10);
            liveData.setSumAnalysis(idx);
            liveData.setCompellingAnalysisPercentage((int) (Math.random()*100));

            liveData.setAnalysisTime((int) (Math.random()*1000));
            liveData.setAverageAnalysisTime((int) (Math.random()*800));
            liveData.setNextAnalysis((int) (Math.random()*500));
            liveData.setTimeSinceStart(new Time(System.currentTimeMillis()-startMs-(7200*1000)).toLocalTime());

            liveData.setTimeToAutoStop(new Time(stopMs-System.currentTimeMillis()-(7200*1000)).toLocalTime());
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
