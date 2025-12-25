package com.mini.orderapp.service.mapper;

import com.mini.orderapp.domain.Orders;
import com.mini.orderapp.service.dto.OrdersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {}
