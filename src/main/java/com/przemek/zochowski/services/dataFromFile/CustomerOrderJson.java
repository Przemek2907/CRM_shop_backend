package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.*;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.CustomerOrder;
import com.przemek.zochowski.repository.*;
import com.przemek.zochowski.services.dataFromFile.converters.CustomerOrderJsonConverter;
import com.przemek.zochowski.services.entityService.CustomerOrderService;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class CustomerOrderJson {

    private Set<CustomerOrderDto> customerOrdersDto;
    ModelMapper modelMapper = new ModelMapper();
    CustomerOrderService customerOrderService = new CustomerOrderService();
    StockRepository stockRepository = new StockRepositoryImpl();
    CustomerRepository customerRepository = new CustomerRepositoryImpl();
    PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    ProductRepository productRepository = new ProductRepositoryImpl();

    // PROBLEM LOGICZNY Z ZADANIEM:

    // PODCZAS ŁADOWANIA PLIKOW W TABELI STOCK MAM INFORMACJĘ O ILOŚCI PRODUKTÓW, ALE
    // MOZEMY MIEC DWA TAKIE SAME PRODUKTY BEDACE W DWOCH ROZNYCH SKLEPACH
    // NATOMIAST PRZY LADOWANIU PLIKU DO TABELI CUSTOMER_ORDER MAM ZALADOWAC TYLKO DANE:
    // {TREŚĆ ZADANIA} Pobierane są w postaci napisów imię, nazwisko oraz nazwa kraju pochodzenia klienta,
    // nazwa oraz nazwa kategorii produktu, ilość zamówionych sztuk, data zamówienia,
    // wysokość zastosowanej dla zamówienia zniżki oraz rodzaj płatności.
    // PRZY TAKIM ZALOZENIU, PROGRAM KRZYCZY, ZE SA DUPLIKATY PRODUCT_ID;

    public List<CustomerOrder> createListOfOrdersFroDB (){
        List<CustomerOrder> customerOrdersToInsert = new ArrayList<>();
        try{
            customerOrdersToInsert = new CustomerOrderJsonConverter(Filenames.CUSTOMER_ORDER_JSON)
                    .fromJson().orElseThrow(IllegalStateException::new)
                    .getCustomerOrdersDto()
                    .stream()
                    .map(modelMapper::fromCustomerOrderDtoToCustomerOrder)
                    .collect(Collectors.toList());

           Iterator<CustomerOrder> orderIterator = customerOrdersToInsert.iterator();
           while (orderIterator.hasNext()){
               CustomerOrder customerOrder = orderIterator.next();
                Long productIdBasedOnproductName = productRepository.provideIdByProductName(customerOrder.getProduct().getName()).get().getId();
                System.out.println("requested product id is:-----" +  productIdBasedOnproductName);
                // DODAC METODE, KTORA ZNAJDUJE SHOP NAME NA PODSTAWIE PRODUCT ID
                String shopName = stockRepository.findOneById(stockRepository.findStockIdByProductId(productIdBasedOnproductName)).get().getShop().getName();
                System.out.println("Sklep" + shopName);
                System.out.println("Ilość " + customerOrder.getQuantity());
                boolean isCorrectRequest =  customerOrderService.processingTheOrder( productIdBasedOnproductName,shopName,customerOrder.getQuantity());
                if (!isCorrectRequest){
                    System.out.println("----REMOVING THE INVALID REQUEST - THE AMOUNT EXCEEEDS THE STOCK");
                   orderIterator.remove();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new MyException(ErrorCode.DATA_UPLOAD_FROM_FILE, e.getMessage());

        }
        return customerOrdersToInsert;
    }

    public List<CustomerOrder> addingCustomerOrdersWithRelatedDataToDB (List<CustomerOrder> customerOrdersToBePutInTheDb){
        List<CustomerOrderDto> customerOrderDtoList = new ArrayList<>();
        List<CustomerOrder> customerOrdersFinalList;

        for (CustomerOrder customerOrder: customerOrdersToBePutInTheDb){
            CustomerDto customerDto = customerRepository.findByName(
                    customerOrder.getCustomer().getName())
                    .map(c -> modelMapper.fromCustomerToCustomerDto(c))
                    .orElseThrow(NullPointerException::new);

            PaymentDto paymentDto = paymentRepository.findByName(
                    customerOrder.getPayment().getPayment().name())
                    .map(p -> modelMapper.fromPaymentToPaymentDto(p))
                    .orElseThrow(NullPointerException::new);

            ProductDto productDto = productRepository.findByName(
                    customerOrder.getProduct().getName())
                    .map(modelMapper::fromProductToProductDto)
                    .orElseThrow(NullPointerException::new);

            CustomerOrderDto customerOrderDto = CustomerOrderDto.builder()
                    .date(customerOrder.getDate())      // BO NIE MOGĘ POPRAWNIE SPARSOWAĆ DATY Z JSONA DO OBIEKTU KLASY LOCALDATE
                    .quantity(customerOrder.getQuantity())
                    .discount(customerOrderService.discountGrantLogic(modelMapper.fromProductDtoToProduct(productDto), customerOrder.getQuantity()))
                    .customerDto(customerDto)
                    .productDto(productDto)
                    .paymentDto(paymentDto)
                    .build();

            customerOrderDtoList.add(customerOrderDto);
        }
        customerOrdersFinalList = customerOrderDtoList.stream().map(modelMapper::fromCustomerOrderDtoToCustomerOrder)
                .collect(Collectors.toList());

        return customerOrdersFinalList;
    }
}
