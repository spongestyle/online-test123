package goodee.gdj58.online.service;

import org.springframework.beans.factory.annotation.Autowired;

import goodee.gdj58.online.mapper.IdMapper;

public class IdService {
@Autowired IdMapper idMapper;
	
	// null이면 사용가능, null아니면 사용불가한 ID
	public String getIdCheck(String id) {
		return idMapper.selectIdCheck(id);
	}

}
