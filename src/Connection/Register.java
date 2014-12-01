package Connection;

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


/**
 *
 * @author Andre Barbosa
 */
public class Register {

    private String email;
    private String github;
    private String apiURL;

    public Register(String email, String github, String apiURL) {
        this.apiURL = apiURL;
        this.email = email;
        this.github = github;
    }

    public void makeHTTPPOSTRequest() {
        try {
            HttpClient c = new DefaultHttpClient();
            HttpPost p = new HttpPost(this.apiURL);

            p.setEntity(new StringEntity("{\"email\":\"" + this.email + "\",\"github\":\"" + this.github + "\"}",
                             ContentType.create("application/json")));

            HttpResponse r = c.execute(p);

            BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
            String json = null;
            String line = rd.readLine();
            JsonObject jobj = new Gson().fromJson(line, JsonObject.class);
            json = jobj.get("result").getAsString();

            System.out.println(json);

        }

        catch(IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        Register member = new Register("abarbosa0494@gmail.com","https://github.com/abarbosa94/CODE2040","http://challenge.code2040.org/api/register");
        member.makeHTTPPOSTRequest();

    }
}