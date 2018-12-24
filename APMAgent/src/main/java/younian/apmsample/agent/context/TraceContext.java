package younian.apmsample.agent.context;

import java.util.LinkedList;

public class TraceContext {

    private LinkedList<Span> activeSpanStak = new LinkedList<>();
    private int spanIdGenerator = 1;

    public Span createSpan(String type, String operationName) {
        return createSpan(null, type, operationName);
    }

    public Span createSpan(ContextCarrier contextCarrier, String type, String operationName) {
        Span parentSpan = peek();
        Span span = null;
        if (parentSpan == null) {
            if (contextCarrier != null) {
                //跨应用
                spanIdGenerator = contextCarrier.getSpanId() + 1;
                span = new Span(type, contextCarrier.getTraceId(), spanIdGenerator++, contextCarrier.getSpanId(), System.currentTimeMillis(), operationName);
            } else {
                //调用链的第一个span
                String traceId = GlobalIdGenerator.generate("tomcat");
                span = new Span(type, traceId, spanIdGenerator++, System.currentTimeMillis(), operationName);
            }
        } else {
            //同应用跨组件
            span = new Span(type, parentSpan.getTraceId(), spanIdGenerator++, parentSpan.getSpanId(), System.currentTimeMillis(), operationName);
        }

        span.setThreadName(Thread.currentThread().getName());
        push(span);
        return span;
    }

    public Span stopSpan() {
        Span span = pop();
        span.setEndTime(System.currentTimeMillis());
        return span;
    }

    public void inject(ContextCarrier contextCarrier) {
        Span parentSpan = peek();
        contextCarrier.setSpanId(parentSpan.getSpanId());
        contextCarrier.setTraceId(parentSpan.getTraceId());
    }

    public void push(Span span) {
        activeSpanStak.push(span);
    }

    public Span pop() {
        return activeSpanStak.pop();
    }

    public Span peek() {
        return activeSpanStak.peek();
    }
}
