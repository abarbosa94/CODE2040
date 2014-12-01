package Stage2;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Haystack {

    private String token;
    private String apiURL;


    public Haystack(String token, String apiURL) {
        this.apiURL = apiURL;
        this.token = token;
    }
    public String getToken() {
        return this.token;
    }
    public String[] makeHTTPPOSTRequest() {
        String needle = null;
        String[] haystack = null;
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
            JsonArray array = (JsonArray) result.get("haystack");
            needle = result.get("needle").getAsString();
            //System.out.println(array);
            haystack = new String[array.size()+1];
            for(int i = 0; i<array.size();i++) {
                JsonElement element = array.get(i);
                haystack[i] = element.getAsString();
                //System.out.println(i);
                //System.out.println(haystack[i]);
            }
            haystack[array.size()] = needle; // the last element of the array is the needle that I want
            }

        }
        catch(IOException e) {
            System.out.println(e);
        }
        return haystack;
    }

    public int search(String [] values, String needle) {
        for(int i = 0; i< values.length-1; i++) {
            if(values[i].equals(needle)) return i;
        }
        return -1;
    }
    public static void main(String[] args) {
        Haystack haystack = new Haystack("p56RSy3nXe","http://challenge.code2040.org/api/haystack");
        String[] values = haystack.makeHTTPPOSTRequest();
        int index = haystack.search(values, values[values.length-1]);
        String Index = String.valueOf(index);
        System.out.println(Index);

    }

}
