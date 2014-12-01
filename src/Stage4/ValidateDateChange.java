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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ValidateDateChange {
        private String token;
        private String date;
        private String apiURL;


        public ValidateDateChange(String token, String date, String apiURL) {
            this.apiURL = apiURL;
            this.token = token;
            this.date = date;
        }

        public String makeHTTPPOSTRequest() {
            String line = null;
            try {
                HttpClient c = new DefaultHttpClient();
                HttpPost p = new HttpPost(this.apiURL);
                Gson gson = new Gson();
                String jsona = gson.toJson(date);
                p.setEntity(new StringEntity("{\"token\":\"" + this.token + "\",\"datestamp\":\"" + this.date + "\"}",
                                 ContentType.create("application/json")));



                HttpResponse r = c.execute(p);

                BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
                String json = rd.readLine();
                JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
                line = jobj.get("result").getAsString();
            }
            catch(IOException e) {
                System.out.println(e);
            }
            return line;
        }

        public static void main(String[] args) {
            DateChange date = new DateChange("p56RSy3nXe","http://challenge.code2040.org/api/time");
            JsonObject result = date.makeHTTPPOSTRequest();
            String datechange = date.DateFind(result);
            DateTime converted = date.dateConversion(datechange);
            int interval = date.getSeconds(result);
            converted = date.changeDate(converted, interval);
            String back = converted.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z"));
            ValidateDateChange valid = new ValidateDateChange(date.getToken(),back,"http://challenge.code2040.org/api/validatetime");
            String found = valid.makeHTTPPOSTRequest();
            System.out.println(found);

        }
}
