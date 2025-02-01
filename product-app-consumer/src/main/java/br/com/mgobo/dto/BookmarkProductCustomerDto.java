package br.com.mgobo.dto;

import java.io.Serializable;

public record BookmarkProductCustomerDto(Long idProduct, Long idCustomer, String email, boolean add) implements Serializable { }
