package younian.apmsample.agent.plugin.jdbc.sql;

import younian.apmsample.agent.context.ContextManager;
import younian.apmsample.agent.context.Span;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTracing {

    public static <R> R execute(Connection realConnection, String method, String sql,
                                Executable<R> exec) throws SQLException {

        Span span = ContextManager.createSpan("Mysql", "/JDBC/Connection/" + method);
        span.setExtraInfo(sql);
        try {
            return exec.exec(realConnection, sql);
        } catch (SQLException e) {
            throw e;
        } finally {
            ContextManager.stopSpan();
        }
    }

    public interface Executable<R> {
        R exec(Connection realConnection, String sql) throws SQLException;
    }
}