package goodee.gdj58.online.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import goodee.gdj58.online.vo.Employee;

@Mapper
public interface EmployeeMapper {

	
	// updateEmp
	int updateEmployeePw(Map<String, Object> paramMap);
	
	// loginEmp
	Employee login(Employee employee);
	
	// deleteEmp
	int deleteEmployee(int empNo);
	
	// insertEmp
	int insertEmployee(Employee employee);
	
	// empList
	List<Employee> selectEmployeeList(Map<String, Object> paramMap);
}