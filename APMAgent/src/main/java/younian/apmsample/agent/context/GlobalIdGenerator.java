package younian.apmsample.agent.context;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class GlobalIdGenerator {

	private final static int PROCESS_UUID = initProcessUUID();

	private final static ThreadLocal<AtomicInteger> THREAD_ID_SEQUENCE = new ThreadLocal<AtomicInteger>() {
		protected AtomicInteger initialValue() {
			return new AtomicInteger(0);
		};
	};

	public static String generate(String type) {
		AtomicInteger num = THREAD_ID_SEQUENCE.get();
		num.incrementAndGet();
		return StringUtils.join('.', type + "", System.currentTimeMillis() + "", PROCESS_UUID + "", MachineInfo.getProcessNo() + "", Thread.currentThread().getId() + "", num.get() + "");
	}

	private static int initProcessUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid.substring(uuid.length() - 7).hashCode();
	}
	public static void main(String[] args) {
		System.out.println(GlobalIdGenerator.generate("tomcat"));
	}

}
