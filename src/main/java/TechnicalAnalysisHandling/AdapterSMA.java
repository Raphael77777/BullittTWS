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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class AdapterSMA extends AlphaVantageAdapter {

    public AdapterSMA(AlphaVantageData alphaVantageData) {
        super(alphaVantageData);
    }

    public double get(String symbol, String interval, String time_period, String series_type) throws MarketClosedException, OverloadApiUseException, MissingApiKeyException, NoNetworkException {

        if (API_KEY == null || API_KEY.length() != 16){
            throw new MissingApiKeyException(); // APIs KEY IS NOT AVAILABLE OR WRONG
        }

        try {
            URL url = new URL("https://www.alphavantage.co/query?function=SMA&symbol=" + symbol + "&interval=" + interval + "&time_period=" + time_period + "&series_type=" + series_type + "&apikey=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                Scanner sc = new Scanner(url.openStream());
                StringBuilder inline = new StringBuilder();
                while (sc.hasNext()) {
                    inline.append(sc.nextLine());
                }
                sc.close();
                conn.disconnect();

                JSONParser jsonParser = new JSONParser();

                try {
                    Object obj = jsonParser.parse(inline.toString());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime localDate = null;
                    double SMA = 0.0;

                    JSONObject skr = (JSONObject) obj;
                    Map analysis = ((Map) skr.get("Technical Analysis: SMA"));
                    for (Map.Entry pair : (Iterable<Map.Entry>) analysis.entrySet()) {
                        LocalDateTime tempLocalDate = LocalDateTime.parse(pair.getKey().toString(), formatter);

                        if (localDate == null) {
                            localDate = tempLocalDate;
                            SMA = Double.parseDouble((String) ((JSONObject) pair.getValue()).get("SMA"));
                        }

                        if (tempLocalDate.isAfter(localDate)) {
                            localDate = tempLocalDate;
                            SMA = Double.parseDouble((String) ((JSONObject) pair.getValue()).get("SMA"));
                        }
                    }
                    return SMA;
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
