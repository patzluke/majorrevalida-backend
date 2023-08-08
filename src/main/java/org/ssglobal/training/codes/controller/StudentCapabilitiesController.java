package org.ssglobal.training.codes.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.service.StudentCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.EvaluationQuestionAnswer;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.SubmittedSubjectsForEnrollment;
import org.ssglobal.training.codes.tables.pojos.WebsiteActivationToggle;

@RestController
@RequestMapping(value = "/api/student")
public class StudentCapabilitiesController {

	@Autowired
	private StudentCapabilitiesService service;

	@GetMapping(value = "/get/{studentId}")
	public ResponseEntity<UserAndStudent> viewStudentProfile(@PathVariable("studentId") Integer studentId) {
		try {
			UserAndStudent studentProfile = service.viewStudentProfile(studentId);
			if (studentProfile != null) {
				return ResponseEntity.ok(studentProfile);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateStudentProfile(@RequestBody UserAndStudent student) {
		try {
			UserAndStudent updatedStudent = service.updateStudentProfile(student);
			if (updatedStudent != null) {
				return ResponseEntity.ok(updatedStudent);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@GetMapping(value = "/view/course/{studentId}")
	public ResponseEntity<StudentCourseData> viewStudentCourse(@PathVariable("studentId") Integer studentNo) {
		try {
			StudentCourseData studentCourse = service.viewCourse(studentNo);
			if (studentCourse != null) {
				return ResponseEntity.ok(studentCourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/edit/major", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Major> editMajor(@RequestBody Major editedMajor) {
		try {
			Major updatedMajor = service.editMajor(editedMajor);
			if (updatedMajor != null) {
				return ResponseEntity.ok(updatedMajor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/edit/course", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Course> editCourse(@RequestBody Course editedMajor) {
		try {
			Course updatedCourse = service.editCourse(editedMajor);
			if (updatedCourse != null) {
				return ResponseEntity.ok(updatedCourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/edit/program", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Program> editMajor(@RequestBody Program editedProgram) {
		try {
			Program updatedProgram = service.editProgram(editedProgram);
			if (updatedProgram != null) {
				return ResponseEntity.ok(updatedProgram);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/view/attendance/{studentId}")
	public ResponseEntity<StudentAttendance> viewStudentAttendance(@PathVariable("studentId") Integer studentNo) {
		try {
			StudentAttendance studentAttendance = service.viewStudentAttendance(studentNo);
			if (studentAttendance != null) {
				return ResponseEntity.ok(studentAttendance);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/view/grades/{studentId}")
	public ResponseEntity<Grades> viewStudentGrades(@PathVariable("studentId") Integer studentId) {
		try {
			Grades studentGrades = service.viewStudentGrade(studentId);
			if (studentGrades != null) {
				return ResponseEntity.ok(studentGrades);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/get/studentsubjectenrolled/{studentNo}")
	public ResponseEntity<List<Map<String, Object>>> selectAllStudentSubjectEnrolledByStudentNo(
			@PathVariable("studentNo") Integer studentNo) {
		try {
			List<Map<String, Object>> studentGrades = service.selectAllStudentSubjectEnrolledByStudentNo(studentNo);
			if (studentGrades != null) {
				return ResponseEntity.ok(studentGrades);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/get/studentattendance")
	public ResponseEntity<List<Map<String, Object>>> selectStudentAttendanceByAndSubjectAndSectionAndProfessorNoAndDate(
			@RequestParam("subjectTitle") String subjectTitle, @RequestParam("studentNo") Integer studentNo) {
		try {
			List<Map<String, Object>> studentGrades = service
					.selectStudentAttendanceByAndSubjectAndStudentNo(subjectTitle, studentNo);
			if (studentGrades != null) {
				return ResponseEntity.ok(studentGrades);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Student grades
	@GetMapping(value = "/get/studentgrades/{studentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllSubjectGradesOfStudent(
			@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<Map<String, Object>> rooms = service.selectAllSubjectGradesOfStudent(studentNo);
			if (!rooms.isEmpty()) {
				return ResponseEntity.ok(rooms);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Student grades
	@GetMapping(value = "/get/academicyear/{studentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectEnrolledSchoolYearOfStudent(
			@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<Map<String, Object>> rooms = service.selectEnrolledSchoolYearOfStudent(studentNo);
			if (!rooms.isEmpty()) {
				return ResponseEntity.ok(rooms);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Student Schedule
	@GetMapping(value = "/get/studentschedule/{studentNo}/{academicYearId}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectEnrolledSchoolYearOfStudent(
			@PathVariable(name = "studentNo") Integer studentNo,
			@PathVariable(name = "academicYearId") Integer academicYearId) {
		try {

			List<Map<String, Object>> schedule = service.selectScheduleOfStudent(studentNo, academicYearId);
			if (!schedule.isEmpty()) {
				return ResponseEntity.ok(schedule);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Student grades
	@GetMapping(value = "/get/curriculumsubject/{studentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllMajorSubjectsInACurriculumOfStudent(
			@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<Map<String, Object>> curriculumSubjects = new ArrayList<>();
			List<Map<String, Object>> minor = service.selectAllMajorSubjectsInACurriculumOfStudent(studentNo);
			List<Map<String, Object>> major = service.selectAllMinorSubjectsInACurriculumOfStudent(studentNo);
			curriculumSubjects.addAll(minor);
			curriculumSubjects.addAll(major);

			Collections.sort(curriculumSubjects, (o1, o2) -> {
				if (Integer.parseInt(o2.get("subjectCode").toString()) < Integer
						.parseInt(o1.get("subjectCode").toString())) {
					return 1;
				}
				return -1;
			});
			if (!curriculumSubjects.isEmpty()) {
				return ResponseEntity.ok(curriculumSubjects);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Curriculum
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/get/curriculum/{studentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity selectStudentEnrollmentData(
			@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			Map<String, Object> curriculum = service.selectStudentEnrollmentData(studentNo);
			System.out.println(curriculum);
			if (curriculum != null) {
				return ResponseEntity.ok(curriculum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/get/subjectstoenroll/{yearLevel}/{sem}/{studentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllSubjectsToEnrollPerYearAndSem(
			@PathVariable(name = "yearLevel") Integer yearLevel, @PathVariable(name = "sem") Integer sem,
			@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<Map<String, Object>> subjects = new ArrayList<>();
			List<Map<String, Object>> minor = service.selectAllMajorSubjectsToEnrollPerYearAndSem(yearLevel, sem);
			List<Map<String, Object>> major = service.selectAllMinorSubjectsToEnrollPerYearAndSem(yearLevel, sem);
			subjects.addAll(minor);
			subjects.addAll(major);			
			
			List<Map<String, Object>> failedSubjects = new ArrayList<>();
			List<Map<String, Object>> failedMajorSubjects = service.selectAllFailedMajorSubjectPreviouslyOfStudent(studentNo);
			List<Map<String, Object>> failedMinorSubjects = service.selectAllFailedMinorSubjectPreviouslyOfStudent(studentNo);
			failedSubjects.addAll(failedMajorSubjects);
			failedSubjects.addAll(failedMinorSubjects);
			
			List<Map<String, Object>> passedSubjects = service.selectAllPassedSubjectOfStudent(studentNo);

			passedSubjects.forEach(subj -> {
				for (Iterator iterator = failedSubjects.iterator(); iterator.hasNext();) {
					Map<String, Object> failedSubj = (Map<String, Object>) iterator.next();
					if (subj.get("subjectCode").equals(failedSubj.get("subjectCode"))) {
						iterator.remove();
					}
				}
			});
			
			System.out.println(failedSubjects + "faileds");
			
			failedSubjects.forEach(failedSubj -> {
				for (Iterator iterator = subjects.iterator(); iterator.hasNext();) {
					Map<String, Object> subj = (Map<String, Object>) iterator.next();
					if (failedSubj.get("subjectCode").equals(subj.get("preRequisite"))) {
						iterator.remove();
					}
				}
			});
			
			if (!subjects.isEmpty()) {
				return ResponseEntity.ok(subjects);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/get/subjectstoenroll/backlog/{studentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity selectAllFailedSubjectPreviouslyOfStudent(@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<Map<String, Object>> failedSubjects = new ArrayList<>();
			List<Map<String, Object>> failedMajorSubjects = service.selectAllFailedMajorSubjectPreviouslyOfStudent(studentNo);
			List<Map<String, Object>> failedMinorSubjects = service.selectAllFailedMinorSubjectPreviouslyOfStudent(studentNo);
			failedSubjects.addAll(failedMajorSubjects);
			failedSubjects.addAll(failedMinorSubjects);
			List<Map<String, Object>> passedSubjects = service.selectAllPassedSubjectOfStudent(studentNo);
					
			passedSubjects.forEach(subj -> {
				for (Iterator iterator = failedSubjects.iterator(); iterator.hasNext();) {
					Map<String, Object> failedSubj = (Map<String, Object>) iterator.next();
					if (subj.get("subjectCode").equals(failedSubj.get("subjectCode"))) {
						iterator.remove();
					}
				}
			});	
			
			List<Map<String, Object>> tempFailedSubjects = new ArrayList<>();			
			tempFailedSubjects.forEach(subj -> {
				for (Iterator iterator = failedSubjects.iterator(); iterator.hasNext();) {
					Map<String, Object> failedSubj = (Map<String, Object>) iterator.next();
					if (subj.get("subjectCode").equals(failedSubj.get("preRequisite"))) {
						iterator.remove();
					}
				}
			});	
			
			List<Map<String, Object>> backLogsMajorSubjects = service.selectListOfBackLogsMajorSubject(studentNo);
			failedSubjects.addAll(backLogsMajorSubjects);
			if (!failedSubjects.isEmpty()) {
				return ResponseEntity.ok(failedSubjects);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Submitted Subjects For enrollment
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/insert/submittedsubjectsforenrollment", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity insertIntoSubmittedSubjectsForEnrollment(
			@RequestBody List<SubmittedSubjectsForEnrollment> submittedSubjectsForEnrollment) {
		try {
			boolean subjectsForEnrollment = service
					.insertIntoSubmittedSubjectsForEnrollment(submittedSubjectsForEnrollment);
			if (subjectsForEnrollment) {
				return ResponseEntity.ok(subjectsForEnrollment);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/get/submittedsubjectsforenrollment/{enrollmentId}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity checkIfThereIsSubmittedSubjectsForEnrollment(
			@PathVariable(name = "enrollmentId") Integer enrollmentId) {
		try {
			List<SubmittedSubjectsForEnrollment> subjects = service
					.checkIfThereIsSubmittedSubjectsForEnrollment(enrollmentId);
			if (!subjects.isEmpty()) {
				return ResponseEntity.ok(subjects);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/get/evaluationquestionanswer/{studentNo}/{subjectCode}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity selectAllEvaluationQuestionAnswerStudentNoAndSubjectCode(@PathVariable(name = "studentNo") Integer studentNo,
																				   @PathVariable(name = "subjectCode") Integer subjectCode) {
		try {
			List<Map<String, Object>> questionList = service
					.selectAllEvaluationQuestionAnswerStudentNoAndSubjectCode(studentNo, subjectCode);
			if (!questionList.isEmpty()) {
				return ResponseEntity.ok(questionList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/evaluationquestionanswer", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateEvaluationQuestionAnswer(@RequestBody List<EvaluationQuestionAnswer> payload) {		
		boolean updatedStudentGrades = service.updateEvaluationQuestionAnswer(payload);
		try {
			if (updatedStudentGrades) {
				return ResponseEntity.ok(updatedStudentGrades);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().body("something went wrong");
	}
	
	@GetMapping(value = "/get/websiteactivationtoggle", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<WebsiteActivationToggle> selectWebsiteActivationToggle() {
		try {
			WebsiteActivationToggle toggle = service.selectWebsiteActivationToggle();
			System.out.println(toggle);
			if (toggle != null) {
				return ResponseEntity.ok(toggle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.notFound().build();
	}
}
