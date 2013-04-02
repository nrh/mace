import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;

import com.google.common.io.CharStreams;
import static com.google.common.collect.Lists.newArrayList;

public class UrlGetter {


    public static class Timeouts {

        public static final Timeouts Default = new Timeouts(
                Duration.standardSeconds( 0),
                Duration.standardMinutes( 0)
        );

        public final int connectionTimeoutMillis;
        public final int receiveTimeoutMillis;

        public Timeouts(Duration connectTimeout, Duration receiveTimeout) {
            this.connectionTimeoutMillis = (int) connectTimeout.getMillis();
            this.receiveTimeoutMillis = (int) receiveTimeout.getMillis();
        }
    }

    public HttpResult get(URI uri) throws IOException, URISyntaxException {
        return handleResponse(uri, openConnectionTo(uri, Timeouts.Default));
    }

    public HttpResult put(URI uri, String content, String contentType) throws IOException, URISyntaxException {
        HttpURLConnection connection = openConnectionTo(uri, Timeouts.Default);
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        setContentTypeOn(connection, contentType);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

        try {
            out.write(content);
        } finally {
            out.close();
        }

        return handleResponse(uri, connection);
    }

    public HttpResult put(URI uri) throws IOException, URISyntaxException {
        HttpURLConnection connection = openConnectionTo(uri, Timeouts.Default);
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");

        return handleResponse(uri, connection);
    }

    private HttpURLConnection openConnectionTo(URI uri, Timeouts timeouts) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setConnectTimeout(timeouts.connectionTimeoutMillis);
        connection.setReadTimeout(timeouts.receiveTimeoutMillis);
        return connection;
    }

    private List<HttpResult.Header> responseHeadersFrom(HttpURLConnection httpCon) {
        List<HttpResult.Header> headers = newArrayList();
        for (Map.Entry<String, List<String>> responseHeader : httpCon.getHeaderFields().entrySet()) {
            for (String headerValue : responseHeader.getValue()) {

                String name = responseHeader.getKey();
                if (name != null) {
                    headers.add(new HttpResult.Header(name, headerValue));
                }
            }
        }
        return headers;
    }

    private void setContentTypeOn(HttpURLConnection connection, String contentType) {
        connection.setRequestProperty("Content-Type", contentType);
    }

    private HttpResult handleResponse(URI uri, HttpURLConnection connection) throws IOException, URISyntaxException {
        int responseCode = connection.getResponseCode();

        InputStreamReader reader = openAppropriateResultStream(responseCode, connection);

        try {
            return new HttpResult(uri, connection.getConnectTimeout(), connection.getReadTimeout(), connection.getRequestMethod(), responseCode, responseHeadersFrom(connection), CharStreams.toString(reader));
        } finally {
            reader.close();
        }
    }

    private InputStreamReader openAppropriateResultStream(int responseCode, HttpURLConnection connection) throws IOException {
        InputStreamReader reader;

        if (responseCode >= 400) {
            reader = new InputStreamReader(connection.getErrorStream());
        } else {
            reader = new InputStreamReader(connection.getInputStream());
        }
        return reader;
    }
}
