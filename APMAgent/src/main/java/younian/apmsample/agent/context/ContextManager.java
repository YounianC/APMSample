package younian.apmsample.agent.context;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

public class ContextManager {
    private final static ThreadLocal<TraceContext> CONTEXT = new ThreadLocal<>();

    public static TraceContext getOrCreate() {
        if (get() == null) {
            CONTEXT.set(new TraceContext());
        }
        return get();
    }

    public static Span createSpan(String type, String operationName) {
        return get().createSpan(type, operationName);
    }

    public static Span createSpan(ContextCarrier contextCarrier, String type, String operationName) {
        return get().createSpan(contextCarrier, type, operationName);
    }

    public static Span stopSpan() {
        final Span span = get().stopSpan();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost("http://localhost:8888/uploadSpan");
                    httpPost.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
                    List<BasicNameValuePair> formparams = new ArrayList<>();
                    formparams.add(new BasicNameValuePair("span", span.toString()));
                    UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                    httpPost.setEntity(uefEntity);
                    httpclient.execute(httpPost);
                } catch (Exception e) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        return span;
    }

    public static TraceContext get() {
        return CONTEXT.get();
    }

    public static void inject(ContextCarrier contextCarrier) {
        get().inject(contextCarrier);
    }
}
