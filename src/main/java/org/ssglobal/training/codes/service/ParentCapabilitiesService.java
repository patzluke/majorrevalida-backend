package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.model.ParentStudentAttendanceCopy;
import org.ssglobal.training.codes.model.StudentGrades;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;

public interface ParentCapabilitiesService {

	UserAndParent selectParent(Integer parentNo);

	UserAndParent updateParentInfo(UserAndParent parent);

	List<UserAndStudent> selectAllChildren(Integer parentNo);

	List<StudentGrades> selectAllGrades(Integer studentNo);

	List<Map<String, Object>> selectEnrolledSchoolYearOfStudent(Integer studentNo);

	public List<ParentStudentAttendanceCopy> selectStudentAttendanceByAttendanceDateDistinct(Integer studentNo);
}
