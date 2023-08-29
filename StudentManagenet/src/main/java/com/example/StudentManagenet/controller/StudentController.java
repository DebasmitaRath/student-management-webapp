package com.example.StudentManagenet.controller;

@Controller
@RequestMapping("/students")
public class StudentController 
{
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("")
	public String findAll(Model model)
	{
		List<Student> students = studentService.findAll();
		model.addAttribute("students", students);
		return "student-list";
	}
	
	@GetMapping("/add")
	public String add(Model model)
	{
		Student theStudent = new Student();
		model.addAttribute("theStudent", theStudent);
		return "student-form";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("theStudent") Student theStudent)
	{
		studentService.save(theStudent);
		return "redirect:/students";
	}
	
	@GetMapping("/{id}/courses")
	public String viewCourses(@PathVariable("id") int id, Model model)
	{
		Student student = studentService.findById(id);
		List<Course> courses = student.getCourses();
		if(courses.isEmpty()) {
			return "redirect:/students/" + id + "/addCourses";
		}
		model.addAttribute("remove_id", id);
		model.addAttribute("courses", courses);
		return "course-list";
	}
	
	@GetMapping("/{id}/addCourses")
	public String addCourses(@PathVariable("id") int id, Model model)
	{
		List<Course> studentCourses = studentService.findById(id).getCourses();
		List<Course> courses = courseService.findAll();
		List<Course> remainingCourses = new ArrayList<Course>();
		for(Course c: courses)
		{
			if(!studentCourses.contains(c)) {
				remainingCourses.add(c);
			}
		}
		model.addAttribute("courses", remainingCourses);
		model.addAttribute("add_id", id);
		return "course-list";
	}
	
	@GetMapping("/{sid}/addCourse")
	public String addCourse(@PathVariable("sid") int sid, @RequestParam("cid") int cid)
	{
		Student student = studentService.findById(sid);
		Course course = courseService.findById(cid);
		student.addCourse(course);
		studentService.save(student);
		course.addStudent(student);
		courseService.save(course);
		return "redirect:/students/"+sid+"/courses";
	}
	
	@GetMapping("/{sid}/removeCourse")
	public String removeCourse(@PathVariable("sid") int sid, @RequestParam("cid") int cid)
	{
		Student student = studentService.findById(sid);
		Course course = courseService.findById(cid);
		student.removeCourse(course);
		studentService.save(student);
		course.removeStudent(student);
		courseService.save(course);
		return "redirect:/students/"+sid+"/courses";
	}
}
