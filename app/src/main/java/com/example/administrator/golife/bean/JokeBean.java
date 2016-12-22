package com.example.administrator.golife.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */
public class JokeBean {

    /**
     * error_code : 0
     * reason : Success
     * result : {"data":[{"content":"他们说捧腹可以让人学到许多知识，还能学会驾驶，因为里面有许多老同机。所以我就进来了。第一步我该干吗？？干嘛？？干马？？？","hashId":"a4c3cb821935e693745e834433c9c45f","unixtime":1482375830,"updatetime":"2016-12-22 11:03:50"}]}
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
             * content : 他们说捧腹可以让人学到许多知识，还能学会驾驶，因为里面有许多老同机。所以我就进来了。第一步我该干吗？？干嘛？？干马？？？
             * hashId : a4c3cb821935e693745e834433c9c45f
             * unixtime : 1482375830
             * updatetime : 2016-12-22 11:03:50
             */

            private String content;
            private String hashId;
            private int unixtime;
            private String updatetime;

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
        }
    }
}
