import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class HttpResult {
    public final String body;
    public final URI uri;
    public final int connectTimeout;
    public final int readTimeout;
    public final int status;
    public final Map<String, Header> headers = newHashMap();
    public final String method;

    public HttpResult(URI url, int connectTimeout, int readTimeout, String method, int status, List<Header> headers, String body) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.status = status;
        this.method = method;
        this.uri = url;
        this.body = body;
        for (Header header : headers) {
            this.headers.put(header.name.toLowerCase(), header);
        }
    }

    public Header header(String name) {
        return headers.get(name.toLowerCase());
    }

    public boolean hasCacheExpiryHeader() {
        return headers.containsKey("Expires".toLowerCase());
    }

    public boolean hasLastModifiedHeader() {
        return headers.containsKey("Last-Modified".toLowerCase());
    }

    public boolean ok() {
        return status == HttpURLConnection.HTTP_OK || status == HttpURLConnection.HTTP_CREATED;
    }

    public boolean notFound() {
        return status == HttpURLConnection.HTTP_NOT_FOUND;
    }

    public boolean error() {
        return status >= HttpURLConnection.HTTP_INTERNAL_ERROR;
    }

    public boolean badGateway() {
        return status == HttpURLConnection.HTTP_BAD_GATEWAY;
    }

    public boolean inConflict() {
        return status == HttpURLConnection.HTTP_CONFLICT;
    }

    public boolean badRequest() {
        return status == HttpURLConnection.HTTP_BAD_REQUEST;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HttpResult[");

        sb.append("url=");
        sb.append(uri);
        sb.append(",\nconnectTimeout=");
        sb.append(connectTimeout);
        sb.append(",\nreceiveTimeout=");
        sb.append(readTimeout);
        sb.append(",\nstatus=");
        sb.append(status);
        sb.append("\nheaders=");
        for (Header header : headers.values()) {
            sb.append("\n\t[name=");
            sb.append(header.name);
            sb.append(",value=");
            sb.append(header.value);
            sb.append("]");
        }
        sb.append(",\nbody=");
        sb.append(body);
        sb.append("]");

        return sb.toString();
    }

    public String contentType() {
        return header("Content-Type").value;
    }

    public static class Header {
        public final String name;
        public final String value;

        public Header(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
