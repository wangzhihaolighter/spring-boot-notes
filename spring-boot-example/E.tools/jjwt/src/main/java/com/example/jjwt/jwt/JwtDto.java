package com.example.jjwt.jwt;

import java.util.Date;

public class JwtDto {
    /**
     * 编号
     */
    private String id;
    /**
     * 主题
     */
    private String subject;
    /**
     * 受众
     */
    private String audience;
    /**
     * 签发人
     */
    private String issuer;
    /**
     * 签发时间
     */
    private Date issuedAt;
    /**
     * 生效时间
     */
    private Date notBefore;
    /**
     * 过期时间
     */
    private Date expiration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "JwtDto{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", audience='" + audience + '\'' +
                ", issuer='" + issuer + '\'' +
                ", issuedAt=" + issuedAt +
                ", notBefore=" + notBefore +
                ", expiration=" + expiration +
                '}';
    }
}
