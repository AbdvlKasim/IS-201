package bacit.web.bacit_web;

import bacit.web.bacit_models.FileModel;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.logging.Logger;

@WebServlet(name = "fileDownload", value = "/fileDownload")
public class FileDownloadServlet extends HttpServlet {

    Logger logger = Logger.getLogger(String.valueOf(FileUploadServlet.class));

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileModel fileModel = getFileModel(1);
        response.setContentType(fileModel.getMimeType());
        try{
            String headerKey = "Content-Disposition";
            String headerValue ="attachment; filename="+fileModel.getName();
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            outStream.write(fileModel.getContents());
        }
        catch(Exception ex)
        {
            logger.severe(ex.getMessage());
        }
    }

    private FileModel getFileModel(int id)
    {
        return new FileModel("sirkel.jfif", getImageBytes(), "application/octet-stream");
    }

    private byte[] getImageBytes()
    {
        return Base64.getDecoder().decode("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");
    }
}