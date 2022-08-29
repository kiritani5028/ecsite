package jp.co.internous.ecsite.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.internous.ecsite.model.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	//「:変数名」はバインド変数といい、@Paramで指定できます。
	@Query(value="SELECT * FROM purchase p WHERE created_at = (SELECT MAX(created_at) FROM purchase p WHERE p.user_id = :userId)",
		nativeQuery=true)
	//下のList変数が呼ばれた際に、引数のIDをもとに、上のクエリが実行され、その結果がしたのList変数に代入されます。
	List<Purchase> findHistory(@Param("userId") long userId);
	
	//「?数字」はバインド変数といい、@Paramで指定できます。
	@Query(value="INSERT INTO purchase (user_id, goods_id, goods_name, item_count, total, created_at)" + 
		"VALUES (?1, ?2, ?3, ?4, ?5, now())", nativeQuery=true)
	
	@Transactional
	@Modifying
	void persist(
		@Param("userId") long userId,
		@Param("goodsId") long productId,
		@Param("goodsName") String goodsName,
		@Param("itemCount") long itemCount,
		@Param("total") long total
	);
	
}
