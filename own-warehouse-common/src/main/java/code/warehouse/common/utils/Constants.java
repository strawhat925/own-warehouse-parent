package code.warehouse.common.utils;

/**
 * 常量集合
 * package code.warehouse.boss.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 16:50
 **/
public class Constants {

    /**
     * 系统管理员
     */
    public static final int SUPER_ADMIN = 1;


    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),

        /**
         * 菜单
         */
        MENU(1),

        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * 用户状态
     */
    public enum UserStatus {
        /**
         * 冻结
         */
        INVALID(0),

        /**
         * 正常
         */
        NORMAL(1);

        private int value;

        private UserStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * 定时任务
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

}

