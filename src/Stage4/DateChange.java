package Stage4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DateChange {

    private String token;
    private String apiURL;


    public DateChange(String token, String apiURL) {
        this.apiURL = apiURL;
        this.token = token;
    }
    public String getToken() {
        return this.token;
    }
    public JsonObject makeHTTPPOSTRequest() {

        try {
            HttpClient c = new DefaultHttpClient();
            HttpPost p = new HttpPost(this.apiURL);

            p.setEntity(new StringEntity("{\"token\":\"" + this.token + "\"}",
                    ContentType.create("application/json")));

            HttpResponse r = c.execute(p);

            BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));

            String json = rd.readLine();
            //System.out.println(json);
            JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
            JsonObject result = jobj.get("result").getAsJsonObject();
            return result;
        }
        catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }
    public String DateFind(JsonObject result) {
        String calendar = result.get("datestamp").getAsString();
        return calendar;
    }
    public int getSeconds(JsonObject result) {
        int seconds = result.get("interval").getAsInt();
        return seconds;
    }

    public DateTime changeDate(DateTime date, int interval) {
        date = date.plusSeconds(interval);
        return date;
    }
    public DateTime dateConversion(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DateTime dateconverted = formatter.withZoneUTC().parseDateTime(date);
        return dateconverted;
    }
    public static void main(String[] args) {
        DateChange test = new DateChange("p56RSy3nXe", "http://challenge.code2040.org/api/time");
        JsonObject result = test.makeHTTPPOSTRequest();
        String jdate= test.DateFind(result);
        DateTime date = test.dateConversion(jdate);
        int interval = test.getSeconds(result);
        date = test.changeDate(date, interval);
        System.out.println(jdate);
        String back = date.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z"));
        System.out.println(date);
        System.out.println(back);





    }

}
