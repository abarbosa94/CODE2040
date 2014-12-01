package Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class CheckStatus {

    private String token;
    private String apiURL;

    public CheckStatus(String token, String apiURL) {
        this.apiURL = apiURL;
        this.token = token;
    }

    public void makeHTTPPOSTRequest() {
        try {
            HttpClient c = new DefaultHttpClient();
            HttpPost p = new HttpPost(this.apiURL);

            p.setEntity(new StringEntity("{\"token\":\"" + this.token + "\"}",
                    ContentType.create("application/json")));

            HttpResponse r = c.execute(p);

            BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
            String line = rd.readLine();

            System.out.println(line);

        }

        catch(IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        CheckStatus check = new CheckStatus("p56RSy3nXe","http://challenge.code2040.org/api/status");
        check.makeHTTPPOSTRequest();

    }


}