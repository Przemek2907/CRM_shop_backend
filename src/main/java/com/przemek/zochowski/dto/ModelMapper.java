package com.przemek.zochowski.dto;

import com.przemek.zochowski.model.*;

import java.util.HashSet;

public class ModelMapper {

    public CategoryDto fromCatagoryToCategoryDto (Category category){
       return category == null ? null : CategoryDto.builder()
               .id(category.getId())
               .name(category.getName())
               .build();
    }

    public Category fromCategoryDtoToCategory (CategoryDto categoryDto){
        return categoryDto == null ? null : Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .products(new HashSet<>())
                .build();
    }

    public CountryDto fromCountryToCountryDto (Country country){
        return country == null ? null : CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    public Country fromCountryDtoToCountry (CountryDto countryDto){
        return countryDto == null ? null : Country.builder()
                .id(countryDto.getId())
                .name(countryDto.getName())
                .customers(new HashSet<>())
                .shops(new HashSet<>())
                .producers(new HashSet<>())
                .build();
    }

    public CustomerDto fromCustomerToCustomerDto (Customer customer){
        return customer == null ? null : CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .age(customer.getAge())
                .countryDTO(customer.getCountry() == null ? null : fromCountryToCountryDto(customer.getCountry()))
                .build();
    }

    public Customer fromCustomerDtoToCustomer (CustomerDto customerDto){
        return customerDto == null ? null : Customer.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .age(customerDto.getAge())
                .orders(new HashSet<>())
                .country(customerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(customerDto.getCountryDto()))
                .build();
    }

    public CustomerOrderDto fromCustomerOrderToCustomerOrderDto (CustomerOrder customerOrder){
        return customerOrder == null ? null : CustomerOrderDto.builder()
                .id(customerOrder.getId())
                .date(customerOrder.getDate())
                .quantity(customerOrder.getQuantity())
                .discount(customerOrder.getDiscount())
                .customerDto(customerOrder.getCustomer() == null ? null : fromCustomerToCustomerDto(customerOrder.getCustomer()))
                .paymentDto(customerOrder.getPayment() == null ? null : fromPaymentToPaymentDto(customerOrder.getPayment()))
                .productDto(customerOrder.getProduct() == null ? null : fromProductToProductDto(customerOrder.getProduct()))
                .build();
    }

    public CustomerOrder fromCustomerOrderDtoToCustomerOrder (CustomerOrderDto customerOrderDto){
        return customerOrderDto == null ? null : CustomerOrder.builder()
                .id(customerOrderDto.getId())
                .date(customerOrderDto.getDate())
                .quantity(customerOrderDto.getQuantity())
                .discount(customerOrderDto.getDiscount())
                .customer(customerOrderDto.getCustomerDto() == null ? null : fromCustomerDtoToCustomer(customerOrderDto.getCustomerDto()))
                .payment(customerOrderDto.getPaymentDto() == null ? null : fromPaymentDtoToPayment(customerOrderDto.getPaymentDto()))
                .product(customerOrderDto.getProductDto() == null ? null : fromProductDtoToProduct(customerOrderDto.getProductDto()))
                .build();
    }


    public PaymentDto fromPaymentToPaymentDto (Payment payment){
        return payment == null ? null : PaymentDto.builder()
                .id(payment.getId())
                .payment(payment.getPayment())
                .build();
    }

    public Payment fromPaymentDtoToPayment (PaymentDto paymentDto){
        return paymentDto == null ? null : Payment.builder()
                .id(paymentDto.getId())
                .payment(paymentDto.getPayment())
                .customerOrders(new HashSet<>())
                .build();
    }

    public ProducerDto fromProducerToProducerDto (Producer producer){
        return producer == null ? null : ProducerDto.builder()
                .id(producer.getId())
                .name(producer.getName())
                .countryDto(producer.getCountry() == null ? null : fromCountryToCountryDto(producer.getCountry()))
                .tradeDto(producer.getTrade() == null ? null : fromTradeToTradeDto(producer.getTrade()))
                .build();
    }

    public Producer fromProducerDtoToProducer (ProducerDto producerDto){
        return producerDto == null ? null : Producer.builder()
                .id(producerDto.getId())
                .name(producerDto.getName())
                .country(producerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(producerDto.getCountryDto()))
                .trade(producerDto.getTradeDto() == null ? null : fromTradeDtoToTrade(producerDto.getTradeDto()))
                .products(new HashSet<>())
                .build();
    }

    public Product fromProductDtoToProduct (ProductDto productDto){
        return productDto == null ? null : Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .stocks(new HashSet<>())
                .customerOrders(new HashSet<>())
                .producer(productDto.getProducerDto() == null ? null : fromProducerDtoToProducer(productDto.getProducerDto()))
                .category(productDto.getCategoryDto() == null ? null : fromCategoryDtoToCategory(productDto.getCategoryDto()))
                .guarantees(productDto.getGuarantees())
                .build();
    }

    public ProductDto fromProductToProductDto (Product product){
        return product == null ? null : ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .producerDto(product.getProducer() == null ? null : fromProducerToProducerDto(product.getProducer()))
                .categoryDto(product.getCategory() == null ? null : fromCatagoryToCategoryDto(product.getCategory()))
                .guarantees(product.getGuarantees())
                .build();
    }

    public ShopDto fromShopToShopDto (Shop shop){
        return shop == null ? null : ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .countryDto(shop.getCountry() == null ? null : fromCountryToCountryDto(shop.getCountry()))
                .build();
    }

    public Shop fromShopDtoToShop (ShopDto shopDto){
        return shopDto == null ? null : Shop.builder()
                .id(shopDto.getId())
                .name(shopDto.getName())
                .country(shopDto.getCountryDto() == null ? null : fromCountryDtoToCountry(shopDto.getCountryDto()))
                .stocks(new HashSet<>())
                .build();
    }

    public StockDto fromStockDtoToStock(Stock stock){
        return  stock == null ? null : StockDto.builder()
                .id(stock.getId())
                .quantity(stock.getQuantity())
                .shopDto(stock.getShop() == null ? null : fromShopToShopDto(stock.getShop()))
                .productDto(stock.getProduct() == null ? null : fromProductToProductDto(stock.getProduct()))
                .build();
    }

    public Stock fromStockDtoToStock (StockDto stockDto){
        return stockDto == null ? null : Stock.builder()
                .id(stockDto.getId())
                .quantity(stockDto.getQuantity())
                .shop(stockDto.getShopDto() == null ? null : fromShopDtoToShop(stockDto.getShopDto()))
                .product(stockDto.getProductDto() == null ? null : fromProductDtoToProduct(stockDto.getProductDto()))
                .build();
    }

    public Trade fromTradeDtoToTrade (TradeDto tradeDto){
        return tradeDto == null ? null : Trade.builder()
                .id(tradeDto.getId())
                .industry(tradeDto.getIndustry())
                .producers(new HashSet<>())
                .build();
    }

    public TradeDto fromTradeToTradeDto (Trade trade){
        return trade == null ? null : TradeDto.builder()
                .id(trade.getId())
                .industry(trade.getIndustry())
                .build();
    }
}
