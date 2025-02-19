package com.alibaba.rsocket.metadata;

import com.alibaba.rsocket.encoding.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * application metadata, json format
 *
 * @author leijuan
 */
public class AppMetadata implements MetadataAware {
    /**
     * instance hashcode ID, auto generated by credential and UUID
     */
    private Integer id;
    /**
     * application uuid, almost uuid
     */
    private String uuid;
    /**
     * power rating. big number means bigger power to process requests
     */
    private int powerRating = 1;
    /**
     * app name
     */
    private String name;
    /**
     * name space
     */
    private String nameSpace;
    /**
     * description
     */
    private String description;
    /**
     * device information
     */
    private String device;
    /**
     * rsocket schema
     */
    private Map<Integer, String> rsocketPorts;
    /**
     * ip
     */
    private String ip;
    /**
     * connected brokers
     */
    private List<String> brokers;
    private List<String> p2pServices;
    /**
     * topology, such as intranet or internet
     */
    private String topology = "intranet";
    /**
     * secure or not
     */
    private boolean secure = false;
    /**
     * web port
     */
    private int webPort;
    /**
     * management port for Spring Boot actuator
     */
    private int managementPort;

    /**
     * sdk and RSocket protocol version
     */
    private String sdk = "Alibaba-RSocket-1.1.2/1.0.0";

    /**
     * developers, format as email list: xxx <xxx@foobar.com>, yyy <yyy@foobar.com>
     */
    private String developers;

    /**
     * metadata
     */
    private Map<String, String> metadata;

    /**
     * humans.md from classpath
     */
    private String humansMd;
    /**
     * connected timestamp
     */
    private Date connectedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPowerRating() {
        return powerRating;
    }

    public void setPowerRating(int powerRating) {
        if (powerRating <= 0) {
            this.powerRating = 1;
        } else {
            this.powerRating = powerRating;
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHumansMd() {
        return humansMd;
    }

    public void setHumansMd(String humansMd) {
        this.humansMd = humansMd;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<Integer, String> getRsocketPorts() {
        return rsocketPorts;
    }

    public void setRsocketPorts(Map<Integer, String> rsocketPorts) {
        this.rsocketPorts = rsocketPorts;
    }

    public List<String> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<String> brokers) {
        this.brokers = brokers;
    }

    public List<String> getP2pServices() {
        return p2pServices;
    }

    public void setP2pServices(List<String> p2pServices) {
        this.p2pServices = p2pServices;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(String topology) {
        this.topology = topology;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public int getWebPort() {
        return webPort;
    }

    public void setWebPort(int webPort) {
        this.webPort = webPort;
    }

    public int getManagementPort() {
        return managementPort;
    }

    public void setManagementPort(int managementPort) {
        this.managementPort = managementPort;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void addMetadata(String name, String value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(name, value);
    }

    public String getMetadata(String name) {
        if (this.metadata == null) {
            return null;
        }
        return metadata.get(name);
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public Date getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(Date connectedAt) {
        this.connectedAt = connectedAt;
    }

    @Override
    public RSocketMimeType rsocketMimeType() {
        return RSocketMimeType.Application;
    }

    @Override
    @JsonIgnore
    public String getMimeType() {
        return RSocketMimeType.Application.getType();
    }

    @Override
    @JsonIgnore
    public ByteBuf getContent() {
        try {
            return JsonUtils.toJsonByteBuf(this);
        } catch (Exception e) {
            return Unpooled.EMPTY_BUFFER;
        }
    }

    @Override
    public void load(ByteBuf byteBuf) throws Exception {
        JsonUtils.updateJsonValue(byteBuf, this);
    }

    public static AppMetadata from(ByteBuf content) {
        AppMetadata appMetadata = new AppMetadata();
        try {
            appMetadata.load(content);
        } catch (Exception ignore) {

        }
        return appMetadata;
    }
}
