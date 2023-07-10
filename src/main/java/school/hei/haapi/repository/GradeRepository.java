package school.hei.haapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.Exam;
import school.hei.haapi.model.Grade;
import school.hei.haapi.model.StudentCourse;
import school.hei.haapi.model.User;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {
  List<Grade> getGradeById(String id);

  Grade getGradeByExamId(String id);

  List<Grade> getGradeByStudentCourse(StudentCourse studentCourse);

  List<Grade> getGradeByExamId(Exam exam);
}
