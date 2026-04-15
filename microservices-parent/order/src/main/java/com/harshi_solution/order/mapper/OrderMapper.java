package com.harshi_solution.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.harshi_solution.order.dto.CreateOrderRequest;
import com.harshi_solution.order.dto.OrderLineItemResponseDTO;
import com.harshi_solution.order.dto.OrderResponseDTO;
import com.harshi_solution.order.dto.OrderStatusHistoryResponseDTO;
import com.harshi_solution.order.entities.Order;
import com.harshi_solution.order.entities.OrderLineItem;
import com.harshi_solution.order.entities.OrderStatusHistory;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderResponseDTO toDto(Order order);

    OrderLineItemResponseDTO toLineItemDto(OrderLineItem item);

    OrderStatusHistoryResponseDTO toStatusDto(OrderStatusHistory status);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "orderLineItems", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    @Mapping(target = "totalOrderQuantity", ignore = true)
    @Mapping(target = "totalBillAmount", ignore = true)
    @Mapping(target = "remainingBillAmount", ignore = true)
    Order toEntity(CreateOrderRequest request);
}
