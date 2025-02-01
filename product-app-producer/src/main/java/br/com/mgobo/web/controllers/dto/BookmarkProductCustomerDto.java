package br.com.mgobo.web.controllers.dto;

public record BookmarkProductCustomerDto(Long idProduct, Long idCustomer, String email, boolean add) { }
