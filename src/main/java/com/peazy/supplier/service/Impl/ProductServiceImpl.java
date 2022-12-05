package com.peazy.supplier.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.bean.DropDownBean;
import com.peazy.supplier.model.bean.ProductColorSizeBean;
import com.peazy.supplier.model.bean.QueryProductBean;
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
import com.peazy.supplier.model.response.QueryProductBySeqNoParam;
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
				queryProductRequest.getProductName(), queryProductRequest.getSkuList(),
				queryProductRequest.getIsAvailable());

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
			blobDocumentBean
					.setName(commonPicOptional.get().getPictureName() + commonPicOptional.get().getPictureExtension());
			blobDocumentBean.setContent(commonPicOptional.get().getPicture());
			return blobDocumentBean;
		}
		return null;
	}

	@Override
	public List<DropDownBean> getProductSizeOption() throws JsonProcessingException {
		List<SupplierProductSizeEntity> sizeEntities = supplierProductSizeRepository.findAll();

		List<DropDownBean> sizeList = new ArrayList<>();
		for (SupplierProductSizeEntity entity : sizeEntities) {
			DropDownBean dropDownBean = new DropDownBean();
			dropDownBean.setValue(String.valueOf(entity.getSeqNo()));
			dropDownBean.setLabel(entity.getSize());
			sizeList.add(dropDownBean);
		}
		return sizeList;
	}

	@Override
	public List<DropDownBean> getProductColorOption() throws JsonProcessingException {
		List<SupplierProductColorEntity> colorEntities = supplierProductColorRepository.findAll();

		List<DropDownBean> colorList = new ArrayList<>();
		for (SupplierProductColorEntity entity : colorEntities) {
			DropDownBean dropDownBean = new DropDownBean();
			dropDownBean.setValue(String.valueOf(entity.getSeqNo()));
			dropDownBean.setLabel(entity.getColor());
			colorList.add(dropDownBean);
		}
		
		return colorList;
	}

	@Override
	public List<DropDownBean> getProductCategoryOption() throws JsonProcessingException {

		List<SupplierProductCategoryEntity> categoryEntities = supplierProductCategoryRepository.findAll();

		List<DropDownBean> categoryList = new ArrayList<>();
		for (SupplierProductCategoryEntity entity : categoryEntities) {
			DropDownBean dropDownBean = new DropDownBean();
			dropDownBean.setValue(String.valueOf(entity.getSeqNo()));
			dropDownBean.setLabel(entity.getCategory());
			categoryList.add(dropDownBean);
		}
		return categoryList;
	}

	@Override
	public QueryProductBySeqNoParam queryProductBySeqNo(Long productSeqNo) throws JsonProcessingException {

		List<GetProductBySeqNoDto> supplierProudctViewList = supplierProductRepository
				.queryProductBySeqNo(productSeqNo);

		GetProductBySeqNoDto getProductBySeqNoDto = null;
		if (supplierProudctViewList.size() > 0) {
			getProductBySeqNoDto = supplierProudctViewList.get(0);
		}

		QueryProductBySeqNoParam queryProductBySeqNoParam = new QueryProductBySeqNoParam();
		if (getProductBySeqNoDto != null) {
			queryProductBySeqNoParam.setProductName(getProductBySeqNoDto.getProductName());
			queryProductBySeqNoParam.setMpnList(getProductMpnByProductSeqNo(productSeqNo));
			queryProductBySeqNoParam.setSkuList(getProductSkuByProductSeqNo(productSeqNo));
			queryProductBySeqNoParam.setCost(getProductBySeqNoDto.getCost());
			queryProductBySeqNoParam.setPrice(getProductBySeqNoDto.getPrice());
			queryProductBySeqNoParam.setCategory(getProductBySeqNoDto.getCategorySeqNo());
			queryProductBySeqNoParam.setProductStatus(getProductBySeqNoDto.getProductStatus());
			queryProductBySeqNoParam.setProductDesc(getProductBySeqNoDto.getProductDesc());
			queryProductBySeqNoParam.setMainPic(getProductBySeqNoDto.getSnCode());
			queryProductBySeqNoParam.setPicList(getProductPicByProductSeqNo(productSeqNo, getProductBySeqNoDto.getSnCode()));
			List<ProductColorSizeBean> productColorSizeBeans = getProductSizeColorByProductSeqNo(productSeqNo);
			queryProductBySeqNoParam.setProductColorSizeList(productColorSizeBeans);
			List<String> sizeList = productColorSizeBeans.stream().map(ProductColorSizeBean::getSizeSeqNo).distinct()
					.collect(Collectors.toList());
			List<String> colorList = productColorSizeBeans.stream().map(ProductColorSizeBean::getColorSeqNo).distinct()
					.collect(Collectors.toList());
			queryProductBySeqNoParam.setSizeList(sizeList);
			queryProductBySeqNoParam.setColorList(colorList);
		}

		return queryProductBySeqNoParam;
	}

	public List<Long> getProductMpnByProductSeqNo(Long productSeqNo) {
		List<SupplierMpnEntity> supplierProductMpnEntities = supplierProductMpnRepository
				.findByProductSeqNo(productSeqNo);
		return supplierProductMpnEntities.stream().map(SupplierMpnEntity::getSeqNo).collect(Collectors.toList());
	}

	public List<Long> getProductSkuByProductSeqNo(Long productSeqNo) {
		List<SupplierSkuEntity> supplierProductSkuEntities = supplierProductSkuRepository
				.findByProductSeqNo(productSeqNo);
		return supplierProductSkuEntities.stream().map(SupplierSkuEntity::getSeqNo).collect(Collectors.toList());
	}

	public List<String> getProductPicByProductSeqNo(Long productSeqNo, String mainPicSeqNo) {
		List<SupplierProductPicEntity> supplierProductPicEntities = SupplierProductPicRepository
				.findByProductSeqNo(productSeqNo);
		List<String> picList = supplierProductPicEntities.stream().map(SupplierProductPicEntity::getSnCode)
			.collect(Collectors.toList());

		// 過濾掉主圖片
		picList = picList.stream().filter(picSeqNo -> !StringUtils.equals(picSeqNo, mainPicSeqNo)).collect(Collectors.toList());

		// 若照片數量小於8張，則需塞入空值
		if (picList.size() < 8) {
			int originPicListSize = 8 - picList.size();
			for (int i = 0; i < originPicListSize; i++) {
				picList.add("");
			}
		}
		
		return picList;
	}

	public List<ProductColorSizeBean> getProductSizeColorByProductSeqNo(Long productSeqNo) {
		List<SupplierProductViewEntity> supplierProductViewEntities = supplierProductViewRepository
				.queryOrderProductBySeqNo(productSeqNo);

		List<ProductColorSizeBean> productColorSizeList = new ArrayList<>();

		if (!CollectionUtils.isEmpty(supplierProductViewEntities)) {
			for (SupplierProductViewEntity supplierProductViewEntity : supplierProductViewEntities) {
				ProductColorSizeBean productColorSizeBean = new ProductColorSizeBean();
				productColorSizeBean.setSizeSeqNo(String.valueOf(supplierProductViewEntity.getSizeSeqNo()));
				productColorSizeBean.setColorSeqNo(String.valueOf(supplierProductViewEntity.getColorSeqNo()));
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

	@Override
	public QueryProductResponse editProduct(QueryProductBySeqNoParam queryProductBySeqNoParam) {



		return null;
	}

}
