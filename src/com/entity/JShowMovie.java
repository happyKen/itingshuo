package com.entity;

import java.util.List;

/**
 * User: zekeng(2815506514@qq.com)
 * Date: 2016-03-25
 * Time: 10:33
 */
public class JShowMovie {

    /**
     * status : 1
     * movie : [{"segment_id":"1","segment_name":"po×öÃÎ","content":"-Po: Coming£¡ Sorry, dad.##-Dad: Sorry doesn't make the noodles. What were you doing up there? All that noise¡£## -Po: Nothing. I just had a crazy dream.##-Dad: About what? What were you dreaming about?##-Po: What was I...? I was dreaming about... ...noodles.##-Dad: Noodles? You were really dreaming about noodles?##-Po: Yeah. What else would I be dreaming about? Careful£¡That soup is sharp.##-Dad: Oh, happy day! My son finally having the noodle dream!You don't know how long I've been waiting for this moment. ","segment_addr":"Public/Home/movie/video/Happiness_panda_00_03_24-00_04_34/Happiness_panda.mp4","segment_cover":"Public/Home/movie/cover/gongfupanda/gongfu.png","segment_time":"0:15","movie_id":"1","emotion_id":"1","create_time":"1456453494"},{"segment_id":"2","segment_name":"Ãæ¹ÝÓÉÀ´","content":"My son finally having the noodle dream! ","segment_addr":"Public/Home/movie/video/Happiness_panda_00_03_24-00_04_34/Happiness_panda_sentence1.mp4","segment_cover":"Public/Home/movie/cover/gongfupanda/1.jpg","segment_time":"0:03","movie_id":"1","emotion_id":"1","create_time":"1456453494"},{"segment_id":"3","segment_name":"ÐÜÃ¨×íÁË","content":"You don't know how long I've been waiting for this moment. ","segment_addr":"Public/Home/movie/video/Happiness_panda_00_03_24-00_04_34/Happiness_panda_sentence2.mp4","segment_cover":"Public/Home/movie/cover/gongfupanda/2.jpg","segment_time":"0:03","movie_id":"1","emotion_id":"1","create_time":"1456886576"},{"segment_id":"4","segment_name":"ÐÜÃ¨ÓÖ×í","content":"You are almost ready to be entrusted with the secret ingredient of my Secret Ingredient Soup. ","segment_addr":"Public/Home/movie/video/Happiness_panda_00_03_24-00_04_34/Happiness_panda_sentence3.mp4","segment_cover":"Public/Home/movie/cover/gongfupanda/3.jpg","segment_time":"0:03","movie_id":"1","emotion_id":"1","create_time":"1456886576"},{"segment_id":"5","segment_name":"ÐÜÃ¨ÔÙ×í","content":"Then you will fulfill your destiny and take over the restaurant! ","segment_addr":"Public/Home/movie/video/Happiness_panda_00_03_24-00_04_34/Happiness_panda_sentence4.mp4","segment_cover":"Public/Home/movie/cover/gongfupanda/4.jpg","segment_time":"0:03","movie_id":"1","emotion_id":"1","create_time":"1456886576"},{"segment_id":"6","segment_name":"ÐÜÃ¨×í","content":"As I took it over from my father, who took it from his father\u2026who won it from a friend in mahjong.","segment_addr":"Public/Home/movie/video/Happiness_panda_00_03_24-00_04_34/Happiness_panda_sentence5.mp4","segment_cover":"Public/Home/movie/cover/gongfupanda/5.jpg","segment_time":"0:03","movie_id":"1","emotion_id":"1","create_time":"1456886576"}]
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
         * segment_id : 1
         * segment_name : po×öÃÎ
         * content : -Po: Coming£¡ Sorry, dad.##-Dad: Sorry doesn't make the noodles. What were you doing up there? All that noise¡£## -Po: Nothing. I just had a crazy dream.##-Dad: About what? What were you dreaming about?##-Po: What was I...? I was dreaming about... ...noodles.##-Dad: Noodles? You were really dreaming about noodles?##-Po: Yeah. What else would I be dreaming about? Careful£¡That soup is sharp.##-Dad: Oh, happy day! My son finally having the noodle dream!You don't know how long I've been waiting for this moment.
         * segment_addr : Public/Home/movie/video/Happiness_panda_00_03_24-00_04_34/Happiness_panda.mp4
         * segment_cover : Public/Home/movie/cover/gongfupanda/gongfu.png
         * segment_time : 0:15
         * movie_id : 1
         * emotion_id : 1
         * create_time : 1456453494
         */

        private List<MovieEntity> movie;

        public void setStatus(int status) {
            this.status = status;
        }

        public void setMovie(List<MovieEntity> movie) {
            this.movie = movie;
        }

        public int getStatus() {
            return status;
        }

        public List<MovieEntity> getMovie() {
            return movie;
        }

        public static class MovieEntity {
            private String segment_id;
            private String segment_name;
            private String content;
            private String segment_addr;
            private String segment_cover;
            private String segment_time;
            private String movie_id;
            private String emotion_id;
            private String create_time;

            public void setSegment_id(String segment_id) {
                this.segment_id = segment_id;
            }

            public void setSegment_name(String segment_name) {
                this.segment_name = segment_name;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setSegment_addr(String segment_addr) {
                this.segment_addr = segment_addr;
            }

            public void setSegment_cover(String segment_cover) {
                this.segment_cover = segment_cover;
            }

            public void setSegment_time(String segment_time) {
                this.segment_time = segment_time;
            }

            public void setMovie_id(String movie_id) {
                this.movie_id = movie_id;
            }

            public void setEmotion_id(String emotion_id) {
                this.emotion_id = emotion_id;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getSegment_id() {
                return segment_id;
            }

            public String getSegment_name() {
                return segment_name;
            }

            public String getContent() {
                return content;
            }

            public String getSegment_addr() {
                return segment_addr;
            }

            public String getSegment_cover() {
                return segment_cover;
            }

            public String getSegment_time() {
                return segment_time;
            }

            public String getMovie_id() {
                return movie_id;
            }

            public String getEmotion_id() {
                return emotion_id;
            }

            public String getCreate_time() {
                return create_time;
            }
        }
    }
}
