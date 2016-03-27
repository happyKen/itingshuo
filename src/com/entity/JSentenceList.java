package com.entity;

import java.util.List;

/**
 * User: zekeng(2815506514@qq.com)
 * Date: 2016-03-25
 * Time: 11:02
 */
public class JSentenceList {

    /**
     * status : 1
     * sentence : [{"sentence_id":"1","course_id":"1","content":"It will be in the place where we always put it.","sen_addr":"Public/Home/tone/record/01.wav","sen_time":"1:23","create_time":"1456453494"},{"sentence_id":"2","course_id":"1","content":"It will be in the place where we always put it.","sen_addr":"Public/Home/tone/record/03.wav","sen_time":"2:13","create_time":"1456453494"},{"sentence_id":"3","course_id":"1","content":"The clothes are lying on the fridge","sen_addr":"Public/Home/tone/record/05.wav","sen_time":"2:31","create_time":"1456453494"},{"sentence_id":"4","course_id":"1","content":"The clothes are lying on the fridge","sen_addr":"Public/Home/tone/record/06.wav","sen_time":"3:12","create_time":"1456453494"}]
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
         * sentence_id : 1
         * course_id : 1
         * content : It will be in the place where we always put it.
         * sen_addr : Public/Home/tone/record/01.wav
         * sen_time : 1:23
         * create_time : 1456453494
         */

        private List<SentenceEntity> sentence;

        public void setStatus(int status) {
            this.status = status;
        }

        public void setSentence(List<SentenceEntity> sentence) {
            this.sentence = sentence;
        }

        public int getStatus() {
            return status;
        }

        public List<SentenceEntity> getSentence() {
            return sentence;
        }

        public static class SentenceEntity {
            private String sentence_id;
            private String course_id;
            private String content;
            private String sen_addr;
            private String sen_time;
            private String create_time;

            public void setSentence_id(String sentence_id) {
                this.sentence_id = sentence_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setSen_addr(String sen_addr) {
                this.sen_addr = sen_addr;
            }

            public void setSen_time(String sen_time) {
                this.sen_time = sen_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getSentence_id() {
                return sentence_id;
            }

            public String getCourse_id() {
                return course_id;
            }

            public String getContent() {
                return content;
            }

            public String getSen_addr() {
                return sen_addr;
            }

            public String getSen_time() {
                return sen_time;
            }

            public String getCreate_time() {
                return create_time;
            }
        }
    }
}
