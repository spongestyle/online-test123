package goodee.gdj58.online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import goodee.gdj58.online.service.EmployeeService;
import goodee.gdj58.online.service.*;
import goodee.gdj58.online.vo.Employee;
import goodee.gdj58.online.vo.Student;
import goodee.gdj58.online.vo.Teacher;
import jakarta.servlet.http.HttpSession;

@Controller 
public class EmployeeController {
	@Autowired 
	EmployeeService employeeService;
	IdService idService;
	TeacherService teacherService;
	StudentService studentService;
	
	
	
	// update empPw
	@GetMapping("/employee/modifyEmpPw")
	public String modifyEmpPw(HttpSession session) {
		// 로그인 후 호출 가능
		Employee loginEmp = (Employee)session.getAttribute("loginEmp");
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		}
		return "employee/modifyEmpPw";
	}
	
	// pw수정 액션
	@PostMapping("/employee/modifyEmpPw")
	public String modifyEmpPw(HttpSession session
						, @RequestParam(value="oldPw") String oldPw
						, @RequestParam(value="newPw") String newPw) {
		// 로그인 후 호출 가능
		Employee loginEmp = (Employee)session.getAttribute("loginEmp");
		if(loginEmp == null) {  // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		}
		
		employeeService.updateEmployeePw(loginEmp.getEmpNo(), oldPw, newPw);
		
		return "redirect:/employee/empList";
	}

	
	
	
	
	
	// emp login
	@GetMapping("/employee/loginEmp")
	public String loginEmp(HttpSession session) { // session이필요한 로직은 그냥 매게변수로 session을 받자.
		// 이미 로그인 중이라면 "redirect://employee/empList";
		Employee loginEmp = (Employee)session.getAttribute("loginEmp");
		if (loginEmp != null ) {
			return "redirect://employee/empList";
		}
		return "employee/loginEmp";
	}
	@PostMapping("/employee/loginEmp")
	public String loginEmp(HttpSession session, Employee emp) {
		Employee resultEmp = employeeService.login(emp);
		if(resultEmp == null) { // 로그인 실패
			return "employee/loginEmp";
		}
		session.setAttribute("loginEmp", resultEmp);
		return "redirect://employee/empList";
	}
	
	
	// emp logout
	@GetMapping("/employee/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect://employee/loginEmp";
	}
	
	
	
	
	// delete emp
	@GetMapping ("/employee/removeEmp")
	public String removeEmp(@RequestParam("empNo") int empNo ) {
		employeeService.removeEmployee(empNo);
		return "redirect://employee/empList"; // 리스트로 리다이렉트
	}
	
	// delete student 
	@GetMapping("/student/removeStudent")
	public String removeStudent(HttpSession session, @RequestParam("studentNo") int studentNo) {
		
		Employee loginEmp = (Employee) session.getAttribute("loginEmp");	
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		}
		
		studentService.removeStudent(studentNo);	// row == 1이면 삭제성공
		return "redirect:/student/studentList";
	}
		
	// delete teacher 
	@GetMapping("/teacher/removeTeacher")
	public String removeTeacher(HttpSession session, @RequestParam("teacherNo") int teacherNo) {
		
		Employee loginEmp = (Employee) session.getAttribute("loginEmp");	
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		}
		
		teacherService.removeTeacher(teacherNo);	// row == 1이면 삭제성공
		return "redirect:/teacher/teacherList";
	}
		
	
	
	
	// insert Emp
	@GetMapping("/employee/addEmp")
	public String addEmp() {
		return "employee/addEmp"; // forword
	}
	
	@PostMapping("/employee/addEmp")
	public String addEmp(HttpSession session, Model model, Employee employee) {
		Employee loginEmp = (Employee)session.getAttribute("loginEmp");
		if(loginEmp == null) {
			return "redirect:/employee/loginEmp";
		}
		
		String idCheck = idService.getIdCheck(employee.getEmpId());
		if(idCheck != null) {
			model.addAttribute("errorMsg", "중복된ID");
			return "employee/addEmp";
		}
		
		int row = employeeService.addEmployee(employee);
		// row == 1이면 입력성공
		if(row == 0) {
			model.addAttribute("errorMsg","시스템에러로 등록실패");
			return "employee/addEmp";
		}
		return "redirect://employee/empList"; // sendRedirect, CM -> C
	}
	
	// insert student 
	@GetMapping("/student/addStudent")
	public String addStudent(HttpSession session) {
		
		Employee loginEmp = (Employee) session.getAttribute("loginEmp");	
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		}
		
		return "student/addStudent";
	}
	@PostMapping("/student/addStudent")
	public String addStudent(HttpSession session, Model model, Student student) {
		
		Employee loginEmp = (Employee) session.getAttribute("loginEmp");	
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		}
		
		String id = idService.getIdCheck(student.getStudentId());
		if(id != null) { // 입력한 ID가 기존 DB에 존재 시, 다시 회원가입 페이지로 이동
			model.addAttribute("errorMsg", "중복된 ID");
			return "student/addStudent";
		}
		
		int row = studentService.addStudent(student);
		if(row != 1) { // row != 1이면 입력실패
			model.addAttribute("errorMsg", "시스템 에러로 인한 실패");
			return "student/addStudent";
		}
		return "redirect:/student/studentList"; // sendRedict , cm
	}
	
	// insert teacher 
	@GetMapping("/teacher/addTeacher")
	public String addTeacher(HttpSession session) {
		
		Employee loginEmp = (Employee) session.getAttribute("loginEmp");	
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		}
		
		return "teacher/addTeacher";
	}
	@PostMapping("/teacher/addTeacher")
	public String addTeacher(HttpSession session, Model model, Teacher teacher) {
		
		Employee loginEmp = (Employee) session.getAttribute("loginEmp");	
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		}
		
		String id = idService.getIdCheck(teacher.getTeacherId());
		if(id != null) { // 입력한 ID가 기존 DB에 존재 시, 다시 회원가입 페이지로 이동
			model.addAttribute("errorMsg", "중복된 ID");
			return "teacher/addTeacher";
		}
		
		int row = teacherService.addTeacher(teacher);
		if(row != 1) { // row != 1이면 입력실패
			model.addAttribute("errorMsg", "시스템 에러로 인한 실패");
			return "teacher/addTeacher";
		}
		return "redirect:/teacher/teacherList"; // sendRedict , cm
	}
	
	
	
	
	
	// empList
	@GetMapping("/employee/empList")
	public String empList(Model model
							, @RequestParam(value="currentPage", defaultValue = "1") int currentPage
							, @RequestParam(value="rowPerPage", defaultValue="10") int rowPerPage) { 
							// int currentPage = reuqest.getParamenter("currentPage");
		List<Employee> list = employeeService.getEmployeeList(currentPage, rowPerPage);
		// request.setAttribute("list", list);
		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
		return "employee/empList";
	}
	
	
	
	// studentList 
	@GetMapping("/student/studentList")
	public String studentList(HttpSession session, Model model
			, @RequestParam(value="currentPage", defaultValue="1") int currentPage
			, @RequestParam(value="rowPerPage", defaultValue="10") int rowPerPage) {
			// int currentPage = request.getParameter("currentPage");
		
		Employee loginEmp = (Employee) session.getAttribute("loginEmp");	
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		} 
		
		List<Student> list = studentService.getStudentList(currentPage,rowPerPage);
		
		model.addAttribute("list", list); // request.setAttribute("list", list) 기능 (매개변수 model 필요)
		model.addAttribute("currentPage", currentPage);
		return "student/studentList";
	}
	
	// teacherList 
	@GetMapping("/teacher/teacherList")
	public String teacherList(HttpSession session, Model model
			, @RequestParam(value="currentPage", defaultValue="1") int currentPage
			, @RequestParam(value="rowPerPage", defaultValue="10") int rowPerPage) {
			// int currentPage = request.getParameter("currentPage");
		
		Employee loginEmp = (Employee) session.getAttribute("loginEmp");	
		if(loginEmp == null) { // 로그인 되어있지 않다면 로그인페이지로 이동
			return "redirect:/employee/loginEmp";
		} 
		
		List<Teacher> list = teacherService.getTeacherList(currentPage,rowPerPage);
		
		model.addAttribute("list", list); // request.setAttribute("list", list) 기능 (매개변수 model 필요)
		model.addAttribute("currentPage", currentPage);
		return "teacher/teacherList";
		}
}
