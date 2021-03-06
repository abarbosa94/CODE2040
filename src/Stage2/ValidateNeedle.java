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
import com.google.gson.JsonObject;

public class ValidateNeedle {
    private String token;
    private int index;
    private String apiURL;


    public ValidateNeedle(String token, int index, String apiURL) {
        this.apiURL = apiURL;
        this.token = token;
        this.index = index;
    }

    public String makeHTTPPOSTRequest() {
        String line = null;
        try {
            HttpClient c = new DefaultHttpClient();
            HttpPost p = new HttpPost(this.apiURL);
            //System.out.println("{\"token\":\"" + this.token + "\",\"needle\":\"" + this.index + "\"}");
            p.setEntity(new StringEntity("{\"token\":\"" + this.token + "\",\"needle\":\"" + this.index + "\"}",
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
        Haystack haystack = new Haystack("p56RSy3nXe","http://challenge.code2040.org/api/haystack");
        String[] values = haystack.makeHTTPPOSTRequest();
        int index = haystack.search(values, values[values.length-1]);
        ValidateNeedle valid = new ValidateNeedle(haystack.getToken(),index,"http://challenge.code2040.org/api/validateneedle");
        String result = valid.makeHTTPPOSTRequest();
        System.out.println(result);

    }



}
