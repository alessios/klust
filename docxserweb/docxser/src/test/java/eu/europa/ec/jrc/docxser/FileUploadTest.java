package eu.europa.ec.jrc.docxser;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class FileUploadTest {

    @Test
    public void testUpload() throws Exception {
	HttpClient httpclient = HttpClientBuilder.create().build();
	HttpPost httppost = new HttpPost(
		"http://localhost:8080/docxser/service/fileupload");

	MultipartEntityBuilder mbem = MultipartEntityBuilder.create();
	mbem.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	mbem.addBinaryBody("file", new File(
		"M:\\tmp\\the_datacenter_as_the_computer.pdf"));

	httppost.setEntity(mbem.build());

	System.out.println("executing request " + httppost.getRequestLine());
	HttpResponse response = httpclient.execute(httppost);
	HttpEntity resEntity = response.getEntity();

	if (resEntity != null) {
	    String page = EntityUtils.toString(resEntity);
	    System.out.println("PAGE :" + page);
	}

    }
}
