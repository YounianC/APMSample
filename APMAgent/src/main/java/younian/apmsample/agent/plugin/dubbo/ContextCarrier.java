package younian.apmsample.agent.plugin.dubbo;

import java.io.Serializable;

public class ContextCarrier implements Serializable {

    private static final long serialVersionUID = -3482243375970509251L;

    private int spanId;

    public String serialize() {
        return this.getSpanId() + "";
    }

    public ContextCarrier deserialize(String text) {
        if (text != null) {
            this.setSpanId(Integer.parseInt(text));
        }
        return this;
    }

    public int getSpanId() {
        return spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }
}
