package com.synpore.utils;

public class CommonBusinessFlag {

    private long value = 0;

    public CommonBusinessFlag markedFlag(FlagPosition position) {
        this.value = value | (1 << position.getPosition());
        return this;
    }

    public boolean isSpecificBusiness(FlagPosition position) {
        return (value >> position.getPosition() & 1) == 1;
    }

    public String getBusinessStr() {
        return Long.toBinaryString(value);
    }

    enum FlagPosition {
        BUSI_0(0, "business0"),
        BUSI_1(1, "business1"),
        BUSI_2(2, "business2"),
        BUSI_3(3, "business3"),
        BUSI_4(4, "business4"),
        ;

        FlagPosition(int position, String desc) {
            this.position = position;
            this.desc = desc;
        }

        private int position;
        private String desc;

        public int getPosition() {
            return position;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static void main(String[] args) {
        CommonBusinessFlag flag = new CommonBusinessFlag();
        flag.markedFlag(FlagPosition.BUSI_1).markedFlag(FlagPosition.BUSI_4).markedFlag(FlagPosition.BUSI_0);
        System.out.println(flag.getBusinessStr());
        System.out.println(flag.isSpecificBusiness(FlagPosition.BUSI_4));
        System.out.println(flag.isSpecificBusiness(FlagPosition.BUSI_1));
        System.out.println(flag.isSpecificBusiness(FlagPosition.BUSI_2));
        System.out.println(flag.isSpecificBusiness(FlagPosition.BUSI_0));
        System.out.println(flag.isSpecificBusiness(FlagPosition.BUSI_3));
    }
}
