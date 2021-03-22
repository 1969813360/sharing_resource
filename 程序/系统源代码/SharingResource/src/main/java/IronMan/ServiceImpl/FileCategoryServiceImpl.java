package IronMan.ServiceImpl;

import IronMan.Dao.FileCategoryMapper;
import IronMan.Model.FileCategory;
import IronMan.Service.FileCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FileCategoryServiceImpl  implements FileCategoryService {
	@Autowired
	FileCategoryMapper fileCategoryMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return fileCategoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FileCategory record) {
		return fileCategoryMapper.insert(record);
	}

	@Override
	public int insertSelective(FileCategory record) {
		return fileCategoryMapper.insertSelective(record);
	}

	@Override
	public FileCategory selectByPrimaryKey(Integer id) {
		return fileCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FileCategory record) {
		return fileCategoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FileCategory record) {
		return fileCategoryMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<FileCategory> selectAll() {
		return fileCategoryMapper.selectAll();
	}

	@Override
	public List<FileCategory> selectByCategoryName(String name) {
		return fileCategoryMapper.selectByCategoryName(name);
	}
}
