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

public class Reverse {
    private String token;
    private String apiURL;

    public String getToken() {
        return this.token;
    }
    public Reverse(String token, String apiURL) {
        this.apiURL = apiURL;
        this.token = token;
    }

    public String makeHTTPPOSTRequest() {
        String line = null;
        try {
            HttpClient c = new DefaultHttpClient();
            HttpPost p = new HttpPost(this.apiURL);

            p.setEntity(new StringEntity("{\"token\":\"" + this.token + "\"}",
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

    public String reverse(String string) {
        String reversed = new StringBuffer(string).reverse().toString();
        return reversed;
    }
    public static void main(String[] args) {
        Reverse reversal = new Reverse("p56RSy3nXe","http://challenge.code2040.org/api/getstring");
        String value = reversal.makeHTTPPOSTRequest();
        System.out.println(value);
        String reversed = reversal.reverse(value);
        System.out.println(reversed);

    }
}
