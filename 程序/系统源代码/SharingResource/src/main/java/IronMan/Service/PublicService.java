package IronMan.Service;

import java.util.ArrayList;
import java.util.List;

public interface PublicService {
	List getDataByPageAndLimit(List data,int page, int limit);
	ArrayList<Integer> getArray(String datas);
	String getSort(Double score);
}
