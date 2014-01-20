package eu.europa.ec.jrc.docxser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/fileupload")
public class UploadFileService {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
	    @FormDataParam("file") InputStream uploadedInputStream,
	    @FormDataParam("file") FormDataContentDisposition fileDetail)
	    throws Exception {

	File uploadedFileLocation = File.createTempFile("docxser", ".tmp");

	// save it
	saveToFile(uploadedInputStream, uploadedFileLocation);

	String output = getContent(new FileInputStream(uploadedFileLocation));

	uploadedFileLocation.delete();

	return Response.status(200).entity(output).build();
    }

    protected String getContent(final InputStream is) {
	PDDocument pdfDocument = null;
	try {
	    PDFParser parser = new PDFParser(is);
	    parser.parse();
	    pdfDocument = parser.getPDDocument();
	    PDFTextStripper stripper = new PDFTextStripper();
	    return stripper.getText(pdfDocument);
	} catch (IOException e) {
	    // log.warn("Error: Can't open stream", e);
	    e.printStackTrace();
	} catch (Throwable e) {
	    // catch this, since we need to close the resources
	    // log.debug("An error occurred while getting contents from PDF",
	    // e);
	    e.printStackTrace();
	} finally {
	    if (pdfDocument != null) {
		try {
		    pdfDocument.close();
		} catch (IOException e1) {
		    // log.warn("Error: Can't close pdf.", e1);
		    e1.printStackTrace();
		}
	    }
	}
	return "NA";
    }

    // save uploaded file to new location
    private void saveToFile(InputStream uploadedInputStream,
	    File uploadedFileLocation) {

	try {
	    OutputStream out = null;
	    int read = 0;
	    byte[] bytes = new byte[1024];

	    out = new FileOutputStream(uploadedFileLocation);
	    while ((read = uploadedInputStream.read(bytes)) != -1) {
		out.write(bytes, 0, read);
	    }
	    out.flush();
	    out.close();
	} catch (IOException e) {

	    e.printStackTrace();
	}

    }

}