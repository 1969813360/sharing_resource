package IronMan.ServiceImpl;

import IronMan.Dao.ResourceMapper;
import IronMan.Model.Resource;
import IronMan.Service.ResourceService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceMapper resourceMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return resourceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Resource record) {
		return resourceMapper.insert(record);
	}

	@Override
	public int insertSelective(Resource record) {
		return resourceMapper.insertSelective(record);
	}

	@Override
	public Resource selectByPrimaryKey(Integer id) {
		return resourceMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Resource record) {
		return resourceMapper.updateByPrimaryKeySelective(record);
	}



	@Override
	public int updateByPrimaryKey(Resource record) {
		return resourceMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Resource> selectAll() {
		return resourceMapper.selectAll();
	}

	@Override
	public Resource selectByFilenameAndFiletype(String filename,String filetype) {
		return resourceMapper.selectByFilenameAndFiletype(filename,filetype);
	}

	@Override
	public List<Resource> selectByFileState(String state) {
		return resourceMapper.selectByFileState(state);
	}

	@Override
	public List<Resource> selectAllByAuthorId(int id) {
		return resourceMapper.selectAllByAuthorId(id);
	}

	@Override
	public ArrayList<Double> getUserFilesNumAndScore(Integer id) {
		ArrayList<Double> res=new ArrayList<Double>();
		Double result=0.0;
		List<Resource> list=resourceMapper.selectAllByAuthorId(id);
		res.add(Double.valueOf(""+list.size()));
		for (int i=0;i<list.size();i++)
			result+=list.get(i).getFilescore();
		res.add(result);
		return res;
	}

	@Override
	public List<Resource> searchFile(String state,String filecategory, String filename, String filetype) {
		List<Resource> result=resourceMapper.selectByFileState(state);
		System.out.println("该状态的文件数为："+result.size());
		if (filecategory.compareTo("*")!=0) {//文件所属科目不为空
			List<Resource> temp2 = resourceMapper.selectByFileCategory(Integer.valueOf(filecategory));
			System.out.println("该科目的文件数为："+temp2.size());
			result=getSame(result,temp2);
		}
		//文件名不为空
			List<Resource> temp = resourceMapper.selectByFilename("%"+filename+"%");
			System.out.println("该文件名的文件数为："+temp.size());
			result=getSame(result,temp);
		if (filetype.compareTo("*")!=0){//文件类型不为空
			List<Resource> temp2 = resourceMapper.selectByFiletype(filetype);
			System.out.println("该文件类型的文件数为："+temp2.size());
			result=getSame(result,temp2);
		}
		return result;
	}

	@Override
	public List<Resource> selectByFileCategory(Integer filecategory) {
		return resourceMapper.selectByFileCategory(filecategory);
	}

	@Override
	public List<Resource> selectByFilename(String filename) {
		return resourceMapper.selectByFilename(filename);
	}

	@Override
	public List<Resource> selectByFiletype(String filetype) {
		return resourceMapper.selectByFiletype(filetype);
	}

	@Override
	public List<Resource> getSame(List<Resource> x, List<Resource> y) {
		List<Resource> result=new ArrayList<>();
		if (x.size()==0||y.size()==0)
			return result;
		for (int i=0;i<y.size();i++)
		{
			if (isExisted(x,y.get(i).getId()))
				result.add(y.get(i));
		}
		return result;
	}
	private Boolean isExisted(List<Resource> list, int tempid) {
		for (int i=0;i<list.size();i++)
		{
			if (list.get(i).getId()==tempid)
				return true;
		}
		return false;
	}
}
