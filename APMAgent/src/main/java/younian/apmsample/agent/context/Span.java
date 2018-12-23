package younian.apmsample.agent.context;

public class Span {
    private String traceId;
    private int spanId;
    private int parentSpanId;
    private long startTime;
    private long endTime;
    private String operationName;
    private String threadName;

    public Span(String traceId, int spanId, long startTime, String operationName) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.operationName = operationName;
    }

    public Span(String traceId, int spanId, int parentSpanId, long startTime, String operationName) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.operationName = operationName;
    }

    public int getSpanId() {
        return spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }

    public int getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(int parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "{\"spanId\":" + spanId +
                ",\"parentSpanId\":" + parentSpanId +
                ",\"startTime\":" + startTime +
                ",\"endTime\":" + endTime +
                ",\"threadName\":\"" + threadName + "\"" +
                ",\"operationName\":\"" + operationName + "\"" +
                ",\"traceId\":\"" + traceId + "\"" +
                "}";
    }
}
