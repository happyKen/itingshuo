package com.entity;

import java.util.List;

/**
 * User: zekeng(2815506514@qq.com)
 * Date: 2016-03-27
 * Time: 11:12
 */
public class JCourse {


    /**
     * status : 1
     * course : [{"course_id":"1","course_name":"课程1","course_introduction":"课程1的介绍blablabla","course_num":"4"},{"course_id":"2","course_name":"课程2","course_introduction":"课程2的介绍blablabla","course_num":"2"}]
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int status;
        /**
         * course_id : 1
         * course_name : 课程1
         * course_introduction : 课程1的介绍blablabla
         * course_num : 4
         */

        private List<CourseEntity> course;

        public void setStatus(int status) {
            this.status = status;
        }

        public void setCourse(List<CourseEntity> course) {
            this.course = course;
        }

        public int getStatus() {
            return status;
        }

        public List<CourseEntity> getCourse() {
            return course;
        }

        public static class CourseEntity {
            private String course_id;
            private String course_name;
            private String course_introduction;
            private String course_num;

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public void setCourse_name(String course_name) {
                this.course_name = course_name;
            }

            public void setCourse_introduction(String course_introduction) {
                this.course_introduction = course_introduction;
            }

            public void setCourse_num(String course_num) {
                this.course_num = course_num;
            }

            public String getCourse_id() {
                return course_id;
            }

            public String getCourse_name() {
                return course_name;
            }

            public String getCourse_introduction() {
                return course_introduction;
            }

            public String getCourse_num() {
                return course_num;
            }
        }
    }
}
