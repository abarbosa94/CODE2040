package Stage3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Prefix {

        private String token;
        private String apiURL;


        public Prefix(String token, String apiURL) {
            this.apiURL = apiURL;
            this.token = token;
        }
        public String getToken() {
            return this.token;
        }
        public List<String> makeHTTPPOSTRequest() {
            String prefix = null;
            List<String> array = new ArrayList<String>();
            try {
                HttpClient c = new DefaultHttpClient();
                HttpPost p = new HttpPost(this.apiURL);

                p.setEntity(new StringEntity("{\"token\":\"" + this.token + "\"}",
                        ContentType.create("application/json")));

                HttpResponse r = c.execute(p);

                BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
                String json = "";
                if((json = rd.readLine()) != null) {
                JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
                //System.out.println(jobj);
                JsonObject result = jobj.get("result").getAsJsonObject();
                JsonArray arrayj = (JsonArray) result.get("array");
                prefix = result.get("prefix").getAsString();
                for(int i = 0; i<arrayj.size();i++) {
                    JsonElement element = arrayj.get(i);
                    String temp = element.getAsString();
                    String substring = temp.substring(0, prefix.length());
                    if(!substring.equals(prefix)) {
                        array.add(temp);
                    }

                }
                }

            }
            catch(IOException e) {
                System.out.println(e);
            }
            return array;
        }

        public static void main(String[] args) {
            Prefix prefix = new Prefix("p56RSy3nXe","http://challenge.code2040.org/api/prefix");
            List<String> values = prefix.makeHTTPPOSTRequest();

        }
}
