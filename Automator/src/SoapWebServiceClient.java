import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hc.client5.http.ContextBuilder;
import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.SystemDefaultDnsResolver;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.StandardAuthScheme;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.auth.CredentialsProviderBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.ManagedHttpClientConnectionFactory;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.ManagedHttpClientConnection;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.CharCodingConfig;
import org.apache.hc.core5.http.config.Http1Config;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.impl.io.DefaultClassicHttpResponseFactory;
import org.apache.hc.core5.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.hc.core5.http.impl.io.DefaultHttpResponseParser;
import org.apache.hc.core5.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.hc.core5.http.io.HttpConnectionFactory;
import org.apache.hc.core5.http.io.HttpMessageParser;
import org.apache.hc.core5.http.io.HttpMessageParserFactory;
import org.apache.hc.core5.http.io.HttpMessageWriterFactory;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicLineParser;
import org.apache.hc.core5.http.message.LineParser;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.CharArrayBuffer;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;



public class SoapWebServiceClient {

	//This method sends a SOAP request and prints response, and parses and prints texts of all <balance> elements in the response 
	public void postSOAPXML(String exampleInputParameter) {
        String resp = null;
        try {

            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:aut=\"http://ulvi.aut.com/aut\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <aut:GetDataRequest>\n" +
                    "         <MiddlewareTransactionId>123456AUT</MiddlewareTransactionId>\n" +
                    "         <keyValue>" + exampleInputParameter + "</keyValue>\n" +
                    "         <returnRealtimeData>true</returnRealtimeData>\n" +
                    "      </aut:GetDataRequest>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            
            HttpClient httpclient = new DefaultHttpClient();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost("http://10.1.1.3:8989/getData");
            post.setHeader("SOAPAction", "GetData");
            post.setEntity(strEntity);

            // Execute request
            BasicClassicHttpResponse response = httpclient.execute(post);
            org.apache.hc.core5.http.HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);
				
                //prints whole response
                System.out.println(resp);
               
                //Regular expression to parse texts of <balance> elements in the response assuming they have no child elements
                Matcher m = Pattern.compile("<balance>([^<]*)").matcher(resp); //groups all characters except < (tag closing character)
                while (m.find()) {
                  //prints texts of all balances in a loop
                  System.out.println(m.group(1));
                }
            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString());
        }
    }

	public static void main(String[] args) {
		SoapWebServiceClient soapWebServiceClientObject = new SoapWebServiceClient();
		soapWebServiceClientObject.postSOAPXML("057177711");
    }
}