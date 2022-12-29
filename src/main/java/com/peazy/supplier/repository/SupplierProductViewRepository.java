package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.dto.GetProductByFilterDto;
import com.peazy.supplier.model.entity.SupplierProductViewEntity;

@Repository
public interface SupplierProductViewRepository extends JpaRepository<SupplierProductViewEntity, Long> {

        @Query(value = "SELECT DISTINCT "
                        + "     supplier_product_view.ProductSeqNo, "
                        + "     supplier_product_view.ProductName,  "
                        + "     supplier_product_view.SnCode, "
                        + "     supplier_product_view.Price, "
                        + "     supplier_product_view.Category, "
                        + "     supplier_product_view.Sku, "
                        + "     supplier_product_view.CreateDt, "
                        + "     supplier_product_view.ProductStatus, "
                        + "     supplier_product_view.VendorSeqNo, "
                        + "     SUM(supplier_product_view.CheckOrderCnt) as ProductQty "
                        + " FROM supplier_product_view "
                        + " WHERE 1 = 1 "
                        + " AND (supplier_product_view.ProductName LIKE CONCAT('%' ,:productName ,'%') or NULLIF(:productName, '') is null) "
                        + " AND (COALESCE(:skuList, NULL) IS NULL OR (supplier_product_view.Sku IN (:skuList))) "
                        + " AND (supplier_product_view.ProductStatus = :isAvailable or NULLIF(:isAvailable, '') is null or :isAvailable = 'ALL') "
                        + " GROUP BY "
                        + " supplier_product_view.ProductSeqNo, "
                        + " supplier_product_view.ProductName, "
                        + " supplier_product_view.SnCode, "
                        + " supplier_product_view.Price, "
                        + " supplier_product_view.Category, "
                        + " supplier_product_view.Sku, "
                        + " supplier_product_view.CreateDt, "
                        + " supplier_product_view.ProductStatus, "
                        + " supplier_product_view.VendorSeqNo "
                        + " ORDER BY supplier_product_view.CreateDt ", nativeQuery = true)
        public List<GetProductByFilterDto> queryProduct(
                        @Param("productName") String productName,
                        @Param("skuList") List<String> skuList,
                        @Param("isAvailable") String isAvailable);

        @Query(value = "SELECT supplier_product_view.*"
                        + "FROM supplier_product_view "
                        + "WHERE VendorSeqNo = :vendorSeqNo "
                        + "ORDER BY ProductSeqNo", nativeQuery = true)
        public List<SupplierProductViewEntity> queryOrderProduct(@Param("vendorSeqNo") Long vendorSeqNo);

        @Query(value = "SELECT supplier_product_view.* "
                        + "FROM supplier_product_view "
                        + "WHERE ProductSeqNo = :productSeqNo ", nativeQuery = true)
        public List<SupplierProductViewEntity> queryOrderProductBySeqNo(@Param("productSeqNo") Long productSeqNo);

        @Query(value = "SELECT supplier_product_view.* "
                        + "FROM supplier_product_view "
                        + "WHERE PCSMSeqNo IN (:pcsmSeqNo) ", nativeQuery = true)
        public List<SupplierProductViewEntity> queryOrderProductByPCSMSeqNo(@Param("pcsmSeqNo") List<Long> pcsmSeqNo);
}