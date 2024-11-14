package com.weilai.aeron.enums;

import java.util.Arrays;

public enum CloudProvider {
    ALI_CLOUD("ali", "Alibaba"),
    AWS("aws", "Amazon"),
    AZURE("azure", "Microsoft"),
    GCP("gcp", "Google"),
    TENCENT_CLOUD("tencent", "Tencent");

    private final String shortName;
    private final String companyName;

    CloudProvider(String shortName, String companyName) {
        this.shortName = shortName;
        this.companyName = companyName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public static CloudProvider of(String provider) {

        CloudProvider cloudProvider = Arrays.stream(CloudProvider.values()).filter(it ->
                it.name().equalsIgnoreCase(provider)
        ).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown provider: " + provider));

        return cloudProvider;
    }

    public static String getShortName(String provider) {
        CloudProvider cloudProvider = of(provider);
        return cloudProvider.getShortName();
    }

    public static String getCompanyName(String provider) {
        CloudProvider cloudProvider = of(provider);
        return cloudProvider.getCompanyName();
    }

    public static void main(String[] args) {
        // 测试示例
        System.out.println("Short Name of ALI_CLOUD: " + getShortName("ALI_CLOUD"));
        System.out.println("Company Name of ALI_CLOUD: " + getCompanyName("ALI_CLOUD"));

        System.out.println("Short Name of AWS: " + getShortName("AWS"));
        System.out.println("Company Name of AWS: " + getCompanyName("AWS"));

        System.out.println(ALI_CLOUD);
        System.out.println(AWS);
    }
}
