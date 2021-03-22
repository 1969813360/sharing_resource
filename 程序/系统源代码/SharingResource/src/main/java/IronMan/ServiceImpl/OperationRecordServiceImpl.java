package IronMan.ServiceImpl;

import IronMan.Dao.OperationRecordMapper;
import IronMan.Model.OperationRecord;
import IronMan.Service.OperationRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationRecordServiceImpl implements OperationRecordService {

	@Autowired
	OperationRecordMapper operationRecordMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return operationRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OperationRecord record) {
		return operationRecordMapper.insert(record);
	}

	@Override
	public int insertSelective(OperationRecord record) {
		return operationRecordMapper.insertSelective(record);
	}

	@Override
	public OperationRecord selectByPrimaryKey(Integer id) {
		return operationRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OperationRecord record) {
		return operationRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(OperationRecord record) {
		return operationRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<OperationRecord> selectAll() {
		return operationRecordMapper.selectAll();
	}

	@Override
	public List<OperationRecord> selectDownloadByFileIdAndUserId(Integer fileid, Integer userid) {
		return operationRecordMapper.selectDownloadByFileIdAndUserId(fileid,userid);
	}

	@Override
	public List<OperationRecord> selectByFileId(Integer fileid) {
		return operationRecordMapper.selectByFileId(fileid);
	}

	@Override
	public List<OperationRecord> selectByUserId(Integer userid) {
		return operationRecordMapper.selectByUserId(userid);
	}

	@Override
	public List<OperationRecord> selectByOperationType(String operationtype) {
		return operationRecordMapper.selectByOperationType(operationtype);
	}

	@Override
	public List<OperationRecord> searchOperation(String userid, String fileid, String operationtype) {
		List<OperationRecord> result=new ArrayList<OperationRecord>();
		List<OperationRecord> temp;
		try{
			if (operationtype.compareTo("*")==0)
				result=operationRecordMapper.selectAll();
			else
				result=operationRecordMapper.selectByOperationType(operationtype);
			if (userid.compareTo("") != 0) {
				temp=operationRecordMapper.selectByUserId(Integer.valueOf(userid));
				result = getSame(result, temp);
			}
			if (fileid.compareTo("") != 0) {
				temp=operationRecordMapper.selectByFileId(Integer.valueOf(fileid));
				result = getSame(result, temp);
			}
			return result;
		}catch (Exception e){result=new ArrayList<OperationRecord>();return result;}
	}
	public List<OperationRecord> getSame(List<OperationRecord> x, List<OperationRecord> y) {
		List<OperationRecord> result=new ArrayList<>();
		if (x.size()==0||y.size()==0)
			return result;
		for (int i=0;i<y.size();i++)
		{
			if (isExisted(x,y.get(i).getId()))
				result.add(y.get(i));
		}
		return result;
	}
	private Boolean isExisted(List<OperationRecord> list, int tempid) {
		for (int i=0;i<list.size();i++)
		{
			if (list.get(i).getId()==tempid)
				return true;
		}
		return false;
	}
}
