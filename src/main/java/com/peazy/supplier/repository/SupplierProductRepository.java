package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.dto.GetProductByFilterDto;
import com.peazy.supplier.model.entity.SupplierProductEntity;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProductEntity, Long> {

    @Query(value = "SELECT DISTINCT "
    + "     Supplier_Product.ProductName, "
    + "     Supplier_ProductPic.SnCode, "
    + "     Supplier_Product.Price, "
    + "     Supplier_ProductCategory.Category, "
    + "     Supplier_Sku.Sku, "
    + "     Supplier_Product.CreateDt, "
    + "     Supplier_Product.ProductStatus, "
    + "     SUM(Supplier_ProductQty.CheckOrderCnt) as ProductQty "
    + " FROM Supplier_Product "
    + " INNER JOIN Supplier_ProductPic "
    + " ON Supplier_Product.MainPicSeqNo = Supplier_ProductPic.SeqNo "
    + " INNER JOIN Supplier_ProductColorSizeMapping "
    + " ON Supplier_Product.SeqNo = Supplier_ProductColorSizeMapping.ProductSeqNo "
    + " INNER JOIN Supplier_ProductColor "
    + " ON Supplier_ProductColorSizeMapping.ColorSeqNo = Supplier_ProductColor.SeqNo "
    + " INNER JOIN Supplier_ProductSize "
    + " ON Supplier_ProductColorSizeMapping.SizeSeqNo = Supplier_ProductSize.SeqNo "
    + " INNER JOIN Supplier_ProductQty "
    + " ON Supplier_ProductColorSizeMapping.SeqNo = Supplier_ProductQty.PCSMappingSeqNo "
    + " INNER JOIN Supplier_ProductCategory "
    + " ON Supplier_Product.ProductCategorySeqNo = Supplier_ProductCategory.SeqNo "
    + " INNER JOIN Supplier_Sku "
    + " ON Supplier_Product.MainSkuSeqNo = Supplier_Sku.SeqNo "
    + " GROUP BY "
    + " Supplier_Product.ProductName, "
    + " Supplier_ProductPic.SnCode, "
    + " Supplier_Product.Price, "
    + " Supplier_ProductCategory.Category, "
    + " Supplier_Sku.Sku, "
    + " Supplier_Product.CreateDt, "
    + " Supplier_Product.ProductStatus ", nativeQuery = true)
    public List<GetProductByFilterDto> queryProduct();
    
}