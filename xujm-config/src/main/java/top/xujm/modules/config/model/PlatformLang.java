package top.xujm.modules.config.model;

import top.xujm.core.base.BaseConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = "xujm_platform_lang", schema = BaseConstants.DATABASE_SCHEMA, catalog = "")
public class PlatformLang {
    private int id;
    private String langKey;
    private String langCode;
    private String langContext;
    private byte langType;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lang_key", nullable = false, length = 20)
    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    @Basic
    @Column(name = "lang_code", nullable = false, length = 10)
    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    @Basic
    @Column(name = "lang_context", nullable = false, length = 200)
    public String getLangContext() {
        return langContext;
    }

    public void setLangContext(String langContext) {
        this.langContext = langContext;
    }

    @Basic
    @Column(name = "lang_type", nullable = false)
    public byte getLangType() {
        return langType;
    }

    public void setLangType(byte langType) {
        this.langType = langType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformLang that = (PlatformLang) o;
        return id == that.id &&
                langType == that.langType &&
                Objects.equals(langKey, that.langKey) &&
                Objects.equals(langCode, that.langCode) &&
                Objects.equals(langContext, that.langContext);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, langKey, langCode, langContext, langType);
    }
}
