package younian.apmsample.agent.context;

import java.util.LinkedList;

public class TraceContext {

    private LinkedList<Span> activeSpanStak = new LinkedList<>();
    private int spanIdGenerator = 1;

    public Span createSpan(String operationName) {
        return createSpan(null, operationName);
    }

    public Span createSpan(ContextCarrier contextCarrier, String operationName) {
        Span parentSpan = peek();
        Span span = null;
        if (parentSpan == null) {
            if (contextCarrier != null) {
                span = new Span(spanIdGenerator++, contextCarrier.getSpanId(), System.currentTimeMillis(), operationName);
            } else {
                span = new Span(spanIdGenerator++, System.currentTimeMillis(), operationName);
            }
        } else {
            span = new Span(spanIdGenerator++, parentSpan.getSpanId(), System.currentTimeMillis(), operationName);
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
