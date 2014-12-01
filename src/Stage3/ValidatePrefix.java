package Stage3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ValidatePrefix {
    private String token;
    private List<String> array;
    private String apiURL;


    public ValidatePrefix(String token, List<String> array, String apiURL) {
        this.apiURL = apiURL;
        this.token = token;
        this.array = array;
    }

    public String makeHTTPPOSTRequest() {
        String line = null;
        try {
            HttpClient c = new DefaultHttpClient();
            HttpPost p = new HttpPost(this.apiURL);
            Gson gson = new Gson();
            String jsona = gson.toJson(array);
            p.setEntity(new StringEntity("{\"token\":\"" + this.token + "\",\"array\":" + jsona + "}",
                             ContentType.create("application/json")));



            HttpResponse r = c.execute(p);

            BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
            String json = rd.readLine();
            System.out.println(json);
            JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
            line = jobj.get("result").getAsString();
        }
        catch(IOException e) {
            System.out.println(e);
        }
        return line;
    }

    public static void main(String[] args) {
        Prefix prefix = new Prefix("p56RSy3nXe","http://challenge.code2040.org/api/prefix");
        List<String> array = prefix.makeHTTPPOSTRequest();
        ValidatePrefix valid = new ValidatePrefix(prefix.getToken(),array,"http://challenge.code2040.org/api/validateprefix");
        String result = valid.makeHTTPPOSTRequest();
        //System.out.println(result);

    }

}
