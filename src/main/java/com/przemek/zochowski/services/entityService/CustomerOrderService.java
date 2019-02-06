package com.przemek.zochowski.services.entityService;

import com.przemek.zochowski.dto.CustomerOrderDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.*;
import com.przemek.zochowski.services.dataInputByUser.DataManager;
import com.przemek.zochowski.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerOrderService {

    ProductRepository productRepository = new ProductRepositoryImpl();
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    StockRepository stockRepository = new StockRepositoryImpl();
    CustomerRepository customerRepository = new CustomerRepositoryImpl();
    ModelMapper modelMapper = new ModelMapper();
    PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();
    StockService stockService = new StockService();


    // 1. PREPARE A METHOD THAT WILL ALLOW TO PLACE AN ORDER (DATA INPUT: CUSTOMER NAME, CUSTOMER SURNAME, CUSTOMER COUNTRY,
    // PRODUCT NAME, PRODUCT CATEGORY, AMOUNT OF THE STOCK BEING ORDERED, DATE OF THE ORDER, DISCOUNT AND THE PAYMENT_METHOD
    //String name, String surname, String productName, int requestedAmount, LocalDate localDate, String payment

    public boolean placingAnOrder(DataManager dataManager) {
        boolean successfulOrder = false;
        try {
            if (dataManager.getCustomerName() == null) {
                throw new NullPointerException();
            }

            if (dataManager.getCustomerSurname() == null) {
                throw new NullPointerException();
            }


            if (dataManager.getTheIdOfTheSelectedProduct() == null) {
                throw new NullPointerException();
            }

            if (dataManager.getShopName() == null) {
                throw new NullPointerException();
            }

            if (dataManager.getQuantity() <= 0 || !String.valueOf(dataManager.getQuantity()).matches("[0-9]+")) {
                throw new NullPointerException();
            }

            if (dataManager.getLocalDate().isBefore(LocalDate.now())) {
                throw new NullPointerException();
            }


            Customer customer = customerRepository.findByNameAndSurname(dataManager.getCustomerName(), dataManager.getCustomerSurname()).get();
            Product product = productRepository.findOneById(dataManager.getTheIdOfTheSelectedProduct()).get();
            Payment paymentOption = paymentRepository.findByName(dataManager.getPaymentType()).get();


            CustomerOrder customerOrder = modelMapper.fromCustomerOrderDtoToCustomerOrder(CustomerOrderDto.builder()
                    .date(dataManager.getLocalDate()).discount(discountGrantLogic(product, dataManager.getQuantity())).quantity(dataManager.getQuantity()).build());

            customerOrder.setCustomer(customer);
            customerOrder.setProduct(product);
            customerOrder.setPayment(paymentOption);
            BigDecimal priceToPay = BigDecimal.valueOf(customerOrder.getQuantity()).multiply(product.getPrice().multiply(BigDecimal.ONE.subtract(customerOrder.getDiscount())));

            successfulOrder = processingTheOrder(dataManager.getTheIdOfTheSelectedProduct(), dataManager.getShopName(), dataManager.getQuantity());


            if (successfulOrder) {
                customerOrderRepository.addOrUpdate(customerOrder);
                System.out.println("YOUR ORDER SUMMARY: " + product.getName() + ". AMOUNT OF ORDERED ITEMS: " + customerOrder.getQuantity()
                        + ". PRICE PER ITEM: " + product.getPrice() + " EURO. DISCOUNT GRANTED: " + customerOrder.getDiscount()
                        + ". TO PAY: " + priceToPay + " EURO. CHOSEN PAYMENT METHOD: " + customerOrder.getPayment().getPayment().name());

                System.out.println("THANK YOU FOR USING OUR SERVICES");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
        return successfulOrder;
    }


    //2. PREPARE A VALIDATION METHOD TO CHECK IF THE AMOUNT BEING ORDERED IS NOT EXCEEDING THE AMOUNT OF THE PRODUCT IN STOCK


    private boolean processingTheOrder(Long productId, String shopName, int requestedAmount) {
        System.out.println("PLEASE WAIT, WHILE WE PROCESS YOUR ORDER");
        boolean sufficientAmountInStock = checkingIfAmountOfProductIsSufficient(productId, shopName, requestedAmount);
        try {
            if (!sufficientAmountInStock) {
                System.out.println("THE REQUESTED AMOUNT EXCEEDS THE CURRENT AMOUNT IN STOCK. PLEASE TRY AGAIN");
            } else {
                deductingTheRequestedAmountFromStock(productId, shopName, requestedAmount);
                System.out.println("YOU ORDER HAS BEEN ACCEPTED" );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
        return sufficientAmountInStock;
    }



    private void deductingTheRequestedAmountFromStock(Long productId, String shopName, int requestedAmount) {
        try {
            Optional<Stock> stockItem = stockRepository.findAll().stream()
                    .filter(s -> s.getProduct().getId().equals(productId))
                    .filter(s -> s.getShop().getName().equals(shopName))
                    .limit(1)
                    .findFirst();

            int currentAmountInStock = stockItem.get().getQuantity();
            Stock stockItemBeingUpdated = stockItem.get();
            stockItemBeingUpdated.setQuantity(currentAmountInStock - requestedAmount);
            stockRepository.addOrUpdate(stockItemBeingUpdated);

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.SERVICE, e.getMessage());
        }
    }

    private Map<Long, Integer> selectedProductAndAmountInStock(Long selectedProductId, String shopName) {
        Map<Long, Integer> productInStock = stockService.presentingTheListOfProductsWithStockAmount().stream()
                .filter(s -> s.getTheIdOfTheSelectedProduct().equals(selectedProductId))
                .filter(s -> s.getShopName().equals(shopName) )
                .collect(Collectors.toMap(s -> s.getTheIdOfTheSelectedProduct(), s -> s.getQuantity())
                );

        return productInStock;
    }

    public boolean checkingIfAmountOfProductIsSufficient(Long idOfOrderedProduct, String shopName, int requestedAmount) {
        Map<Long, Integer> productAndStock = selectedProductAndAmountInStock(idOfOrderedProduct, shopName);
        boolean enoughAmountInStock = false;
        for (Map.Entry<Long, Integer> entrySet : productAndStock.entrySet()) {
            if (entrySet.getValue() >= requestedAmount) {
                enoughAmountInStock = true;
            }
        }
        return enoughAmountInStock;
    }

    private BigDecimal discountGrantLogic(Product product, int requestedAmount) {
        BigDecimal discountGranted;
        if (product.getPrice().compareTo(BigDecimal.valueOf(100000)) >= 0 && requestedAmount > 10) {
            discountGranted = BigDecimal.valueOf(0.1);
        } else if (product.getPrice().compareTo(BigDecimal.valueOf(50000)) > 0 && requestedAmount > 10) {
            discountGranted = BigDecimal.valueOf(0.07);
        } else if (product.getPrice().compareTo(BigDecimal.valueOf(2000)) > 0 && requestedAmount > 20) {
            discountGranted = BigDecimal.valueOf(0.05);
        } else if (product.getPrice().compareTo(BigDecimal.valueOf(2000)) > 0 || requestedAmount >= 100) {
            discountGranted = BigDecimal.valueOf(0.02);
        } else {
            discountGranted = BigDecimal.ZERO;
        }
        return discountGranted;
    }

}




