// shared-lib/src/main/java/com/globalbooks/catalog/CatalogService.java
package com.globalbooks.orders.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface CatalogService {
    @WebMethod
    double getBookPrice(@WebParam(name = "isbn") String isbn);
}