package com.peazy.supplier.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.bean.DropDownBean;
import com.peazy.supplier.model.bean.PictureSnCodeBean;
import com.peazy.supplier.model.bean.ProductColorSizeBean;
import com.peazy.supplier.model.bean.QueryProductBean;
import com.peazy.supplier.model.dto.GetProductByFilterDto;
import com.peazy.supplier.model.dto.GetProductBySeqNoDto;
import com.peazy.supplier.model.entity.CommonPictureEntity;
import com.peazy.supplier.model.entity.SupplierMpnEntity;
import com.peazy.supplier.model.entity.SupplierProductCategoryEntity;
import com.peazy.supplier.model.entity.SupplierProductColorEntity;
import com.peazy.supplier.model.entity.SupplierProductColorSizeMappingEntity;
import com.peazy.supplier.model.entity.SupplierProductEntity;
import com.peazy.supplier.model.entity.SupplierProductPicEntity;
import com.peazy.supplier.model.entity.SupplierProductSizeEntity;
import com.peazy.supplier.model.entity.SupplierProductViewEntity;
import com.peazy.supplier.model.entity.SupplierSkuEntity;
import com.peazy.supplier.model.entity.SupplierVendorEntity;
import com.peazy.supplier.model.request.QueryProductRequest;
import com.peazy.supplier.model.response.QueryProductBySeqNoParam;
import com.peazy.supplier.model.response.QueryProductResponse;
import com.peazy.supplier.repository.CommonPictureRepository;
import com.peazy.supplier.repository.SupplierProductCategoryRepository;
import com.peazy.supplier.repository.SupplierProductColorRepository;
import com.peazy.supplier.repository.SupplierProductColorSizeMappingRepository;
import com.peazy.supplier.repository.SupplierProductMpnRepository;
import com.peazy.supplier.repository.SupplierProductRepository;
import com.peazy.supplier.repository.SupplierProductSizeRepository;
import com.peazy.supplier.repository.SupplierProductSkuRepository;
import com.peazy.supplier.repository.SupplierProductViewRepository;
import com.peazy.supplier.repository.SupplierVendorRepository;
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
	private SupplierVendorRepository supplierVendorRepository;

	@Autowired
	private SupplierProductPicRepository supplierProductPicRepository;

	@Autowired
	private SupplierProductViewRepository supplierProductViewRepository;

	@Autowired
	private SupplierProductColorSizeMappingRepository supplierProductColorSizeMappingRepository;

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
		Optional<CommonPictureEntity> commonPicOptional = commonPictureRepository.findById(Long.valueOf(snCode));
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
	public List<DropDownBean> getProductVendorOption() throws JsonProcessingException {
		List<SupplierVendorEntity> vendorEntities = supplierVendorRepository.findAll();
		List<DropDownBean> vendorList = new ArrayList<>();

		for (SupplierVendorEntity entity : vendorEntities) {
			DropDownBean dropDownBean = new DropDownBean();
			dropDownBean.setValue(String.valueOf(entity.getSeqNo()));
			dropDownBean.setLabel(entity.getVendor());
			vendorList.add(dropDownBean);
		}

		return vendorList;
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
			queryProductBySeqNoParam.setProductSeqNo(getProductBySeqNoDto.getProductSeqNo());
			queryProductBySeqNoParam.setProductName(getProductBySeqNoDto.getProductName());
			queryProductBySeqNoParam.setMpnList(getProductMpnByProductSeqNo(productSeqNo));
			queryProductBySeqNoParam.setSkuList(getProductSkuByProductSeqNo(productSeqNo));
			queryProductBySeqNoParam.setCost(getProductBySeqNoDto.getCost());
			queryProductBySeqNoParam.setPrice(getProductBySeqNoDto.getPrice());
			queryProductBySeqNoParam.setCategory(getProductBySeqNoDto.getCategorySeqNo());
			queryProductBySeqNoParam.setProductStatus(getProductBySeqNoDto.getProductStatus());
			queryProductBySeqNoParam.setProductDesc(getProductBySeqNoDto.getProductDesc());
			queryProductBySeqNoParam.setMainPicSnCode(getProductBySeqNoDto.getSnCode());
			queryProductBySeqNoParam.setPicSnCodeList(getProductPicByProductSeqNo(productSeqNo, getProductBySeqNoDto.getSnCode()));
			List<ProductColorSizeBean> productColorSizeBeans = getProductSizeColorByProductSeqNo(productSeqNo);
			queryProductBySeqNoParam.setProductColorSizeList(productColorSizeBeans);
			List<String> sizeList = productColorSizeBeans.stream().map(ProductColorSizeBean::getSizeSeqNo).distinct()
					.collect(Collectors.toList());
			List<String> colorList = productColorSizeBeans.stream().map(ProductColorSizeBean::getColorSeqNo).distinct()
					.collect(Collectors.toList());
			queryProductBySeqNoParam.setSizeList(sizeList);
			queryProductBySeqNoParam.setColorList(colorList);
			queryProductBySeqNoParam.setVendorSeqNo(getProductBySeqNoDto.getVendorSeqNo());
		}

		return queryProductBySeqNoParam;
	}

	public List<String> getProductMpnByProductSeqNo(Long productSeqNo) {
		List<SupplierMpnEntity> supplierProductMpnEntities = supplierProductMpnRepository
				.findByProductSeqNo(productSeqNo);
		return supplierProductMpnEntities.stream().map(SupplierMpnEntity::getMpn).collect(Collectors.toList());
	}

	public List<String> getProductSkuByProductSeqNo(Long productSeqNo) {
		List<SupplierSkuEntity> supplierProductSkuEntities = supplierProductSkuRepository
				.findByProductSeqNo(productSeqNo);
		return supplierProductSkuEntities.stream().map(SupplierSkuEntity::getSku).collect(Collectors.toList());
	}

	public List<String> getProductPicByProductSeqNo(Long productSeqNo, String mainPicSeqNo) {
		List<SupplierProductPicEntity> supplierProductPicEntities = supplierProductPicRepository
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
	@Transactional(rollbackFor = { Exception.class })
	public void editProduct(QueryProductBySeqNoParam queryProductBySeqNoParam, MultipartFile mainPicFile, List<MultipartFile> picFiles) throws IOException {

		PictureSnCodeBean pictureSnCodeBean = updatePicture(mainPicFile, picFiles, queryProductBySeqNoParam.getUserId());
		queryProductBySeqNoParam.setMainPicSnCode(pictureSnCodeBean.getMainPicSnCode());
		queryProductBySeqNoParam.setPicSnCodeList(pictureSnCodeBean.getPicSnCodeList());

		// SKU跟MPN也在這裡面更新
		insertOrUpdateProduct(queryProductBySeqNoParam, true);		

		insertOrUpdateProductColorSizeMapping(queryProductBySeqNoParam.getProductColorSizeList(), 
			queryProductBySeqNoParam.getProductSeqNo(), queryProductBySeqNoParam.getUserId());
	}

	@Transactional
	@Override
	public void editProductWithoutPic(QueryProductBySeqNoParam queryProductBySeqNoParam) {
		// SKU跟MPN也在這裡面更新
		insertOrUpdateProduct(queryProductBySeqNoParam, false);

		insertOrUpdateProductColorSizeMapping(queryProductBySeqNoParam.getProductColorSizeList(), 
			queryProductBySeqNoParam.getProductSeqNo(), queryProductBySeqNoParam.getUserId());		
	}
	
	private PictureSnCodeBean updatePicture(MultipartFile mainPicFile, List<MultipartFile> picFiles, String userId) throws IOException {

		CommonPictureEntity mainPictureEntity = new CommonPictureEntity();
		mainPictureEntity.setPicture(mainPicFile.getBytes());
		if (StringUtils.isNotBlank(mainPicFile.getOriginalFilename())) {

			String[] fullFileName = mainPicFile.getOriginalFilename().split("\\.");
			if (fullFileName.length > 0) {
				mainPictureEntity.setPictureName(fullFileName[0].toString());
			}
			if (fullFileName.length > 1) {
				mainPictureEntity.setPictureExtension(fullFileName[1].toString());
			}
		}
		mainPictureEntity.setCreateUser(userId);
		mainPictureEntity.setCreateDt(new Date());
		mainPictureEntity.setUpdateUser(userId);
		mainPictureEntity.setUpdateDt(new Date());
		mainPictureEntity = commonPictureRepository.save(mainPictureEntity);

		PictureSnCodeBean pictureSnCodeBean = new PictureSnCodeBean();
		pictureSnCodeBean.setMainPicSnCode(String.valueOf(mainPictureEntity.getSnCode()));

		List<String> picSnCodeList = new ArrayList<>();
		for (MultipartFile picFile : picFiles) {
			CommonPictureEntity pictureEntity = new CommonPictureEntity();
			pictureEntity.setPicture(picFile.getBytes());

			String[] fullFileName = picFile.getOriginalFilename().split("\\.");
			if (fullFileName.length > 0) {
				pictureEntity.setPictureName(fullFileName[0].toString());
			}
			if (fullFileName.length > 1) {
				pictureEntity.setPictureExtension(fullFileName[1].toString());
			}

			pictureEntity.setCreateUser(userId);
			pictureEntity.setCreateDt(new Date());
			pictureEntity.setUpdateUser(userId);
			pictureEntity.setUpdateDt(new Date());
			pictureEntity = commonPictureRepository.save(pictureEntity);
			picSnCodeList.add(String.valueOf(pictureEntity.getSnCode()));
		}
		pictureSnCodeBean.setPicSnCodeList(picSnCodeList);
		return pictureSnCodeBean;
	}

	private void insertOrUpdateProduct(QueryProductBySeqNoParam queryProductBySeqNoParam, 
		boolean isNeedUpdatePic) {

		SupplierProductEntity supplierProductEntity = new SupplierProductEntity();
		boolean isUpdateProduct = true;

		// 1. 若是編輯則取出原本的資料(會先把圖片、SKU、MPN塞入)，若新增則直接set資料
		if (StringUtils.isNotBlank(queryProductBySeqNoParam.getProductSeqNo())) {
			Optional<SupplierProductEntity> supplierProductOptional = supplierProductRepository.findById(Long.parseLong(queryProductBySeqNoParam.getProductSeqNo()));
			if (supplierProductOptional.isPresent()) {
				supplierProductEntity = supplierProductOptional.get();
			}

			if (isNeedUpdatePic) {
				// 更新MainPic資料
				insertOrUpdatePic(queryProductBySeqNoParam.getMainPicSnCode(), queryProductBySeqNoParam.getPicSnCodeList(), 
				queryProductBySeqNoParam.getProductSeqNo(), queryProductBySeqNoParam.getUserId());
				if (StringUtils.isNotBlank(queryProductBySeqNoParam.getMainPicSnCode())) {
					supplierProductEntity.setMainPicSnCode(queryProductBySeqNoParam.getMainPicSnCode());
				}
			}

			// 更新SKU資料
			String mainSkuSeqNo = insertOrUpdateSku(queryProductBySeqNoParam.getSkuList(), queryProductBySeqNoParam.getProductSeqNo(), queryProductBySeqNoParam.getUserId());
			if (StringUtils.isNotEmpty(mainSkuSeqNo)) {
				supplierProductEntity.setMainSkuSeqNo(mainSkuSeqNo);
			}

			// 更新MPN資料
			String mainMpnSeqNo = insertOrUpdateMpn(queryProductBySeqNoParam.getMpnList(), queryProductBySeqNoParam.getProductSeqNo(), queryProductBySeqNoParam.getUserId());
			if (StringUtils.isNotEmpty(mainMpnSeqNo)) {
				supplierProductEntity.setMainMpnSeqNo(mainMpnSeqNo);
			}

		} else {
			supplierProductEntity.setCreateUser(queryProductBySeqNoParam.getUserId());
			supplierProductEntity.setCreateDt(new Date());
			isUpdateProduct = false;
		}

		// 2. set資料
		supplierProductEntity.setProductName(queryProductBySeqNoParam.getProductName());
		supplierProductEntity.setCost(queryProductBySeqNoParam.getCost());
		supplierProductEntity.setPrice(queryProductBySeqNoParam.getPrice());
		supplierProductEntity.setProductStatus(queryProductBySeqNoParam.getProductStatus());
		supplierProductEntity.setProductCategorySeqNo(queryProductBySeqNoParam.getCategory());
		supplierProductEntity.setProductDesc(queryProductBySeqNoParam.getProductDesc());
		supplierProductEntity.setVendorSeqNo(queryProductBySeqNoParam.getVendorSeqNo());

		supplierProductEntity.setUpdateUser(queryProductBySeqNoParam.getUserId());
		supplierProductEntity.setUpdateDt(new Date());

		supplierProductEntity = supplierProductRepository.save(supplierProductEntity);
		queryProductBySeqNoParam.setProductSeqNo(String.valueOf(supplierProductEntity.getSeqNo()));

		// 若是Insert產品，則需要後續再塞入產品的MainPic、MainSku、MainMpn
		if (!isUpdateProduct) {
			if (isNeedUpdatePic) {
				// 更新MainPic資料
				insertOrUpdatePic(queryProductBySeqNoParam.getMainPicSnCode(), queryProductBySeqNoParam.getPicSnCodeList(), 
				queryProductBySeqNoParam.getProductSeqNo(), queryProductBySeqNoParam.getUserId());
				if (StringUtils.isNotBlank(queryProductBySeqNoParam.getMainPicSnCode())) {
					supplierProductEntity.setMainPicSnCode(queryProductBySeqNoParam.getMainPicSnCode());
				}
			}

			// 更新SKU資料
			String mainSkuSeqNo = insertOrUpdateSku(queryProductBySeqNoParam.getSkuList(), queryProductBySeqNoParam.getProductSeqNo(), queryProductBySeqNoParam.getUserId());
			if (StringUtils.isNotEmpty(mainSkuSeqNo)) {
				supplierProductEntity.setMainSkuSeqNo(mainSkuSeqNo);
			}

			// 更新MPN資料
			String mainMpnSeqNo = insertOrUpdateMpn(queryProductBySeqNoParam.getMpnList(), queryProductBySeqNoParam.getProductSeqNo(), queryProductBySeqNoParam.getUserId());
			if (StringUtils.isNotEmpty(mainMpnSeqNo)) {
				supplierProductEntity.setMainMpnSeqNo(mainMpnSeqNo);
			}
			supplierProductRepository.save(supplierProductEntity);
		}

	}

	private String insertOrUpdateSku(List<String> skuList, String productSeqNo, String userId) {
		
		if (!CollectionUtils.isEmpty(supplierProductSkuRepository.findByProductSeqNo(Long.valueOf(productSeqNo)))) {
			supplierProductSkuRepository.deleteByProductSeqNo(Long.valueOf(productSeqNo));
		}

		List<SupplierSkuEntity> supplierSkuEntities = new ArrayList<>();

		for (String sku : skuList) {
			SupplierSkuEntity supplierSkuEntity = new SupplierSkuEntity();
			supplierSkuEntity.setSku(sku);
			supplierSkuEntity.setProductSeqNo(Long.valueOf(productSeqNo));
			supplierSkuEntity.setCreateDt(new Date());
			supplierSkuEntity.setCreateUser(userId);
			supplierSkuEntity.setUpdateDt(new Date());
			supplierSkuEntity.setUpdateUser(userId);
			supplierSkuEntities.add(supplierSkuEntity);
		}

		supplierSkuEntities = supplierProductSkuRepository.saveAll(supplierSkuEntities);
		if (!CollectionUtils.isEmpty(supplierSkuEntities)) {
			return String.valueOf(supplierSkuEntities.get(0).getSeqNo());
		}
		return null;
	}

	private String insertOrUpdateMpn(List<String> mpnList, String productSeqNo, String userId) {
		
		if (!CollectionUtils.isEmpty(supplierProductMpnRepository.findByProductSeqNo(Long.valueOf(productSeqNo)))) {
			supplierProductMpnRepository.deleteByProductSeqNo(Long.valueOf(productSeqNo));
		}

		List<SupplierMpnEntity> supplierMpnEntities = new ArrayList<>();

		for (String mpn : mpnList) {
			SupplierMpnEntity supplierMpnEntity = new SupplierMpnEntity();
			supplierMpnEntity.setMpn(mpn);
			supplierMpnEntity.setProductSeqNo(Long.valueOf(productSeqNo));
			supplierMpnEntity.setCreateDt(new Date());
			supplierMpnEntity.setCreateUser(userId);
			supplierMpnEntity.setUpdateDt(new Date());
			supplierMpnEntity.setUpdateUser(userId);
			supplierMpnEntities.add(supplierMpnEntity);
		}

		supplierMpnEntities = supplierProductMpnRepository.saveAll(supplierMpnEntities);
		if (!CollectionUtils.isEmpty(supplierMpnEntities)) {
			return String.valueOf(supplierMpnEntities.get(0).getSeqNo());
		}
		return null;
	}

	private String insertOrUpdatePic(String mainPic, List<String> picList, String productSeqNo, String userId) {
		if (!CollectionUtils.isEmpty(supplierProductPicRepository.findByProductSeqNo(Long.valueOf(productSeqNo)))) {
			supplierProductPicRepository.deleteByProductSeqNo(Long.valueOf(productSeqNo));
		}
		
		List<SupplierProductPicEntity> supplierPicEntities = new ArrayList<>();
		SupplierProductPicEntity mainPicEntity = new SupplierProductPicEntity();
		mainPicEntity.setSnCode(mainPic);
		mainPicEntity.setProductSeqNo(Long.valueOf(productSeqNo));
		mainPicEntity.setCreateDt(new Date());
		mainPicEntity.setCreateUser(userId);
		mainPicEntity.setUpdateDt(new Date());
		mainPicEntity.setUpdateUser(userId);
		mainPicEntity = supplierProductPicRepository.save(mainPicEntity);

		for (String pic : picList) {
			SupplierProductPicEntity picEntity = new SupplierProductPicEntity();
			picEntity.setSnCode(pic);
			picEntity.setProductSeqNo(Long.valueOf(productSeqNo));
			picEntity.setCreateDt(new Date());
			picEntity.setCreateUser(userId);
			picEntity.setUpdateDt(new Date());
			picEntity.setUpdateUser(userId);
			supplierPicEntities.add(picEntity);
		}

		supplierProductPicRepository.saveAll(supplierPicEntities);

		if (mainPicEntity != null) {
			return String.valueOf(mainPicEntity.getSnCode());
		}
		return null;
	}

	private void insertOrUpdateProductColorSizeMapping(List<ProductColorSizeBean> productColorSizeList, String productSeqNo, String userId) {
		if (!CollectionUtils.isEmpty(supplierProductColorSizeMappingRepository.findByProductSeqNo(Long.valueOf(productSeqNo)))) {
			supplierProductColorSizeMappingRepository.deleteByProductSeqNo(Long.valueOf(productSeqNo));
		}

		List<SupplierProductColorSizeMappingEntity> supplierProductColorSizeMappingEntities = new ArrayList<>();

		productColorSizeList.forEach(ProductColorSizeBean -> {
			SupplierProductColorSizeMappingEntity supplierProductColorSizeMappingEntity = new SupplierProductColorSizeMappingEntity();
			supplierProductColorSizeMappingEntity.setProductSeqNo(Long.valueOf(productSeqNo));
			supplierProductColorSizeMappingEntity.setColorSeqNo(Long.valueOf(ProductColorSizeBean.getColorSeqNo()));
			supplierProductColorSizeMappingEntity.setSizeSeqNo(Long.valueOf(ProductColorSizeBean.getSizeSeqNo()));
			supplierProductColorSizeMappingEntity.setNotOrderCnt(ProductColorSizeBean.getNotOrderCnt());
			supplierProductColorSizeMappingEntity.setOrderedCnt(ProductColorSizeBean.getOrderedCnt());
			supplierProductColorSizeMappingEntity.setCheckOrderCnt(ProductColorSizeBean.getCheckOrderCnt());
			supplierProductColorSizeMappingEntity.setAllocatedCnt(ProductColorSizeBean.getAllocatedCnt());
			supplierProductColorSizeMappingEntity.setReadyDeliveryCnt(ProductColorSizeBean.getReadyDeliveryCnt());
			supplierProductColorSizeMappingEntity.setFinishCnt(ProductColorSizeBean.getFinishCnt());
			supplierProductColorSizeMappingEntity.setCreateUser(userId);
			supplierProductColorSizeMappingEntity.setCreateDt(new Date());
			supplierProductColorSizeMappingEntity.setUpdateUser(userId);
			supplierProductColorSizeMappingEntity.setUpdateDt(new Date());

			supplierProductColorSizeMappingEntities.add(supplierProductColorSizeMappingEntity);
		});
		supplierProductColorSizeMappingRepository.saveAll(supplierProductColorSizeMappingEntities);
	}

}
