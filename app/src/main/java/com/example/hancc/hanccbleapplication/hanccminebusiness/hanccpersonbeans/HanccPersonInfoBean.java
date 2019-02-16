package com.example.hancc.hanccbleapplication.hanccminebusiness.hanccpersonbeans;

public class HanccPersonInfoBean {
    // 姓名
    public final String name;
    // 年龄
    public final Integer age;
    // 身高
    public final Float height;
    // 体重
    public final Float weight;
    //
    public final String address;

    private HanccPersonInfoBean(Builder builder) {
        name = builder.name;
        age = builder.age;
        height = builder.height;
        weight = builder.weight;
        address = builder.address;
    }


    public static final class Builder {
        private String name;
        private Integer age;
        private Float height;
        private Float weight;
        private String address;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder age(Integer val) {
            age = val;
            return this;
        }

        public Builder height(Float val) {
            height = val;
            return this;
        }

        public Builder weight(Float val) {
            weight = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

                                      public HanccPersonInfoBean build() {
            return new HanccPersonInfoBean(this);
        }
    }
}
