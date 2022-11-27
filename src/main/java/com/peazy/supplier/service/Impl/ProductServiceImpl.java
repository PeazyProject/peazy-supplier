package com.peazy.supplier.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.bean.ProductColorSizeBean;
import com.peazy.supplier.model.bean.QueryCheckOrderItemBean;
import com.peazy.supplier.model.bean.QueryProductBean;
import com.peazy.supplier.model.dto.CheckOrderItemDto;
import com.peazy.supplier.model.dto.GetProductByFilterDto;
import com.peazy.supplier.model.dto.GetProductBySeqNoDto;
import com.peazy.supplier.model.entity.CommonPictureEntity;
import com.peazy.supplier.model.entity.SupplierMpnEntity;
import com.peazy.supplier.model.entity.SupplierProductCategoryEntity;
import com.peazy.supplier.model.entity.SupplierProductColorEntity;
import com.peazy.supplier.model.entity.SupplierProductPicEntity;
import com.peazy.supplier.model.entity.SupplierProductSizeEntity;
import com.peazy.supplier.model.entity.SupplierProductViewEntity;
import com.peazy.supplier.model.entity.SupplierSkuEntity;
import com.peazy.supplier.model.request.QueryProductRequest;
import com.peazy.supplier.model.response.QueryCheckOrderResponse;
import com.peazy.supplier.model.response.QueryProductBySeqNoResponse;
import com.peazy.supplier.model.response.QueryProductResponse;
import com.peazy.supplier.repository.CommonPictureRepository;
import com.peazy.supplier.repository.SupplierProductCategoryRepository;
import com.peazy.supplier.repository.SupplierProductColorRepository;
import com.peazy.supplier.repository.SupplierProductMpnRepository;
import com.peazy.supplier.repository.SupplierProductRepository;
import com.peazy.supplier.repository.SupplierProductSizeRepository;
import com.peazy.supplier.repository.SupplierProductSkuRepository;
import com.peazy.supplier.repository.SupplierProductViewRepository;
import com.peazy.supplier.repository.SupplierProductPicRepository;
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
	private SupplierProductMpnRepository supplierProductMpnRepository;

	@Autowired
	private SupplierProductSkuRepository supplierProductSkuRepository;

	@Autowired
	private SupplierProductColorRepository supplierProductColorRepository;

	@Autowired
	private SupplierProductCategoryRepository supplierProductCategoryRepository;

	@Autowired
	private SupplierProductPicRepository SupplierProductPicRepository;

	@Autowired
	private SupplierProductViewRepository supplierProductViewRepository;

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
			queryProductBean.setProductSeqNo(getProductByFilterDto.getProductSeqNo());
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
	public QueryProductBySeqNoResponse queryProductBySeqNo(Long productSeqNo) throws JsonProcessingException {

		List<GetProductBySeqNoDto> supplierProudctViewList = supplierProductRepository.queryProductBySeqNo(productSeqNo);

		GetProductBySeqNoDto getProductBySeqNoDto = null;
		if (supplierProudctViewList.size() > 0) {
			getProductBySeqNoDto = supplierProudctViewList.get(0);
		}

		QueryProductBySeqNoResponse queryProductBySeqNoResponse = new QueryProductBySeqNoResponse();
		if (getProductBySeqNoDto != null) {
			queryProductBySeqNoResponse.setProductName(getProductBySeqNoDto.getProductName());
			queryProductBySeqNoResponse.setMpnList(getProductMpnByProductSeqNo(productSeqNo));
			queryProductBySeqNoResponse.setSkuList(getProductSkuByProductSeqNo(productSeqNo));
			queryProductBySeqNoResponse.setCost(getProductBySeqNoDto.getCost());
			queryProductBySeqNoResponse.setPrice(getProductBySeqNoDto.getPrice());
			queryProductBySeqNoResponse.setCategory(getProductBySeqNoDto.getCategory());
			queryProductBySeqNoResponse.setProductStatus(getProductBySeqNoDto.getProductStatus());
			queryProductBySeqNoResponse.setProductDesc(getProductBySeqNoDto.getProductDesc());
			queryProductBySeqNoResponse.setMainPic(getProductBySeqNoDto.getSnCode());
			queryProductBySeqNoResponse.setPicList(getProductPicByProductSeqNo(productSeqNo));
			List<ProductColorSizeBean> productColorSizeBeans = getProductSizeColorByProductSeqNo(productSeqNo);
			queryProductBySeqNoResponse.setProductColorSizeList(productColorSizeBeans);
			List<Long> sizeList = productColorSizeBeans.stream().map(ProductColorSizeBean::getSizeSeqNo).collect(Collectors.toList());
			List<Long> colorList = productColorSizeBeans.stream().map(ProductColorSizeBean::getSizeSeqNo).collect(Collectors.toList());
			queryProductBySeqNoResponse.setSizeList(sizeList);
			queryProductBySeqNoResponse.setColorList(colorList);
		}

		return queryProductBySeqNoResponse;
	}

	@Override
	public QueryCheckOrderResponse queryCheckOrder() throws JsonProcessingException {
		QueryCheckOrderResponse response = new QueryCheckOrderResponse();
		List<CheckOrderItemDto> checkOrderItemDtos = supplierProductRepository.queryCheckOrder();
		List<QueryCheckOrderItemBean> queryCheckOrderItemBeanList = new ArrayList<>();

		for (CheckOrderItemDto checkOrderItemDto : checkOrderItemDtos) {
			QueryCheckOrderItemBean queryCheckOrderItemBean = new QueryCheckOrderItemBean();
			BeanUtils.copyProperties(checkOrderItemDto, queryCheckOrderItemBean);
			queryCheckOrderItemBeanList.add(queryCheckOrderItemBean);
		}
		response.setQueryCheckOrderItemBeanList(queryCheckOrderItemBeanList);

		return response;
	}
	public List<Long> getProductMpnByProductSeqNo(Long productSeqNo) {
		List<SupplierMpnEntity> supplierProductMpnEntities = supplierProductMpnRepository.findByProductSeqNo(productSeqNo);
		return supplierProductMpnEntities.stream().map(SupplierMpnEntity::getSeqNo).collect(Collectors.toList());
	}

	public List<Long> getProductSkuByProductSeqNo(Long productSeqNo) {
		List<SupplierSkuEntity> supplierProductSkuEntities = supplierProductSkuRepository.findByProductSeqNo(productSeqNo);
		return supplierProductSkuEntities.stream().map(SupplierSkuEntity::getSeqNo).collect(Collectors.toList());
	}

	public List<String> getProductPicByProductSeqNo(Long productSeqNo) {
		List<SupplierProductPicEntity> supplierProductPicEntities = SupplierProductPicRepository.findByProductSeqNo(productSeqNo);
		return supplierProductPicEntities.stream().map(SupplierProductPicEntity::getSnCode).collect(Collectors.toList());
	}

	public List<ProductColorSizeBean> getProductSizeColorByProductSeqNo(Long productSeqNo) {
		List<SupplierProductViewEntity> supplierProductViewEntities = supplierProductViewRepository.queryOrderProductBySeqNo(productSeqNo);

		List<ProductColorSizeBean> productColorSizeList = new ArrayList<>();

		if (!CollectionUtils.isEmpty(supplierProductViewEntities)) {
			for (SupplierProductViewEntity supplierProductViewEntity : supplierProductViewEntities) {
				ProductColorSizeBean productColorSizeBean = new ProductColorSizeBean();
				productColorSizeBean.setSizeSeqNo(supplierProductViewEntity.getSizeSeqNo());
				productColorSizeBean.setColorSeqNo(supplierProductViewEntity.getColorSeqNo());
				productColorSizeBean.setNotOrderCnt(supplierProductViewEntity.getNotOrderCnt());
				productColorSizeBean.setOrderedCnt(supplierProductViewEntity.getOrderedCnt());
				productColorSizeBean.setCheckOrderCnt(supplierProductViewEntity.getCheckOrderCnt());
				productColorSizeBean.setAllocatedCnt(supplierProductViewEntity.getAllocatedCnt());
				productColorSizeBean.setReadyDeliveryCnt(supplierProductViewEntity.getReadyDeliveryCnt());
				productColorSizeBean.setFinishCnt(supplierProductViewEntity.getFinishCnt());
				productColorSizeList.add(productColorSizeBean);
			}
		}
		return productColorSizeList;
	}
}
