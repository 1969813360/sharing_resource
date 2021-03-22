package IronMan.Controller;

import IronMan.Model.OperationRecord;
import IronMan.Service.OperationRecordService;
import IronMan.Service.PublicService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class OperationRecordController
{
	@Autowired
	OperationRecordService operationRecordService;
	@Autowired
	PublicService publicService;

	@RequestMapping(value = "/AllOperationRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public String AllOperationRecord() {return "operationRecord/allOperationRecord"; }

	//************************************************
	@ResponseBody
	@RequestMapping("/getAllOperationRecord")//获取所有
	public JSONObject getAllOperationRecord(@Param("page") int page, @Param("limit") int limit) {
		JSONObject result=new JSONObject();
		List alldata = operationRecordService.selectAll();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/searchOperationRecord")//查找
	public JSONObject searchOperationRecord(@Param("userid") String userid,@Param("fileid") String fileid,@Param("operationtype") String operationtype,@Param("page") int page, @Param("limit") int limit) {
		List<OperationRecord> alldata = operationRecordService.searchOperation(userid,fileid,operationtype);
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
}

