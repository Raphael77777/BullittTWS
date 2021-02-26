package TechnicalAnalysisHandling;

import DataHandling.AlphaVantageData;
import UserInterface.Screen.Observer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class AlphaVantageAdapter implements Observer {

    private AlphaVantageData alphaVantageData;

    private static String API_KEY_1 = "";
    private static String API_KEY_2 = "";

    public AlphaVantageAdapter(AlphaVantageData alphaVantageData) {
        this.alphaVantageData = alphaVantageData;
        this.alphaVantageData.registerObserver(this);

        update();
    }

    public static double SMA(String symbol, String interval, String time_period, String series_type) throws IOException {

        URL url = new URL("https://www.alphavantage.co/query?function=SMA&symbol="+symbol+"&interval="+interval+"&time_period="+time_period+"&series_type="+series_type+"&apikey="+API_KEY_1);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responsecode = conn.getResponseCode();

        if(responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " +responsecode);
        else
        {
            Scanner sc = new Scanner(url.openStream());
            String inline = "";
            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            sc.close();
            conn.disconnect();

            JSONParser jsonParser = new JSONParser();

            try {
                Object obj = jsonParser.parse(inline);

                String pattern = "yyyy-MM-dd HH:mm";
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("fr", "FR"));
                String date = simpleDateFormat.format(new Date(System.currentTimeMillis()-3600*1000*7-60000));

                System.out.println(obj);

                JSONObject skr = (JSONObject) obj;
                Map analysis = ((Map) skr.get("Technical Analysis: SMA"));
                Iterator<Map.Entry> itr1 = analysis.entrySet().iterator();
                while (itr1.hasNext()) {
                    Map.Entry pair = itr1.next();
                    if (pair.getKey().toString().equals(date)){

                        JSONObject sma = (JSONObject) pair.getValue();
                        String MA = (String) sma.get("SMA");
                        return Double.parseDouble(MA);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Limit reach");
            }
        }
        return 0.0d;
    }
    public static double RSI(String symbol, String interval, String time_period, String series_type) throws IOException {

        URL url = new URL("https://www.alphavantage.co/query?function=RSI&symbol="+symbol+"&interval="+interval+"&time_period="+time_period+"&series_type="+series_type+"&apikey="+API_KEY_2);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responsecode = conn.getResponseCode();

        if(responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " +responsecode);
        else
        {
            Scanner sc = new Scanner(url.openStream());
            String inline = "";
            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            sc.close();
            conn.disconnect();

            JSONParser jsonParser = new JSONParser();

            try {
                Object obj = jsonParser.parse(inline);

                String pattern = "yyyy-MM-dd HH:mm";
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("fr", "FR"));
                String date = simpleDateFormat.format(new Date(System.currentTimeMillis()-3600*1000*7-60000));

                System.out.println(obj);

                JSONObject skr = (JSONObject) obj;
                Map analysis = ((Map) skr.get("Technical Analysis: RSI"));
                Iterator<Map.Entry> itr1 = analysis.entrySet().iterator();
                while (itr1.hasNext()) {
                    Map.Entry pair = itr1.next();
                    if (pair.getKey().toString().equals(date)){

                        JSONObject sma = (JSONObject) pair.getValue();
                        String MA = (String) sma.get("RSI");
                        return Double.parseDouble(MA);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Limit reach");
            }
        }
        return 0.0d;
    }

    @Override
    public void update() {
        API_KEY_1 = alphaVantageData.getAPI_KEY_1();
        API_KEY_2 = alphaVantageData.getAPI_KEY_2();
    }
}
