package com.globalbooks.catalog;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import com.globalbooks.catalog.model.Book;

@WebService(
    targetNamespace = "http://catalog.globalbooks.com/",
    name = "CatalogService"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface CatalogService {
    @WebMethod
    Book getBookDetails(@WebParam(name = "isbn") String isbn);
    
    @WebMethod
    double getBookPrice(@WebParam(name = "isbn") String isbn);
    
    @WebMethod
    boolean checkAvailability(@WebParam(name = "isbn") String isbn, 
                             @WebParam(name = "quantity") int quantity);
}