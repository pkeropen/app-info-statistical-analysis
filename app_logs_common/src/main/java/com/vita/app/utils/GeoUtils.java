package com.vita.app.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.InetAddress;

public class GeoUtils {

    private static Reader reader;

    /**
     * 获取国家数据
     *
     * @param ip
     * @return
     */
    public static String getCountry(String ip) {
        try {
            Resource resource = new ClassPathResource("GeoLite2-City.mmdb");
            reader = new Reader(resource.getFile());

            if (reader != null) {
                JsonNode node = reader.get(InetAddress.getByName(ip));

                if (node != null) {
                    //国家
                    JsonNode countryNode = node.get("country");

                    if (countryNode != null) {

                        JsonNode namesNode = countryNode.get("names");

                        if (namesNode != null) {
                            JsonNode zhNode = namesNode.get("zh-CN");

                            if (zhNode != null) {
                                return zhNode.textValue();
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 获取省份数据
     *
     * @param ip
     * @return
     */
    public static String getProvince(String ip) {
        try {
            Resource resource = new ClassPathResource("GeoLite2-City.mmdb");
            reader = new Reader(resource.getFile());

            if (reader != null) {
                JsonNode node = reader.get(InetAddress.getByName(ip));

                if (node != null) {
                    JsonNode subdivisionsNode = reader.get(InetAddress.getByName(ip));
                    if (subdivisionsNode != null) {
                        JsonNode areaNode = subdivisionsNode.get(0);

                        if (areaNode != null) {
                            JsonNode namesNode = areaNode.get("names");

                            if (namesNode != null) {
                                JsonNode zhNode = namesNode.get("zh-CN");

                                if (zhNode != null) {
                                    return zhNode.textValue();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }
}
