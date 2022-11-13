package com.peazy.supplier.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.bean.QueryProductBean;
import com.peazy.supplier.model.dto.GetProductByFilterDto;
import com.peazy.supplier.model.entity.CommonPictureEntity;
import com.peazy.supplier.model.entity.SupplierProductCategoryEntity;
import com.peazy.supplier.model.entity.SupplierProductColorEntity;
import com.peazy.supplier.model.entity.SupplierProductSizeEntity;
import com.peazy.supplier.model.request.QueryProductRequest;
import com.peazy.supplier.model.response.QueryProductBySeqNoResponse;
import com.peazy.supplier.model.response.QueryProductResponse;
import com.peazy.supplier.repository.CommonPictureRepository;
import com.peazy.supplier.repository.SupplierProductCategoryRepository;
import com.peazy.supplier.repository.SupplierProductColorRepository;
import com.peazy.supplier.repository.SupplierProductRepository;
import com.peazy.supplier.repository.SupplierProductSizeRepository;
import com.peazy.supplier.service.interfaces.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private SupplierProductRepository supplierProductRepository;

	@Autowired
	private CommonPictureRepository commonPictureRepository;

	@Autowired
	private SupplierProductSizeRepository supplierProductSizeRepository;

	@Autowired
	private SupplierProductColorRepository supplierProductColorRepository;

	@Autowired
	private SupplierProductCategoryRepository supplierProductCategoryRepository;

	@Override
	public QueryProductResponse queryProduct(QueryProductRequest queryProductRequest) throws JsonProcessingException {

		if (CollectionUtils.isEmpty(queryProductRequest.getSkuList())) {
			queryProductRequest.setSkuList(null);
		}

		if (CollectionUtils.isEmpty(queryProductRequest.getInStockList())) {
			queryProductRequest.setInStockList(null);
		}

		List<GetProductByFilterDto> supplierProductEntity = supplierProductRepository.queryProduct(
			queryProductRequest.getProductName(), queryProductRequest.getSkuList(), queryProductRequest.getIsAvailable());
		
		List<QueryProductBean> queryProductList = new ArrayList<>();
		for (GetProductByFilterDto getProductByFilterDto : supplierProductEntity) {
			QueryProductBean queryProductBean = new QueryProductBean();
			queryProductBean.setProductSeqNo(getProductByFilterDto.getSeqNo());
			queryProductBean.setProductName(getProductByFilterDto.getProductName());
			queryProductBean.setSnCode(getProductByFilterDto.getSnCode());
			queryProductBean.setPrice(getProductByFilterDto.getPrice());
			queryProductBean.setCategory(getProductByFilterDto.getCategory());
			queryProductBean.setSku(getProductByFilterDto.getSku());
			queryProductBean.setCreateDt(getProductByFilterDto.getCreateDt());
			queryProductBean.setProductStatus(getProductByFilterDto.getProductStatus());
			queryProductBean.setProductQty(getProductByFilterDto.getProductQty());

			if (isProductInStock(queryProductRequest.getInStockList(), 
				queryProductBean.getProductQty(), getProductByFilterDto.getProductStatus())) {
				queryProductList.add(queryProductBean);
			}
			

		}

		QueryProductResponse queryProductResponse = new QueryProductResponse();
		queryProductResponse.setQueryProductList(queryProductList);
		return queryProductResponse;
	}

	private boolean isProductInStock(List<String> inStockList, int productQty, String productStatus) {


		if (CollectionUtils.isEmpty(inStockList)) {
			return true;
		}

		if (inStockList.contains("Y")) {
			if (productQty > 0 && !StringUtils.equals(productStatus, "OUT_OF_STOCK")) {
				return true;
			}
		}

		if (inStockList.contains("N")) {
			if (productQty == 0) {
				return true;
			}
		}

		return false;
	}

	@Override
	public BlobDocumentBean getImgUrl(String snCode) throws JsonProcessingException {
		Optional<CommonPictureEntity> commonPicOptional = commonPictureRepository.findById(snCode);
		if (commonPicOptional.isPresent()) {
			BlobDocumentBean blobDocumentBean = new BlobDocumentBean();
			blobDocumentBean.setName(commonPicOptional.get().getPictureName() + commonPicOptional.get().getPictureExtension());
			blobDocumentBean.setContent(commonPicOptional.get().getPicture());
			return blobDocumentBean;
		}
		return null;
	}

	@Override
	public List<String> getProductSizeOption() throws JsonProcessingException {
		List<SupplierProductSizeEntity> sizeEntities = supplierProductSizeRepository.findAll();

		List<String> sizeList = new ArrayList<>();
		for (SupplierProductSizeEntity entity : sizeEntities) {
			sizeList.add(entity.getSize());
		}
		return sizeList;
	}

	@Override
	public List<String> getProductColorOption() throws JsonProcessingException {
		List<SupplierProductColorEntity> colorEntities = supplierProductColorRepository.findAll();

		List<String> colorList = new ArrayList<>();
		for (SupplierProductColorEntity entity : colorEntities) {
			colorList.add(entity.getColor());
		}
		return colorList;
	}

	@Override
	public List<String> getProductCategoryOption() throws JsonProcessingException {

		List<SupplierProductCategoryEntity> categoryEntities = supplierProductCategoryRepository.findAll();

		List<String> categoryList = new ArrayList<>();
		for (SupplierProductCategoryEntity entity : categoryEntities) {
			categoryList.add(entity.getCategory());
		}
		return categoryList;
	}

	@Override
	public QueryProductBySeqNoResponse queryProductBySeqNo(String seqNo) throws JsonProcessingException {

		// TODO Auto-generated method stub

		return null;
	}

}
