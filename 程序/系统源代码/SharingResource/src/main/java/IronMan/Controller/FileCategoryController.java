package IronMan.Controller;

import IronMan.Model.FileCategory;
import IronMan.Service.FileCategoryService;
import IronMan.Service.PublicService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileCategoryController
{
	@Autowired
	FileCategoryService fileCategoryService;
	@Autowired
	PublicService publicService;
	
	@RequestMapping(value = "/AllFileCategory", method = { RequestMethod.POST, RequestMethod.GET })
	public String AllFileCategory() {return "fileCategory/allFileCategory"; }
	@RequestMapping(value = "/gotoAddFileCategory", method = { RequestMethod.POST, RequestMethod.GET })
	public String gotoAddFileCategory() {return "fileCategory/addFileCategory"; }
	
	//************************************************
	@ResponseBody
	@RequestMapping("/getAllFileCategory")//获取所有
	public JSONObject getAllFileCategory(@Param("page") int page, @Param("limit") int limit) {
		List alldata = fileCategoryService.selectAll();
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/updateFileCategory")//更新
	public JSONObject updateFileCategory(@Param("fileCategoryId") String fileCategoryId,@Param("value") String value, HttpSession session) {
		FileCategory temp=fileCategoryService.selectByPrimaryKey(Integer.valueOf(fileCategoryId));
//		System.out.println(fileCategoryid+"**"+dataname+"**"+value);
		if (fileCategoryService.selectByCategoryName(value).size()==1)//科目名已存在
			return JSONObject.parseObject("{code : " + 2 + " }");
		temp.setName(value);
		fileCategoryService.updateByPrimaryKey(temp);
		return JSONObject.parseObject("{code : " + 1 + " }");
	}
	@ResponseBody
	@RequestMapping("/addFileCategory")//增添
	public JSONObject addFileCategory(HttpSession session, @Param("name") String name) {
		if(fileCategoryService.selectByCategoryName(name).size()==1)
			return JSONObject.parseObject("{code : " + 1 + " }");//科目名被占用
		fileCategoryService.insert(new FileCategory(name));
		return JSONObject.parseObject("{code : " + 2 + " }");
	}
	@ResponseBody
	@RequestMapping("/delFileCategorys")//批量删除
	public JSONObject delFileCategorys(@Param("datas") String datas) {
		ArrayList<Integer> data = publicService.getArray(datas);
		for (int i = 0; i < data.size(); i++) {
			fileCategoryService.deleteByPrimaryKey(data.get(i));
		}
		return JSONObject.parseObject("{code : " + 1 + " }");//传给前台js的一个参数状态
	}
	@ResponseBody
	@RequestMapping("/searchFileCategory")//查找
	public JSONObject searchFileCategory(@Param("name") String name,@Param("page") int page, @Param("limit") int limit) {
		List<FileCategory> alldata = fileCategoryService.selectByCategoryName("%"+name+"%");
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/delFileCategory")//删除
	public JSONObject delFileCategory(@Param("id") String id) {
		fileCategoryService.deleteByPrimaryKey(Integer.valueOf(id));
		return JSONObject.parseObject("{code : " + 1 + " }");//传给前台js的一个参数判断删除的状态
	}
}
