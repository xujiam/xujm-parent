package top.xujm.modules.oss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.xujm.modules.oss.model.Oss;

/**
 * @author Xujm
 */
public interface OssRepository extends JpaRepository<Oss,Integer> {

    /**
     * 通过类型码查询数据
     * @param typeCode 类型码
     * @return oss
     */
    Oss findByTypeCode(String typeCode);

}
