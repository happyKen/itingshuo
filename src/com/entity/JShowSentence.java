package com.entity;

import java.util.List;

/**
 * User: zekeng(2815506514@qq.com)
 * Date: 2016-03-25
 * Time: 11:26
 */
public class JShowSentence {


    /**
     * status : 1
     * sentence : [{"sentence_id":"1","course_id":"1","content":"It will be in the place where we always put it.","sen_addr":"Public/Home/tone/record/01.wav","sen_time":"1:23","create_time":"1456453494"}]
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
