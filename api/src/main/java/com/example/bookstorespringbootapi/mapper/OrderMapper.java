package com.example.bookstorespringbootapi.mapper;

import com.example.bookstorespringbootapi.dto.OrderResponseDTO;
import com.example.bookstorespringbootapi.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDTO toOrderResponseDTO(Order order);
    List<OrderResponseDTO> toOrderResponseDTOs(List<Order> orders);
}
