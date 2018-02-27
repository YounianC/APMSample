package younian.apmsample.agent.context;

public class ContextManager {

    private final static ThreadLocal<TraceContext> CONTEXT = new ThreadLocal<>();

    public static Span createSpan(String operationName){
        return get().createSpan(operationName);
    }

    public static Span stopSpan(){
        Span span = get().stopSpan();
        System.out.println(span.toString());
        return span;
    }

    public static TraceContext get(){
        return CONTEXT.get();
    }
}
