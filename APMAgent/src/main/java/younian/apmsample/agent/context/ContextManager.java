package younian.apmsample.agent.context;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ContextManager {
    private final static ThreadLocal<TraceContext> CONTEXT = new ThreadLocal<>();

    public static Span createSpan(String operationName){
        return get().createSpan(operationName);
    }

    public static Span stopSpan(){
        Span span = get().stopSpan();

        try {
            CloseableHttpClient httpclient  = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8888/uploadSpan");
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
            List<BasicNameValuePair> formparams = new ArrayList<>();
            formparams.add(new BasicNameValuePair("span", span.toString()));
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(uefEntity);
            httpclient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return span;
    }

    public static TraceContext get(){
        return CONTEXT.get();
    }
}
