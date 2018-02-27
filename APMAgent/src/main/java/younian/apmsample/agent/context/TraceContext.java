package younian.apmsample.agent.context;

import java.util.LinkedList;

public class TraceContext {

    private static LinkedList<Span> activeSpanStak = new LinkedList<>();

    private static int spanIdGenerator;

    static Span createSpan(String operationName) {
        Span parentSpan =  peek();
        Span span = null;
        if (parentSpan == null) {
            span = new Span(spanIdGenerator++, System.currentTimeMillis(), operationName);
        } else {
            span = new Span(spanIdGenerator++, parentSpan.getSpanId(), System.currentTimeMillis(), operationName);
        }
        TraceContext.push(span);
        return span;
    }

    static Span stopSpan() {
        Span span =  pop();
        span.setEndTime(System.currentTimeMillis());
        return span;
    }

    static void push(Span span) {
        activeSpanStak.push(span);
    }

    static Span pop() {
        return activeSpanStak.pop();
    }

    static Span peek() {
        return activeSpanStak.peek();
    }
}
