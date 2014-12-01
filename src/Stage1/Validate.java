package Stage1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Validate {
        private String token;
        private String string;
        private String apiURL;


        public Validate(String token, String string, String apiURL) {
            this.apiURL = apiURL;
            this.token = token;
            this.string = string;
        }

        public String makeHTTPPOSTRequest() {
            String line = null;
            try {
                HttpClient c = new DefaultHttpClient();
                HttpPost p = new HttpPost(this.apiURL);

                p.setEntity(new StringEntity("{\"token\":\"" + this.token + "\",\"string\":\"" + this.string + "\"}",
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
            Reverse reversal = new Reverse("p56RSy3nXe","http://challenge.code2040.org/api/getstring");
            String value = reversal.makeHTTPPOSTRequest();
            String reversed = reversal.reverse(value);
            Validate valid = new Validate(reversal.getToken(),reversed,"http://challenge.code2040.org/api/validatestring");
            String result = valid.makeHTTPPOSTRequest();
            System.out.println(result);

        }



}
