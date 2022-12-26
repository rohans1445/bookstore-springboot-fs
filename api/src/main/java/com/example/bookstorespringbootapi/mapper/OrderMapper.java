package com.example.bookstorespringbootapi.mapper;

import com.example.bookstorespringbootapi.dto.OrderResponseDTO;
import com.example.bookstorespringbootapi.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
//    default OrderResponseDTO toOrderResponseDTO(Order order){
//        return OrderResponseDTO.builder()
//                .id(order.getId())
//                .total(order.getTotal())
//                .paymentType(order.getPaymentType())
//                .createdAt(order.getCreatedAt())
//                .
//                .build();
//    }
    List<OrderResponseDTO> toOrderResponseDTOs(List<Order> orders);
}
