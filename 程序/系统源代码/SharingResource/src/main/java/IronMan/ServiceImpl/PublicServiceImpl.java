package IronMan.ServiceImpl;

import IronMan.Service.PublicService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PublicServiceImpl implements PublicService {
	@Override
	public List getDataByPageAndLimit(List data,int page, int limit) {
		List result=new ArrayList();
		for (int i=(page-1)*limit;i<data.size()&&i<page*limit;i++)
			result.add(data.get(i));
		return result;
	}
	@Override
	public ArrayList<Integer> getArray(String datas) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		String temp = "";
		for (int i = 0; i < datas.length(); i++) {
			if (datas.charAt(i) != ',')
				temp += datas.charAt(i);
			else {
				result.add(Integer.valueOf(temp));
				temp = "";
			}
		}
		return result;
	}

	@Override
	public String getSort(Double score) {
		if (score<30)
			return "倔强青铜";
		else if (score<60)
			return "不屈白银";
		else if (score<90)
			return "荣耀黄金";
		else if (score<120)
			return "尊贵铂金";
		else if (score<150)
			return "璀璨钻石";
		else if (score<180)
			return "超凡大师";
		else
			return "最强王者";
	}
}
