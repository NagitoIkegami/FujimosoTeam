package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Download")
public class DownloadServlet extends HttpServlet {
    private final int ARBITARY_SIZE = 1048;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
      throws ServletException, IOException {
    	String fileDirName = "files";
    	String tempFileName = "aaa1.txt";
    	String realFileName = "testFile.txt";
    	String filePath = File.separator+fileDirName+File.separator+tempFileName;
    	
    	
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename="+realFileName);

        try(InputStream in = req.getServletContext().getResourceAsStream(filePath);
          OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[ARBITARY_SIZE];
        
            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }
}