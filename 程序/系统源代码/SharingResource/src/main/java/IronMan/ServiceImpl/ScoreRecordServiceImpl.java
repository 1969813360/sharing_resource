package IronMan.ServiceImpl;

import IronMan.Dao.ScoreRecordMapper;
import IronMan.Model.ScoreRecord;
import IronMan.Service.ScoreRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreRecordServiceImpl implements ScoreRecordService {

	@Autowired
	ScoreRecordMapper scoreRecordMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return scoreRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ScoreRecord record) {
		return scoreRecordMapper.insert(record);
	}

	@Override
	public int insertSelective(ScoreRecord record) {
		return scoreRecordMapper.insertSelective(record);
	}

	@Override
	public ScoreRecord selectByPrimaryKey(Integer id) {
		return scoreRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ScoreRecord record) {
		return scoreRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ScoreRecord record) {
		return scoreRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ScoreRecord> selectAll() {
		return scoreRecordMapper.selectAll();
	}

	@Override
	public List<ScoreRecord> selectCommentByFileId(Integer fileid) {
		return scoreRecordMapper.selectCommentByFileId(fileid);
	}

	@Override
	public List<ScoreRecord> selectCommentByFileIdAndUserId(Integer fileid, Integer userid) {
		return scoreRecordMapper.selectCommentByFileIdAndUserId(fileid,userid);
	}

	@Override
	public List<ScoreRecord> selectCommentByUserId(Integer userid) {
		return scoreRecordMapper.selectCommentByUserId(userid);
	}

	@Override
	public List<ScoreRecord> searchComment(String userid, String fileid) {
		List<ScoreRecord> result=scoreRecordMapper.selectAll();
		List<ScoreRecord> temp;
		try {
			if (userid.compareTo("") != 0) {
				temp = scoreRecordMapper.selectCommentByUserId(Integer.valueOf(userid));
				result = getSame(result, temp);
			}
			if (fileid.compareTo("") != 0) {
				temp = scoreRecordMapper.selectCommentByFileId(Integer.valueOf(fileid));
				result = getSame(result, temp);
			}
			System.out.print("查询后："+result.size());
			return result;
		}catch (Exception e){result=new ArrayList<ScoreRecord>();return result;}
	}

	@Override
	public Double getAverage(Integer fileid) {
		List<ScoreRecord> list=scoreRecordMapper.selectCommentByFileId(fileid);
		Double result=0.0;
		for (int i=0;i<list.size();i++)
			result+=list.get(i).getScore();
		result/=(list.size()*1.0);
		return result;
	}

	public List<ScoreRecord> getSame(List<ScoreRecord> x, List<ScoreRecord> y) {
		List<ScoreRecord> result=new ArrayList<>();
		if (x.size()==0||y.size()==0)
			return result;
		for (int i=0;i<y.size();i++)
		{
			if (isExisted(x,y.get(i).getId()))
				result.add(y.get(i));
		}
		return result;
	}
	private Boolean isExisted(List<ScoreRecord> list, int tempid) {
		for (int i=0;i<list.size();i++)
		{
			if (list.get(i).getId()==tempid)
				return true;
		}
		return false;
	}
}
