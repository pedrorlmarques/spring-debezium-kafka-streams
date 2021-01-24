package com.pt.umbrella.orderservice.mapper;

import com.pt.umbrella.orderservice.domain.Orders;
import com.pt.umbrella.orderservice.service.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrdersMapper {

    OrdersMapper INSTANCE = Mappers.getMapper(OrdersMapper.class);

    Orders toEntity(OrderDTO orderDTO);

    OrderDTO toDTO(Orders orderDTO);
}
