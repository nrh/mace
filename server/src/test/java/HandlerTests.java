import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Connector;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spotify.mace.*;

public class HandlerTests extends TestCase {
    private static Logger log = Logger.getLogger(HandlerTests.class);
    private static Gson json = new GsonBuilder().create();
    private static Server server = new Server();

    @Before
    public void setUp() throws Exception {
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8080);
        connector.setMaxIdleTime(3000);
        server.setConnectors(new Connector[] { connector });

        ContextHandler jobscontext = new ContextHandler("/mace/v1/jobs");
        jobscontext.setHandler(new JobsHandler());

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { jobscontext });
        server.setHandler(contexts);

        log.error("started on 8080");
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
        log.error("stopped");
    }

    @Test
    public void testRequestResponse() throws Throwable {
      log.error("hi");
      UrlGetter getter = new UrlGetter();
      HttpResult result = getter.get(new URI("http://localhost:8080/mace/v1/jobs"));
      log.error(result);
//      assertThat(result, isOK());
//      assertThat(result, hasBody(equalTo("Hello")));
    }
}


