package TechnicalAnalysisHandling;

import CustomException.MarketClosedException;
import CustomException.MissingApiKeyException;
import CustomException.NoNetworkException;
import CustomException.OverloadApiUseException;
import DataHandling.AlphaVantageData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdapterRSI extends AlphaVantageAdapter {

    public AdapterRSI(AlphaVantageData alphaVantageData) {
        super(alphaVantageData);
    }

    public double get(String symbol, String interval, String time_period, String series_type) throws MarketClosedException, OverloadApiUseException, MissingApiKeyException, NoNetworkException {

        if (API_KEY == null || API_KEY.length() != 16){
            throw new MissingApiKeyException(); // APIs KEY IS NOT AVAILABLE OR WRONG
        }

        try {
            URL url = new URL("https://www.alphavantage.co/query?function=RSI&symbol=" + symbol + "&interval=" + interval + "&time_period=" + time_period + "&series_type=" + series_type + "&apikey=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                Scanner sc = new Scanner(url.openStream());
                String inline = "";
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();
                conn.disconnect();

                JSONParser jsonParser = new JSONParser();

                try {
                    Object obj = jsonParser.parse(inline);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("fr", "FR"));
                    String date = simpleDateFormat.format(new Date(System.currentTimeMillis() - 3600 * 1000 * 7 - 60000));

                    JSONObject skr = (JSONObject) obj;
                    Map analysis = ((Map) skr.get("Technical Analysis: RSI"));
                    Iterator<Map.Entry> itr1 = analysis.entrySet().iterator();
                    while (itr1.hasNext()) {
                        Map.Entry pair = itr1.next();
                        if (pair.getKey().toString().equals(date)) {
                            JSONObject val = (JSONObject) pair.getValue();
                            return Double.parseDouble((String) val.get("RSI"));
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    throw new OverloadApiUseException(); //LIMIT APIs CALL REACH
                }
            }
        } catch (UnknownHostException u) {
                throw new NoNetworkException(); //NO NETWORK AVAILABLE
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new MarketClosedException(); //MARKET CLOSED
    }
}
