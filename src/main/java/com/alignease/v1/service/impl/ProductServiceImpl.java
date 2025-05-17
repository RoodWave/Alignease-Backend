package com.alignease.v1.service.impl;

import com.alignease.v1.dto.request.ProductRequest;
import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.entity.Inventory;
import com.alignease.v1.entity.Product;
import com.alignease.v1.exception.AlignEaseValidationsException;
import com.alignease.v1.repository.ProductRepository;
import com.alignease.v1.service.ProductService;
import com.alignease.v1.utils.Messages;
import com.alignease.v1.utils.RequestStatus;
import com.alignease.v1.utils.ResponseCodes;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    Messages messages;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) {
        logger.info("Add Product Starts");

        ProductResponse productResponse = new ProductResponse();

        try {
            Product product = modelMapper.map(productRequest, Product.class);
            product.setInventory(null);
            product.setIsDeleted("N");

            Product initialSavedProduct = productRepository.save(product);

            if (productRequest.getInventoryRequest() != null) {
                Inventory inventory = modelMapper.map(productRequest.getInventoryRequest(), Inventory.class);
                inventory.setProduct(initialSavedProduct);
                initialSavedProduct.setInventory(inventory);

                Product savedProduct = productRepository.save(initialSavedProduct);
                logger.info("Product and inventory saved successfully with ID: {}", savedProduct.getProductId());

                productResponse.setProduct(savedProduct);
            } else {
                productResponse.setProduct(initialSavedProduct);
            }
            logger.info("Add Product Success");

            productResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            productResponse.setResponseCode(ResponseCodes.SUCCESS);
            productResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_ADD_SUCCESS, null));

        } catch (Exception e) {
            throw new AlignEaseValidationsException(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_ADD_FAILURE, null));
        }

        return productResponse;
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        logger.info("Update Product Starts for productId: {}", productId);

        ProductResponse productResponse = new ProductResponse();

        try {
            Optional<Product> existingProductOpt = productRepository.findByProductIdAndIsDeleted(productId, "N");

            if (existingProductOpt.isEmpty()) {
                logger.error("Product not found with ID: {}", productId);
                throw new AlignEaseValidationsException(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_NOT_FOUND, null));
            }

            Product existingProduct = existingProductOpt.get();

            existingProduct.setName(productRequest.getName());
            existingProduct.setBrand(productRequest.getBrand());
            existingProduct.setModel(productRequest.getModel());
            existingProduct.setSize(productRequest.getSize());
            existingProduct.setPrice(productRequest.getPrice());

            if (productRequest.getInventoryRequest() != null) {
                if (existingProduct.getInventory() != null) {
                    Inventory existingInventory = existingProduct.getInventory();
                    existingInventory.setQuantity(productRequest.getInventoryRequest().getQuantity());
                    existingInventory.setLocation(productRequest.getInventoryRequest().getLocation());
                    existingInventory.setStockStatus(productRequest.getInventoryRequest().getStockStatus());
                } else {
                    Inventory newInventory = modelMapper.map(productRequest.getInventoryRequest(), Inventory.class);
                    newInventory.setProduct(existingProduct);
                    existingProduct.setInventory(newInventory);
                }
            }

            Product updatedProduct = productRepository.save(existingProduct);
            logger.info("Product updated successfully with ID: {}", updatedProduct.getProductId());

            productResponse.setProduct(updatedProduct);
            productResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            productResponse.setResponseCode(ResponseCodes.SUCCESS);
            productResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_UPDATE_SUCCESS, null));

            logger.info("Update Product Success");

        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_UPDATE_FAILURE, null));
        }

        return productResponse;
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        logger.info("Get Product by ID Starts for productId: {}", productId);

        ProductResponse productResponse = new ProductResponse();

        try {
            Optional<Product> productOpt = productRepository.findByProductIdAndIsDeleted(productId, "N");

            if (productOpt.isEmpty()) {
                logger.error("Product not found with ID: {}", productId);
                throw new AlignEaseValidationsException(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_NOT_FOUND, null));
            }

            Product product = productOpt.get();
            productResponse.setProduct(product);
            productResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            productResponse.setResponseCode(ResponseCodes.SUCCESS);
            productResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_FETCH_SUCCESS, null));

            logger.info("Get Product by ID Success");

        } catch (Exception e) {
            logger.error("Error fetching product: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_FETCH_FAILURE, null));
        }

        return productResponse;
    }

    @Override
    public ProductResponse getAllProducts() {
        logger.info("Get All Products Starts");

        ProductResponse productResponse = new ProductResponse();

        try {
            List<Product> products = productRepository.findByIsDeleted("N");

            productResponse.setProducts(products);
            productResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            productResponse.setResponseCode(ResponseCodes.SUCCESS);
            productResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_FETCH_SUCCESS, null));

            logger.info("Get All Products Success. Found {} products", products.size());

        } catch (Exception e) {
            logger.error("Error fetching all products: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_UPDATE_FAILURE, null));
        }

        return productResponse;
    }

    @Override
    @Transactional
    public ProductResponse deleteProduct(Long productId) {
        logger.info("Delete Product Starts for productId: {}", productId);

        ProductResponse productResponse = new ProductResponse();

        try {
            Optional<Product> productOpt = productRepository.findByProductIdAndIsDeleted(productId, "N");

            if (productOpt.isEmpty()) {
                logger.error("Product not found with ID: {}", productId);
                throw new AlignEaseValidationsException(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_NOT_FOUND, null));
            }

            Product product = productOpt.get();
            product.setIsDeleted("Y");

            Product deletedProduct = productRepository.save(product);
            logger.info("Product soft deleted successfully with ID: {}", deletedProduct.getProductId());

            productResponse.setProduct(deletedProduct);
            productResponse.setStatus(RequestStatus.SUCCESS.getStatus());
            productResponse.setResponseCode(ResponseCodes.SUCCESS);
            productResponse.setMessage(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_DELETE_SUCCESS, null));

            logger.info("Delete Product Success");

        } catch (Exception e) {
            logger.error("Error deleting product: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_DELETE_FAILURE, null));
        }

        return productResponse;
    }
}