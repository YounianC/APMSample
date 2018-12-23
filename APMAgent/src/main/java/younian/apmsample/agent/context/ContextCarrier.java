package younian.apmsample.agent.context;

import java.io.Serializable;

public class ContextCarrier implements Serializable {
    private static final long serialVersionUID = -3482243375970509251L;

    private String traceId;
    private int spanId;

    public String serialize() {
        return StringUtils.join('|',
                this.getTraceId(),
                this.getSpanId() + "");
    }

    public ContextCarrier deserialize(String text) {
        if (text != null) {
            String[] arr = text.split("\\|", 2);
            if (arr.length == 2) {
                this.setTraceId(arr[0]);
                this.setSpanId(Integer.parseInt(arr[1]));
            }
        }
        return this;
    }

    public int getSpanId() {
        return spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
