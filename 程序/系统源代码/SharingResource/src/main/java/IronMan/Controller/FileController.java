package IronMan.Controller;

import IronMan.Model.*;
import IronMan.Service.*;
import com.alibaba.fastjson.JSONObject;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class FileController {
	@Autowired
	ResourceService fileService;
	@Autowired
	FileCategoryService fileCategoryService;
	@Autowired
	OperationRecordService operationRecordService;
	@Autowired
	ScoreRecordService scoreRecordService;
	@Autowired
	UserService userService;
	@Autowired
	PublicService publicService;

	SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd-hh:mm");

	@RequestMapping(value = "/gotoAddFile", method = { RequestMethod.POST, RequestMethod.GET })
	public String gotoAddFile( HttpSession session) {
		List<FileCategory> allFileCategory=fileCategoryService.selectAll();
		session.setAttribute("allFileCategory",allFileCategory);
		return "file/addFile";
	}
	@RequestMapping(value = "/gotoCommentFile", method = { RequestMethod.POST, RequestMethod.GET })
	public String gotoCommentFile() { return "file/commentFile"; }
	@RequestMapping(value = "/gotoViewVideo", method = { RequestMethod.POST, RequestMethod.GET })
	public String gotoViewVideo() { return "viewResource/viewVideo"; }
	@RequestMapping(value = "/gotoViewImg", method = { RequestMethod.POST, RequestMethod.GET })
	public String gotoViewImg() { return "viewResource/viewImage"; }
	@RequestMapping(value = "/AllFile", method = { RequestMethod.POST, RequestMethod.GET })
	public String AllFile() { return "file/allFile"; }
	//******************************以下是ajax传参调用的方法************************************
	@ResponseBody
	@RequestMapping("/getAllFile")//获取所有
	public JSONObject getAllFile(@Param("page") int page, @Param("limit") int limit) {
		List<Resource> alldata = fileService.selectAll();
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/getAllPassedFile")//获取所有已通过的文件
	public JSONObject getAllPassedFile(@Param("page") int page, @Param("limit") int limit) {
		List alldata = fileService.selectByFileState("已过审");
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/getAllPassingFile")//获取所有待审核的文件
	public JSONObject getAllPassingFile(@Param("page") int page, @Param("limit") int limit) {
		List alldata = fileService.selectByFileState("待审核");
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/getAllNotPassedFile")//获取所有未通过审核的文件
	public JSONObject getAllNotPassedFile(@Param("page") int page, @Param("limit") int limit) {
		List alldata = fileService.selectByFileState("未过审");
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/rejectfiles")//批量拒绝通过审核
	public JSONObject rejectfiles(@Param("datas") String datas) {
		ArrayList<Integer> data = publicService.getArray(datas);
		Resource tempfile;
		for (int i = 0; i < data.size(); i++) {
			tempfile=fileService.selectByPrimaryKey(data.get(i));
			tempfile.setFilestate("未过审");
			fileService.updateByPrimaryKey(tempfile);
		}
		return JSONObject.parseObject("{code : " + 1 + " }");//传给前台js的一个参数状态
	}
	@ResponseBody
	@RequestMapping("/agreefiles")//批量通过审核
	public JSONObject agreefiles(@Param("datas") String datas) {
		ArrayList<Integer> data = publicService.getArray(datas);
		Resource tempfile;
		for (int i = 0; i < data.size(); i++) {
			tempfile=fileService.selectByPrimaryKey(data.get(i));
			tempfile.setFilestate("已过审");
			fileService.updateByPrimaryKey(tempfile);
		}
		return JSONObject.parseObject("{code : " + 1 + " }");//传给前台js的一个参数状态
	}
	@ResponseBody
	@RequestMapping("/showMyFile")//查询个人上传文件
	public JSONObject showMyFile(@Param("page") int page, @Param("limit") int limit,HttpSession session) {
		List<Resource> alldata = fileService.selectAllByAuthorId(Integer.valueOf(session.getAttribute("currentUserId").toString()));
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/searchFile")//查询文件
	public JSONObject searchFile(@Param("fileCategory") String fileCategory,@Param("filename") String filename,@Param("filetype") String filetype,@Param("filestate") String filestate,@Param("page") int page, @Param("limit") int limit) {
		List<Resource> alldata;
		System.out.println("文件状态:"+filestate);
		if (filestate==null)
			alldata= fileService.searchFile("已过审",fileCategory,filename,filetype);
		else
			alldata= fileService.searchFile(filestate,fileCategory,filename,filetype);
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/uploadFile")//上传
	public JSONObject uploadFile(@Param("categoryId")String categoryId,@Param("fileIntroduce")String fileIntroduce,HttpSession session,@RequestParam("file") MultipartFile file, HttpServletRequest servletRequest){
		JSONObject res = new JSONObject();
		try{
			//上传文件路径
			String path = servletRequest.getServletContext().getRealPath("/uploadFile");
			String name = file.getOriginalFilename();//上传文件的真实名称
			String suffixName = name.substring(name.lastIndexOf("."));//获取后缀名
			suffixName=suffixName.toLowerCase();
			String filename=name.substring(0,name.lastIndexOf("."));
			//将上传文件保存到一个目标文档中
			path="D:/MyIdea/SharingResource/src/main/resources/static/Source";
			File tempFile = new File(path + File.separator + name);
			String hash="";
			String newName=filename+suffixName;
			while(tempFile.exists())//有同名文件
			{
				hash = Integer.toHexString(new Random().nextInt());//自定义随机数（字母+数字）作为文件名
				newName = filename+hash + suffixName;
				tempFile=new File(path+ File.separator+newName);
			}
			tempFile.createNewFile();//创建新文件
			file.transferTo(tempFile);//将原文件复制到文件上传地址

			//在target目录临时文件中添加文件，防止文件找不到
			File targetFile=new File("D:/MyIdea/SharingResource/target/classes/static/Source/"+newName);
			targetFile.createNewFile();
			file.transferTo(targetFile);
			int userid=Integer.valueOf(session.getAttribute("currentUserId").toString());
			Date now = new Date( );

			//写入文件数据库
			fileService.insert(new Resource(userid,filename+hash,suffixName,Integer.valueOf(categoryId),path+'/'+tempFile.getName(),0.0,ft.format(now),ft.format(now),"待审核",fileIntroduce));
			operationRecordService.insert(new OperationRecord(userid,fileService.selectByFilenameAndFiletype(filename+hash,suffixName).getId(),"上传","用户："+session.getAttribute("username")+"于"+ft.format(now)+"上传该文件，等待审核"));

			res.put("code",0);
		}
		catch (IOException E) { E.printStackTrace(); }
		return res;
	}
	@ResponseBody
	@RequestMapping("/delFile")//删除
	public JSONObject delFile(@Param("id") String id,HttpSession session) {
		int currentid=Integer.valueOf(session.getAttribute("currentUserId").toString());
		int fileuserid=fileService.selectByPrimaryKey(Integer.valueOf(id)).getUserid();
		if (fileuserid==currentid)//是本人操作
		{
			File tempfile=new File(fileService.selectByPrimaryKey(Integer.valueOf(id)).getFilelocation());
			tempfile.delete();
			fileService.deleteByPrimaryKey(Integer.valueOf(id));

			//更新用户积分
			User tempuser=userService.selectByPrimaryKey(currentid);
			tempuser.setUserscore(fileService.getUserFilesNumAndScore(currentid).get(1));
			userService.updateByPrimaryKey(tempuser);
			return JSONObject.parseObject("{code : " + 1 + " }");
		}
		return JSONObject.parseObject("{code : " + 2 + " }");//非本人文件删除失败
	}
	@ResponseBody
	@RequestMapping("/downloadFile")//下载
	public JSONObject downloadFile(@Param("filelocation") String filelocation,@Param("filename") String filename,@Param("fileid")String fileid, HttpSession session){
		Integer userid=Integer.valueOf(session.getAttribute("currentUserId").toString());
		try{
			File srcfile=new File("D:/download");
			if (!srcfile.exists())
				srcfile.mkdirs();
			File oddfile=new File(filelocation);
			File newfile=new File("D:/download/"+filename);
			String hash="";
			while (newfile.exists())//已有文件
			{
				hash = Integer.toHexString(new Random().nextInt());
				newfile=new File("D:/download/"+hash+filename);
			}
			newfile.createNewFile();
			FileInputStream input=new FileInputStream(oddfile);
			FileOutputStream out=new FileOutputStream(newfile);
			byte[] bytes = new byte[1024];
			int len = -1;
			while((len = input.read(bytes))!=-1) {
				out.write(bytes,0,len);
			}
			input.close();
			out.close();

			//插入下载记录
			Date now=new Date();
			operationRecordService.insert(new OperationRecord(userid,Integer.valueOf(fileid),"下载","用户："+session.getAttribute("username")+"于"+ft.format(now)+"下载该文件至：D：/download"));
			return JSONObject.parseObject("{code : " + 1 + ",location:'D:/download/"+hash+filename+"' }");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return JSONObject.parseObject("{code : " + 2 + " }");
		}
	}
	@ResponseBody
	@RequestMapping("/judgeCommentFile")//判断是否符合评分标准
	public JSONObject judgeCommentFile(@Param("fileid") String fileid,HttpSession session) {
		String currentid=session.getAttribute("currentUserId").toString();
		Resource temp=fileService.selectByPrimaryKey(Integer.valueOf(fileid));
		if (Integer.valueOf(currentid)==temp.getUserid())//本人评分则返回
			return JSONObject.parseObject("{code : " + 3 + " }");
		if (operationRecordService.selectDownloadByFileIdAndUserId(Integer.valueOf(fileid),Integer.valueOf(currentid)).size()==1)//该用户下载过该文件
		{
			return JSONObject.parseObject("{code : " + 1 + ",fileid :"+fileid+" }");
		}
		return JSONObject.parseObject("{code : " + 2 + " }");//未下载过
	}
	@ResponseBody
	@RequestMapping("/changeFileState")//修改文件状态
	public JSONObject changeFileState(@Param("fileid") String fileid,@Param("filestate") String filestate,HttpSession session) {
		Resource tempfile = fileService.selectByPrimaryKey(Integer.valueOf(fileid));
		int currentid=Integer.valueOf(session.getAttribute("currentUserId").toString());
		tempfile.setFilestate(filestate);
		fileService.updateByPrimaryKey(tempfile);
		return JSONObject.parseObject("{code : " + 1 + " }");
	}
	@ResponseBody
	@RequestMapping("/commentFile")//评分
	public JSONObject judgeCommentFile(@Param("fileid")String fileid,@Param("x1") String x1,@Param("x2") String x2,@Param("x3") String x3,@Param("x4") String x4,HttpSession session) {
		String currentid=session.getAttribute("currentUserId").toString();
		Double score=Double.valueOf(x1)+Double.valueOf(x2)+Double.valueOf(x3)+Double.valueOf(x4);
		score/=4.0;
		List<ScoreRecord> temp=scoreRecordService.selectCommentByFileIdAndUserId(Integer.valueOf(fileid),Integer.valueOf(currentid));
		Date now=new Date();
		if (temp.size()==1)//该用户对该文件已评过分
		{
			ScoreRecord tempComment=temp.get(0);
			tempComment.setScore(score);
			tempComment.setDate(ft.format(now));
			scoreRecordService.updateByPrimaryKey(tempComment);//覆盖以往的评分
		}
		else{

			scoreRecordService.insert(new ScoreRecord(Integer.valueOf(currentid),Integer.valueOf(fileid),score,ft.format(now)));//插入评分记录
		}

		//更新文件评分
		Resource tempfile=fileService.selectByPrimaryKey(Integer.valueOf(fileid));
		tempfile.setFilescore(scoreRecordService.getAverage(Integer.valueOf(fileid)));
		fileService.updateByPrimaryKey(tempfile);

		//更新用户积分
		User tempuser=userService.selectByPrimaryKey(Integer.valueOf(currentid));
		tempuser.setUserscore(fileService.getUserFilesNumAndScore(Integer.valueOf(currentid)).get(1));
		userService.updateByPrimaryKey(tempuser);
		return JSONObject.parseObject("{code : " + 1 + " }");
	}
	@ResponseBody
	@RequestMapping("/setSourcePath")//判断文件类型
	public JSONObject setSourcePath(@Param("filename") String filename,@Param("filetype") String filetype,@Param("filepath") String filepath,HttpSession session) {
		JSONObject result=new JSONObject();
		if (filetype.compareTo(".zip")==0)
		{
			result.put("code",0);
			return result;
		}
		session.setAttribute("sourceName",filename+filetype);
		filetype=filetype.toLowerCase();
		String officetype=".txt.doc.docx.xls.xlsx.ppt.pptx";
		String imgtype=".jpg.png";
		if (filetype.compareTo(".mp4")==0)//是视频
			result.put("code",1);
		else if (officetype.contains(filetype)){//是office文件
			session.setAttribute("filepath",filepath);
			result.put("code",2);
		}
		else if(filetype.compareTo(".pdf")==0)//是pdf
		{
			session.setAttribute("filepath",filepath);
			result.put("code",3);
		}
		else if(imgtype.contains(filetype))//是图片
			result.put("code",4);
		return result;
	}
	@ResponseBody
	@RequestMapping("/viewPdfFile")//查看pdf文件
	public void viewPdfFile(HttpServletResponse response,HttpSession session) {
		try {
			String filepath = session.getAttribute("filepath").toString();
			File file = new File(filepath);
			InputStream in = new FileInputStream(file);// 读取文件
			ServletOutputStream outputStream = response.getOutputStream();
			// copy文件
			int i = IOUtils.copy(in, outputStream);
			in.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping("/toPdfFile")//转换文件
	public void toPdfFile(HttpServletResponse response,HttpSession session) {
		String filepath=session.getAttribute("filepath").toString();
		String tempname=session.getAttribute("sourceName").toString();
		String filename=tempname.substring(0,tempname.lastIndexOf('.'));
		OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
		try {
			File file = new File(filepath);//需要转换的文件
			File newFile = new File("D:/obj-pdf");//转换之后文件生成的地址
			if (!newFile.exists()) {//文件夹不存在则创建文件夹
				newFile.mkdirs();
			}
			File outputFile = new File("D:/obj-pdf/" + filename + ".pdf");
			if (!outputFile.exists()) {//pdf不存在则创建
				outputFile.createNewFile();
				//连接OpenOffice服务
				connection.connect();
				//创建文件转换器
				DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
				//开始转换
				converter.convert(file, outputFile);
				System.out.println("pdf生成完毕！");
			}
			InputStream in = new FileInputStream(outputFile);// 读取文件
			ServletOutputStream outputStream = response.getOutputStream();
			// copy文件
			int i = IOUtils.copy(in, outputStream);
			in.close();
			outputStream.close();
		}
		catch (IOException r){
			r.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping("/changeData")//更新
	public JSONObject changeData(@Param("fileid") String fileid,@Param("dataname") String dataname,@Param("value") String value,HttpSession session) {
		Integer currentid=Integer.valueOf(session.getAttribute("currentUserId").toString());
		Resource temp=fileService.selectByPrimaryKey(Integer.valueOf(fileid));
		String userCategory=userService.selectByPrimaryKey(currentid).getCategory();
		if (currentid==temp.getUserid()||userCategory.compareTo("Admin")==0)//是本人修改或者管理员修改
		{
			temp.setFileintroduce(value);
			Date now=new Date();
			temp.setChangedate(ft.format(now));
			fileService.updateByPrimaryKey(temp);
			operationRecordService.insert(new OperationRecord(currentid,Integer.valueOf(fileid),"修改","用户："+session.getAttribute("username")+"于"+ft.format(now)+"修改该文件"));
			return JSONObject.parseObject("{code : " + 1 + " }");
		}
		return JSONObject.parseObject("{code : " + 2 + " }");
	}
}
