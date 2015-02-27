package victorluproductions.thestockmonitor;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.List;

/**
 * Created by victorlu on 2015-02-21.
 */
public class JSONResponseHandler implements ResponseHandler<List<String>> {

	@Override
	public List<String> handleResponse(HttpResponse httpResponse)
			throws ClientProtocolException, IOException {
		return null;
	}
}
