package servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



@WebServlet("/UploadServlet" )
@SuppressWarnings("serial")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024,  // 1MB
		maxFileSize= 1024 * 1024 * 100,  // 100MB
		maxRequestSize = 1024 * 1024 * 500  // 500MB
		)
public class UploadServlet extends HttpServlet {
	// ファイル通番
	public void init() throws ServletException {
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		//保存先フォルダが存在しなければ作成
		String fileDirName = "files";
		String fileDirPath = this.getServletContext().getRealPath(File.separator+fileDirName);
		File fileDir = new File(fileDirPath);
		if( !fileDir.exists() ) {
			fileDir.mkdir();
		}

//		//単独ファイルの場合------------------
//		// リクエストのファイルを取得
//		Part part = req.getPart("files");
//		// 保存用のファイル名を作成
//		StringBuilder saveName = new StringBuilder();
//		//saveName.append(part.getSubmittedFileName());
//		saveName.append("aaa1.txt");
//		System.out.println("FileName:"+saveName.toString());
//
//		// 保存用のファイルを作成
//		File saveFile = new File(
//				fileDirPath, saveName.toString()
//				);
//		System.out.println("AbsolutePath:"+saveFile.getAbsolutePath());
//
//		// ファイルを保存
//		part.write(saveFile.getAbsolutePath());
//		//単独ファイルの場合------------------
		
		
		//複数ファイルの場合------------------
		int count=1;
		for (Part part : req.getParts()) {

			if( !part.getName().equals("files")) continue;
			// 保存用のファイル名を作成
			StringBuilder saveName = new StringBuilder();
			//saveName.append(part.getSubmittedFileName());
			saveName.append("aaa"+count+".txt");
			System.out.println("FileName:"+saveName.toString());

			// 保存用のファイルを作成
			File saveFile = new File(
					fileDirPath, saveName.toString()
					);
			System.out.println("AbsolutePath:"+saveFile.getAbsolutePath());

			// ファイルを保存
			part.write(saveFile.getAbsolutePath());
			count++;
		}
		//複数ファイルの場合------------------
		
		
		// レスポンスを返却または他のサーブレットに移動
		respond(res);
	}

	public void respond(HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		res.setCharacterEncoding("utf-8");
		res.getWriter().println(
				"<html><body><p>File saved.</p></body></html>"
				);
	}
}
