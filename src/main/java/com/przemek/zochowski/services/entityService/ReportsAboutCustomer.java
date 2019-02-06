package com.przemek.zochowski.services.entityService;

import com.przemek.zochowski.model.CustomerOrder;
import com.przemek.zochowski.services.dataInputByUser.DataManager;
import com.przemek.zochowski.repository.CustomerOrderRepository;
import com.przemek.zochowski.repository.CustomerOrderRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ReportsAboutCustomer {
    CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl();

  /*  Pobierz z bazy danych listę tych klientów, którzy zamówili przynajmniej jeden
    produkt pochodzący z tego samego kraju co klient. Informacje o kliencie powinny
    zawierać imię, nazwisko, wiek, nazwę kraju pochodzenia klienta
    oraz ilość zamówionych produktów pochodzących z innego kraju niż kraj klienta.*/

    public int howManyProductsOutsideCustomerCountry(Long customerId) {
        return customerOrderRepository
                .findAll()
                .stream()
                .filter(c -> !c.getProduct().getProducer().getCountry().equals(c.getCustomer().getCountry()))
                .filter(c -> c.getCustomer().getId().equals(customerId))
                .map(CustomerOrder::getQuantity)
                .reduce(0, Integer::sum);
    }

    // warstwa serwisowa podpunkt H - jak podać ilość zamówionych produktów pochodzących z innego kraju niż kraj klienta?
    public List<DataManager> listOfClientsWhoOrderedAproductFromTheSameCountry() {

        return customerOrderRepository
                .findAll()
                .stream()
                .filter(co -> co.getProduct().getProducer().getCountry().equals(co.getCustomer().getCountry()))
                .distinct()
                .map(CustomerOrder::getCustomer)
                .distinct()
                .map(c -> DataManager.builder()
                    .customerName(c.getName())
                    .customerSurname(c.getSurname())
                    .age(c.getAge())
                    .countryName(c.getCountry().getName())
                        .quantity(howManyProductsOutsideCustomerCountry(c.getId()))
                    .build())
                .collect(Collectors.toList());

    }
}
