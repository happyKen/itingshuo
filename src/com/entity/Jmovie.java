package com.entity;
import java.util.List;

/**
 * User: zekeng(2815506514@qq.com)
 * Date: 2016-03-24
 * Time: 09:52
 */
public class Jmovie {


    /**
     * movie : [{"movie_id":"1","movie_name":"功夫熊猫","cover_addr":"Public/Home/movie/cover/gongfupanda/gongfu.png","emotion_id":"1","movie_introduction":"《功夫熊猫》是一部以中国功夫为主题的美国动作喜剧电影，影片以中国古代为背景，其景观、布景、服装以至食物均充满中国元素。故事讲述了一只笨拙的熊猫立志成为武林高手的故事},{"movie_id":"2","movie_name":"娉拌开鐔�,"cover_addr":"Public/Home/movie/cover/taidixiong.png","emotion_id":"1","movie_introduction":"浜屽崄澶氬勾鍓嶏紝涓�釜绾湡鍠勮壇鐨勭敺瀛╃害缈帮紝鍥犱负瀹虫�娌℃湁鏈嬪弸鐨勫鐙紝鑰岃浜嗕竴涓効锛屽笇鏈涗粬鐨勫疂璐濇嘲杩唺鍙互娲昏繃鏉ワ紝鎴愪负浠栫湡姝ｇ殑鏈嬪弸銆傜害缈版病鏈夋兂鍒扮殑鏄紝鏌愮绁炵鐨勫姏閲忥紝璁╀粬鐨勬効鏈涙垚鐪熶簡銆傜害缈板拰娉拌开鐔婁竴璧风敓娲伙紝搴﹁繃浜嗘涔愮殑绔ュ勾銆傜洿鍒帮紝浜屽崄澶氬勾鍚庯紝绾︾堪鎴愪负浜嗕竴涓棤鎵�簨浜嬬殑骞磋交浜猴紝娆箰缁堜簬鍙樻垚浜嗛夯鐑︺�宸茶繃鑰岀珛涔嬪勾鐨勭害缈颁緷鐒惰繕鏄崟韬竴浜猴紝姣忓ぉ鍜屾嘲杩唺涓�捣杩囩潃閱夌敓姊︽鐨勭敓娲汇�鐩村埌绾︾堪閬囦笂浜嗚浠栦竴瑙侀挓鎯呯殑濮戝鍔充附锛屼粬鎵嶅喅蹇冭鏀瑰彉鑷繁銆傚彲鏄紝杩欐椂鍊欎紮浼翠笌鎭嬩汉鐨勭煕鐩惧嵈閫愭笎鏄剧幇锛屼袱绉嶄笉鍚岀殑鐢熸椿鏂瑰紡璁╃害缈伴毦浠ユ妷鎷┿�鏈�悗缁忓巻浜嗚澶氭尝鎶橈紝鍦ㄧ埍鎯呭拰鍙嬭皧涔嬮棿锛岀害缈扮粓浜庢壘鍒颁竴涓井濡欑殑骞宠　鐐�}]
     * status : 1
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
         * movie_id : 1
         * movie_name : 功夫熊猫
         * cover_addr : Public/Home/movie/cover/gongfupanda/gongfu.png
         * emotion_id : 1
         * movie_introduction : 《功夫熊猫》是一部以中国功夫为主题的美国动作喜剧电影，影片以中国古代为背景，其景观、布景、服装以至食物均充满中国元素。故事讲述了一只笨拙的熊猫立志成为武林高手的故事。
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
            private String movie_id;
            private String movie_name;
            private String cover_addr;
            private String emotion_id;
            private String movie_introduction;

            public void setMovie_id(String movie_id) {
                this.movie_id = movie_id;
            }

            public void setMovie_name(String movie_name) {
                this.movie_name = movie_name;
            }

            public void setCover_addr(String cover_addr) {
                this.cover_addr = cover_addr;
            }

            public void setEmotion_id(String emotion_id) {
                this.emotion_id = emotion_id;
            }

            public void setMovie_introduction(String movie_introduction) {
                this.movie_introduction = movie_introduction;
            }

            public String getMovie_id() {
                return movie_id;
            }

            public String getMovie_name() {
                return movie_name;
            }

            public String getCover_addr() {
                return cover_addr;
            }

            public String getEmotion_id() {
                return emotion_id;
            }

            public String getMovie_introduction() {
                return movie_introduction;
            }
        }
    }
}
