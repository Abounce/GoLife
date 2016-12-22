package com.example.administrator.golife.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public class PhotoData {


    /**
     * error_code : 0
     * reason : Success
     * result : {"data":[{"content":"第一次感觉到了雾霾的可怕！大家一定做好防护措施\u2026\u2026","hashId":"16243BC92D8F6EFC0FF92E5DEA222B88","unixtime":1482307518,"updatetime":"2016-12-21 16:05:18","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201612/21/16243BC92D8F6EFC0FF92E5DEA222B88.png"}]}
     */

    private int error_code;
    private String reason;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * content : 第一次感觉到了雾霾的可怕！大家一定做好防护措施……
             * hashId : 16243BC92D8F6EFC0FF92E5DEA222B88
             * unixtime : 1482307518
             * updatetime : 2016-12-21 16:05:18
             * url : http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201612/21/16243BC92D8F6EFC0FF92E5DEA222B88.png
             */

            private String content;
            private String hashId;
            private int unixtime;
            private String updatetime;
            private String url;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHashId() {
                return hashId;
            }

            public void setHashId(String hashId) {
                this.hashId = hashId;
            }

            public int getUnixtime() {
                return unixtime;
            }

            public void setUnixtime(int unixtime) {
                this.unixtime = unixtime;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
