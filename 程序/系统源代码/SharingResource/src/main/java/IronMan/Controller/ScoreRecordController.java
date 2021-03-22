package IronMan.Controller;

import IronMan.Model.ScoreRecord;
import IronMan.Service.ScoreRecordService;
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
public class ScoreRecordController
{
	@Autowired
	ScoreRecordService scoreRecordService;
	@Autowired
	PublicService publicService;

	@RequestMapping(value = "/AllScoreRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public String AllScoreRecord() {return "scoreRecord/allScoreRecord"; }

	//************************************************
	@ResponseBody
	@RequestMapping("/getAllScoreRecord")//获取所有
	public JSONObject getAllScoreRecord(@Param("page") int page, @Param("limit") int limit) {
		List alldata = scoreRecordService.selectAll();
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
	@ResponseBody
	@RequestMapping("/searchScoreRecord")//查找
	public JSONObject searchScoreRecord(@Param("userid") String userid,@Param("fileid") String fileid,@Param("page") int page, @Param("limit") int limit) {
		List<ScoreRecord> alldata = scoreRecordService.searchComment(userid,fileid);
		JSONObject result=new JSONObject();
		result.put("code",0);
		result.put("msg","");
		result.put("count",alldata.size());
		result.put("data",publicService.getDataByPageAndLimit(alldata,page,limit));
		return result;
	}
}
